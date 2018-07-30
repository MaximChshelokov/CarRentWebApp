package com.mv.schelokov.car_rent.actions.admin;

import com.mv.schelokov.car_rent.actions.AbstractAction;
import com.mv.schelokov.car_rent.actions.JspForward;
import com.mv.schelokov.car_rent.consts.Jsps;
import com.mv.schelokov.car_rent.exceptions.ActionException;
import com.mv.schelokov.car_rent.model.entity.User;
import com.mv.schelokov.car_rent.model.entity.builders.RoleBuilder;
import com.mv.schelokov.car_rent.model.entity.builders.UserBuilder;
import com.mv.schelokov.car_rent.model.services.RoleService;
import com.mv.schelokov.car_rent.model.services.UserService;
import com.mv.schelokov.car_rent.model.services.exceptions.ServiceException;
import com.mv.schelokov.car_rent.model.validators.UserValidator;
import com.mv.schelokov.car_rent.model.validators.ValidationResult;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class SaveUser extends AbstractAction {
    
    private static final Logger LOG = Logger.getLogger(SaveUser.class);
    private static final String ERROR = "Unable to write user to database";

    @Override
    public JspForward execute(HttpServletRequest req, HttpServletResponse res)
            throws ActionException {
        
        JspForward forward = new JspForward();
        
        if (isAdmin(req)) {
            try {
                User user = new UserBuilder()
                        .setLogin(req.getParameter("login"))
                        .setPassword(req.getParameter("password"))
                        .setRole(new RoleBuilder()
                                .setId(getIntParam(req, "role"))
                                .getRole())
                        .getUser();
                String repeat = req.getParameter("repeat");
                
                int validationResult = new UserValidator(user).validate();
                
                if (validationResult == ValidationResult.OK && 
                        !user.getPassword().equals(repeat))
                    validationResult = ValidationResult.PASSWORDS_NOT_MATCH;
                
                UserService userService = UserService.getInstance();
                
                if (validationResult == ValidationResult.OK && !userService
                        .getUserByLogin(user.getLogin()).isEmpty())
                    validationResult = ValidationResult.SAME_LOGIN;
                if (validationResult == ValidationResult.OK) {
                    userService.registerNewUser(user);
                    return new JspForward("action/user_list", true);
                }
                
                RoleService roleService = RoleService.getInstance();
                
                req.setAttribute("errParam", validationResult);
                req.setAttribute("user_edit", user);
                req.setAttribute("roles", roleService.getAllRoles());
                
                forward.setUrl(Jsps.ADMIN_ADD_USER);
                
                return forward;
                
            } catch (ServiceException ex) {
                LOG.error(ERROR, ex);
                throw new ActionException(ERROR, ex);
            }
        } else {
            sendForbidden(res);
            return forward;
        }
    }
}
