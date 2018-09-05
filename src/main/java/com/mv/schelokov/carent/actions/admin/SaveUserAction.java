package com.mv.schelokov.carent.actions.admin;

import com.mv.schelokov.carent.actions.interfaces.AbstractAction;
import com.mv.schelokov.carent.actions.JspForward;
import com.mv.schelokov.carent.actions.consts.Actions;
import com.mv.schelokov.carent.actions.consts.Jsps;
import com.mv.schelokov.carent.actions.exceptions.ActionException;
import com.mv.schelokov.carent.model.entity.User;
import com.mv.schelokov.carent.model.entity.builders.RoleBuilder;
import com.mv.schelokov.carent.model.entity.builders.UserBuilder;
import com.mv.schelokov.carent.model.services.RoleService;
import com.mv.schelokov.carent.model.services.UserService;
import com.mv.schelokov.carent.model.services.exceptions.ServiceException;
import com.mv.schelokov.carent.model.validators.UserValidator;
import com.mv.schelokov.carent.model.validators.ValidationResult;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class SaveUserAction extends AbstractAction {
    
    private static final Logger LOG = Logger.getLogger(SaveUserAction.class);
    private static final String ERROR = "Unable to write user to database";
    private static final String ROLE = "role";

    @Override
    public JspForward execute(HttpServletRequest req, HttpServletResponse res)
            throws ActionException {
        
        JspForward forward = new JspForward();
        
        if (isAdmin(req)) {
            try {
                User user = new UserBuilder()
                        .withLogin(req.getParameter(LOGIN))
                        .withPassword(req.getParameter(PASSWORD))
                        .withRole(new RoleBuilder()
                                .withId(getIntParam(req, ROLE))
                                .getRole())
                        .getUser();
                String repeat = req.getParameter(REPEAT_PASSWORD);
                
                int validationResult = new UserValidator(user).validate();
                
                if (validationResult == ValidationResult.OK && 
                        !user.getPassword().equals(repeat))
                    validationResult = ValidationResult.PASSWORDS_NOT_MATCH;
                
                UserService userService = new UserService();
                
                if (validationResult == ValidationResult.OK && !userService
                        .getUserByLogin(user.getLogin()).isEmpty())
                    validationResult = ValidationResult.SAME_LOGIN;
                if (validationResult == ValidationResult.OK) {
                    userService.registerNewUser(user);
                    return new JspForward(Actions
                            .getActionName(Actions.USER_LIST), true);
                }
                
                RoleService roleService = new RoleService();
                
                req.setAttribute(ERROR_NUMBER, validationResult);
                req.setAttribute(USER_EDIT, user);
                req.setAttribute(ROLES, roleService.getAllRoles());
                
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
