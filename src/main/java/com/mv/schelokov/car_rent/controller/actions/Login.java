package com.mv.schelokov.car_rent.controller.actions;

import com.mv.schelokov.car_rent.controller.consts.Jsps;
import com.mv.schelokov.car_rent.controller.consts.SessionAttr;
import com.mv.schelokov.car_rent.controller.exceptions.ActionException;
import com.mv.schelokov.car_rent.model.entities.User;
import com.mv.schelokov.car_rent.model.services.UserService;
import com.mv.schelokov.car_rent.model.services.exceptions.ServiceException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class Login extends AbstractAction {
    
    public static final Logger LOG = Logger.getLogger(Login.class);
    private static final String ERROR = "Failed to login";
    private static final int OK = 0;
    private static final int EMPTY_FIELD = 1;
    private static final int USER_NOT_FOUND = 2;

    @Override
    public JspForward execute(HttpServletRequest req, HttpServletResponse res)
            throws ActionException {
        UserService userService = new UserService();
        String login = req.getParameter("email");
        String password = req.getParameter("pass");
        try {
            int validationResult = validate(login, password);
            if (validationResult == OK) {
                List userList = userService.getUserByCredentials(login, password);
                if (userList.size() == 1) {
                    User user = (User) userList.get(0);
                    HttpSession session = req.getSession();
                    session.setAttribute(SessionAttr.USER, user);
                    if (isAdmin(req)) {
                        return new JspForward("action/admin_actions", true);
                    } else {
                        return new JspForward("action/home", true);
                    }
                } else
                    validationResult = USER_NOT_FOUND;
            }
            req.setAttribute("errorLogin", validationResult);
            req.setAttribute("sign", false);
            return new JspForward(Jsps.HOME);
        } catch (ServiceException ex) {
            LOG.error(ERROR, ex);
            throw new ActionException(ERROR, ex);
        }
    }
    
    private int validate(String login, String password) {
        if (login == null || login.isEmpty() || password == null
                || password.isEmpty())
            return EMPTY_FIELD;
        return OK;
    }
    
}
