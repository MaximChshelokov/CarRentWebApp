package com.mv.schelokov.car_rent.actions;

import com.mv.schelokov.car_rent.actions.admin.ApproveOrder;
import com.mv.schelokov.car_rent.actions.admin.CloseOrder;
import com.mv.schelokov.car_rent.actions.admin.DeleteCar;
import com.mv.schelokov.car_rent.actions.admin.DeleteOrder;
import com.mv.schelokov.car_rent.actions.admin.DeleteUser;
import com.mv.schelokov.car_rent.actions.admin.EditOrder;
import com.mv.schelokov.car_rent.actions.admin.SaveUser;
import com.mv.schelokov.car_rent.actions.admin.ShowAddCarPage;
import com.mv.schelokov.car_rent.actions.admin.ShowAddUserPage;
import com.mv.schelokov.car_rent.actions.admin.ShowAdminActionsPage;
import com.mv.schelokov.car_rent.actions.admin.ShowBillOrderPage;
import com.mv.schelokov.car_rent.actions.admin.ShowCarList;
import com.mv.schelokov.car_rent.actions.admin.ShowEditCarPage;
import com.mv.schelokov.car_rent.actions.admin.ShowEditUserPage;
import com.mv.schelokov.car_rent.actions.admin.ShowOpenedOrdersPage;
import com.mv.schelokov.car_rent.actions.admin.ShowOrderList;
import com.mv.schelokov.car_rent.actions.admin.ShowOrderViewPage;
import com.mv.schelokov.car_rent.actions.admin.ShowUsersList;
import com.mv.schelokov.car_rent.actions.admin.UpdateCar;
import com.mv.schelokov.car_rent.actions.admin.UpdateOrder;
import com.mv.schelokov.car_rent.actions.admin.UpdateUser;
import com.mv.schelokov.car_rent.actions.user.CreateOrder;
import com.mv.schelokov.car_rent.actions.user.PayInvoice;
import com.mv.schelokov.car_rent.actions.user.ShowEditProfilePage;
import com.mv.schelokov.car_rent.actions.user.ShowInvoicePage;
import com.mv.schelokov.car_rent.actions.user.ShowSelectCarPage;
import com.mv.schelokov.car_rent.actions.user.ShowWelcomePage;
import com.mv.schelokov.car_rent.actions.user.UpdateProfile;
import com.mv.schelokov.car_rent.consts.Jsps;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class ActionFactory {
    private static final Map<String, Action> ACTIONS = new HashMap<>();
    static {
        ACTIONS.put("home", new ShowPage(Jsps.HOME));
        ACTIONS.put("login", new Login());
        ACTIONS.put("sign_up", new SignUp());
        ACTIONS.put("log_out", new LogOut());
        ACTIONS.put("admin_actions", new ShowAdminActionsPage());
        ACTIONS.put("user_list", new ShowUsersList());
        ACTIONS.put("edit_user", new ShowEditUserPage());
        ACTIONS.put("update_user", new UpdateUser());
        ACTIONS.put("delete_user", new DeleteUser());
        ACTIONS.put("create_user", new ShowAddUserPage());
        ACTIONS.put("save_user", new SaveUser());
        ACTIONS.put("car_list", new ShowCarList());
        ACTIONS.put("edit_car", new ShowEditCarPage());
        ACTIONS.put("update_car", new UpdateCar());
        ACTIONS.put("delete_car", new DeleteCar());
        ACTIONS.put("create_car", new ShowAddCarPage());
        ACTIONS.put("order_list", new ShowOrderList());
        ACTIONS.put("view_order", new ShowOrderViewPage());
        ACTIONS.put("approve_order", new ApproveOrder());
        ACTIONS.put("edit_profile", new ShowEditProfilePage());
        ACTIONS.put("update_profile", new UpdateProfile());
        ACTIONS.put("select_car", new ShowSelectCarPage());
        ACTIONS.put("create_order", new CreateOrder());
        ACTIONS.put("order_completed", new ShowPage(Jsps.USER_ORDER_COMPLETED));
        ACTIONS.put("error_page", new ShowPage(Jsps.ERROR_PAGE));
        ACTIONS.put("delete_order", new DeleteOrder());
        ACTIONS.put("edit_order", new EditOrder());
        ACTIONS.put("update_order", new UpdateOrder());
        ACTIONS.put("opened_orders", new ShowOpenedOrdersPage());
        ACTIONS.put("bill_order", new ShowBillOrderPage());
        ACTIONS.put("close_order", new CloseOrder());
        ACTIONS.put("invoice", new ShowInvoicePage());
        ACTIONS.put("pay_check", new PayInvoice());
        ACTIONS.put("change_locale", new ChangeLocale());
        ACTIONS.put("welcome", new ShowWelcomePage());
    }
    
    public static Action action(HttpServletRequest req) {
        String actionName = req.getPathInfo().replaceAll("/", "").toLowerCase();
        Action result = ACTIONS.get(actionName);
        return result;
    }
}
