package com.mv.schelokov.car_rent.controller.actions;

import com.mv.schelokov.car_rent.controller.Urls;
import com.mv.schelokov.car_rent.controller.actions.admin.DeleteUser;
import com.mv.schelokov.car_rent.controller.actions.admin.ShowEditUserPage;
import com.mv.schelokov.car_rent.controller.actions.admin.ShowUsersList;
import com.mv.schelokov.car_rent.controller.actions.admin.UpdateUser;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class ActionFactory {
    private static final Logger log = Logger.getLogger(ActionFactory.class);
    
    public static Action action(HttpServletRequest req) {
        String actionName = req.getPathInfo().replaceAll("/", "").toUpperCase();
        log.debug(String.format("Forward to %s", actionName));
        if ("".equals(actionName))
            return new ShowPage("WEB-INF/jsp/home.jsp");
        switch(Urls.valueOf(actionName)) {
            case HOME:
                return new ShowPage("WEB-INF/jsp/home.jsp");
            case LOGIN:
                return new Login();
            case WELCOME:
                return new ShowPage("view/welcome.jsp");
            case ADMIN_ACTIONS:
                return new ShowPage("WEB-INF/jsp/admin/admin-actions.jsp");
            case USER_LIST:
                return new ShowUsersList();
            case EDIT_USER:
                return new ShowEditUserPage();
            case UPDATE_USER:
                return new UpdateUser();
            case DELETE_USER:
                return new DeleteUser();
            default:
                return new ShowPage("WEB-INF/jsp/home.jsp");                
        }
    }
}
