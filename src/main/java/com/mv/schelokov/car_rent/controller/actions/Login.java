package com.mv.schelokov.car_rent.controller.actions;

import com.mv.schelokov.car_rent.model.entities.User;
import com.mv.schelokov.car_rent.model.services.UserService;
import com.mv.schelokov.car_rent.model.services.exceptions.ServiceException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class Login implements Action {

    @Override
    public JspRedirect execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        UserService userService = new UserService();
        String login = req.getParameter("email");
        String password = req.getParameter("pass");
        try {
            List userList = userService.getUserByCredentials(login, password);
            if (userList.size() == 1) {
                User user = (User) userList.get(0);
                HttpSession session = req.getSession();
                session.setAttribute("user", user);
                req.setAttribute("login", login);
            }
        } catch (ServiceException ex) {
            throw new Exception("Насяйника! Полный бизнес...", ex);
        }
        req.setAttribute("login", "хер там ночевал...");
        return new JspRedirect("/view/welcome.jsp");
                
    }

    
    
}
