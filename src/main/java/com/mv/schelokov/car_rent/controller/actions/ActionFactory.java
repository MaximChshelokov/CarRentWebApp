package com.mv.schelokov.car_rent.controller.actions;

import com.mv.schelokov.car_rent.controller.actions.admin.DeleteCar;
import com.mv.schelokov.car_rent.controller.actions.admin.DeleteUser;
import com.mv.schelokov.car_rent.controller.actions.admin.ShowAddCarPage;
import com.mv.schelokov.car_rent.controller.actions.admin.ShowAddUserPage;
import com.mv.schelokov.car_rent.controller.actions.admin.ShowCarList;
import com.mv.schelokov.car_rent.controller.actions.admin.ShowEditCarPage;
import com.mv.schelokov.car_rent.controller.actions.admin.ShowEditUserPage;
import com.mv.schelokov.car_rent.controller.actions.admin.ShowUsersList;
import com.mv.schelokov.car_rent.controller.actions.admin.UpdateCar;
import com.mv.schelokov.car_rent.controller.actions.admin.UpdateUser;
import com.mv.schelokov.car_rent.controller.consts.Jsps;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class ActionFactory {
    private static final Logger LOG = Logger.getLogger(ActionFactory.class);
    private static final Map<String, Action> ACTIONS = new HashMap<>();
    static {
        ACTIONS.put("home", new ShowPage(Jsps.HOME));
        ACTIONS.put("login", new Login());
        ACTIONS.put("admin_actions", new ShowPage(Jsps.ADMIN_ACTIONS));
        ACTIONS.put("user_list", new ShowUsersList());
        ACTIONS.put("edit_user", new ShowEditUserPage());
        ACTIONS.put("update_user", new UpdateUser());
        ACTIONS.put("delete_user", new DeleteUser());
        ACTIONS.put("create_user", new ShowAddUserPage());
        ACTIONS.put("car_list", new ShowCarList());
        ACTIONS.put("edit_car", new ShowEditCarPage());
        ACTIONS.put("update_car", new UpdateCar());
        ACTIONS.put("delete_car", new DeleteCar());
        ACTIONS.put("create_car", new ShowAddCarPage());
    }
    
    public static Action action(HttpServletRequest req) {
        String actionName = req.getPathInfo().replaceAll("/", "").toLowerCase();
        LOG.debug(String.format("Forward to %s", actionName));
        if ("".equals(actionName))
            return ACTIONS.get("home");
        Action result = ACTIONS.get(actionName);
        if (result == null)
            return ACTIONS.get("home");     // Change to 404 page
        return result;
    }
}
