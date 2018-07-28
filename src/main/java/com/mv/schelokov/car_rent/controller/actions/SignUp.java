package com.mv.schelokov.car_rent.controller.actions;

import com.mv.schelokov.car_rent.controller.consts.Jsps;
import com.mv.schelokov.car_rent.controller.consts.SessionAttr;
import com.mv.schelokov.car_rent.controller.exceptions.ActionException;
import com.mv.schelokov.car_rent.model.entities.User;
import com.mv.schelokov.car_rent.model.entities.builders.RoleBuilder;
import com.mv.schelokov.car_rent.model.entities.builders.UserBuilder;
import com.mv.schelokov.car_rent.model.services.UserService;
import com.mv.schelokov.car_rent.model.services.exceptions.ServiceException;
import com.mv.schelokov.car_rent.model.validators.UserValidator;
import com.mv.schelokov.car_rent.model.validators.ValidationResult;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class SignUp implements Action {
    
    private static final Logger LOG = Logger.getLogger(SignUp.class);
    private static final String ERROR = "Unable to create a new user";
    private static final int USER_ROLE = 2;
    
    @Override
    public JspForward execute(HttpServletRequest req, HttpServletResponse res)
            throws ActionException {
    try {
            User user = new UserBuilder()
                    .setLogin(req.getParameter("email"))
                    .setPassword(req.getParameter("pass"))
                    .setRole(new RoleBuilder()
                            .setId(USER_ROLE)
                            .getRole())
                    .getUser();
            String repeat = req.getParameter("repeat");

            int validationResult = new UserValidator(user).validate();

            if (validationResult == ValidationResult.OK
                    && !user.getPassword().equals(repeat)) {
                validationResult = ValidationResult.PASSWORDS_NOT_MATCH;
            }
            if (validationResult == ValidationResult.OK && !UserService
                .getUserByLogin(user.getLogin()).isEmpty()) {
                validationResult = ValidationResult.SAME_LOGIN;
            }   
            if (validationResult == ValidationResult.OK) {
                UserService.registerNewUser(user);
                req.getSession().setAttribute(SessionAttr.USER, 
                        UserService.getUserByLogin(user.getLogin()).get(0));
                return new JspForward("action/welcome", true);
            }
            req.setAttribute("errParam", validationResult);
            req.setAttribute("sign", true);
            req.setAttribute("user_edit", user);
            return new JspForward(Jsps.HOME);
        } catch (ServiceException ex) {
            LOG.error(ERROR, ex);
            throw new ActionException(ERROR, ex);
        }
    }
}
