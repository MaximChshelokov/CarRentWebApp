package com.mv.schelokov.car_rent.controller.actions;

import com.mv.schelokov.car_rent.controller.Urls;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class ActionFactory {
    
    public static Action action(HttpServletRequest req) {
        String actionName = req.getPathInfo().replaceAll("/", "").toUpperCase();
        if ("".equals(actionName))
            return new ShowPage("WEB-INF/jsp/home.jsp");
        switch(Urls.valueOf(actionName)) {
            case HOME:
                return new ShowPage("WEB-INF/jsp/home.jsp");
            case LOGIN:
                return new Login();
            case LOGIN_PAGE:
                return new ShowPage("login.jsp");
            case WELCOME:
                return new ShowPage("view/welcome.jsp");
            case ADMIN_ACTIONS:
                return new ShowPage("WEB-INF/jsp/admin/admin-actions.jsp");
            default:
                return new ShowPage("WEB-INF/jsp/home.jsp");                
        }
    }
}
