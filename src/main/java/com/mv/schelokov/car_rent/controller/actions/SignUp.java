package com.mv.schelokov.car_rent.controller.actions;

import com.mv.schelokov.car_rent.controller.exceptions.ActionException;
import com.mv.schelokov.car_rent.model.entities.builders.RoleBuilder;
import com.mv.schelokov.car_rent.model.entities.builders.UserBuilder;
import com.mv.schelokov.car_rent.model.services.UserService;
import com.mv.schelokov.car_rent.model.services.exceptions.ServiceException;
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
    private static final int OK = 0;
    private static final int EMPTY_FIELD = 1;
    private static final int PASSWORDS_NOT_MATCH = 2;
    private static final int SAME_LOGIN = 3;
    private static final int USER_ROLE = 2;
    
    @Override
    public JspForward execute(HttpServletRequest req, HttpServletResponse res)
            throws ActionException {
        String login = req.getParameter("email");
        String password = req.getParameter("password");
        String repeat = req.getParameter("repeat");
        UserService userService = new UserService();
        try {
            int validationResult = validate(login, password, repeat);
            if (validationResult == OK
                    && !userService.getUserByLogin(login).isEmpty()) {
                validationResult = SAME_LOGIN;
            }
            if (validationResult == OK) {
                userService.registerNewUser(new UserBuilder()
                        .setLogin(login)
                        .setPassword(password)
                        .setRole(new RoleBuilder().setId(USER_ROLE).getRole())
                        .getUser());
            }
            req.setAttribute("errorSignup", validationResult);
            req.setAttribute("logn", false);
            return new JspForward("#", true);
        } catch (ServiceException ex) {
            LOG.error(ERROR, ex);
            throw new ActionException(ERROR, ex);
        }
    }
    
    private int validate(String login, String password, String repeat) {
        if (login == null || login.isEmpty() || password == null
                || password.isEmpty() || repeat == null || repeat.isEmpty()) {
            return EMPTY_FIELD;
        }
        if (!password.equals(repeat)) {
            return PASSWORDS_NOT_MATCH;
        }
        return OK;
    }
}
