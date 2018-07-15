package com.mv.schelokov.car_rent.controller.actions;

import com.mv.schelokov.car_rent.controller.consts.Urls;
import com.mv.schelokov.car_rent.controller.actions.admin.DeleteUser;
import com.mv.schelokov.car_rent.controller.actions.admin.ShowCarList;
import com.mv.schelokov.car_rent.controller.actions.admin.ShowEditCarPage;
import com.mv.schelokov.car_rent.controller.actions.admin.ShowEditUserPage;
import com.mv.schelokov.car_rent.controller.actions.admin.ShowUsersList;
import com.mv.schelokov.car_rent.controller.actions.admin.UpdateCar;
import com.mv.schelokov.car_rent.controller.actions.admin.UpdateUser;
import com.mv.schelokov.car_rent.controller.consts.Jsps;
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
            return new ShowPage(Jsps.HOME);
        switch(Urls.valueOf(actionName)) {
            case HOME:
                return new ShowPage(Jsps.HOME);
            case LOGIN:
                return new Login();
//            case WELCOME:
//                return new ShowPage("view/welcome.jsp");
            case ADMIN_ACTIONS:
                return new ShowPage(Jsps.ADMIN_ACTIONS);
            case USER_LIST:
                return new ShowUsersList();
            case EDIT_USER:
                return new ShowEditUserPage();
            case UPDATE_USER:
                return new UpdateUser();
            case DELETE_USER:
                return new DeleteUser();
            case CAR_LIST:
                return new ShowCarList();
            case EDIT_CAR:
                return new ShowEditCarPage();
            case UPDATE_CAR:
                return new UpdateCar();
            default:
                return new ShowPage(Jsps.HOME);                
        }
    }
}
