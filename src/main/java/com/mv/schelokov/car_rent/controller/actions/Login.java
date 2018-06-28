package com.mv.schelokov.car_rent.controller.actions;

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
public class Login implements Action {
    
    public static final Logger log = Logger.getLogger(Login.class);

    @Override
    public JspRedirect execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        UserService userService = new UserService();
        String login = req.getParameter("email");
        String password = req.getParameter("pass");
        log.debug(String.format("Login = '%s', password = '%s'", login, password));
        try {
            List userList = userService.getUserByCredentials(login, password);
            if (userList.size() == 1) {
                User user = (User) userList.get(0);
                HttpSession session = req.getSession();
                session.setAttribute("user", user);
                req.setAttribute("login", login);
                log.debug("Parameters has been written");
            } else
                log.debug(String.format("userList.size = %d", userList.size()));
        } catch (ServiceException ex) {
            log.error("Failed to login", ex);
            throw new ActionException("Failed to login", ex);
        }
        return new JspRedirect("/view/welcome.jsp");
                
    }

    
    
}
