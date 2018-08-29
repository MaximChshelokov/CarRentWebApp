package com.mv.schelokov.carent.actions.factory;

import com.mv.schelokov.carent.actions.interfaces.Action;
import com.mv.schelokov.carent.actions.ChangeLocaleAction;
import com.mv.schelokov.carent.actions.LogOutAction;
import com.mv.schelokov.carent.actions.LoginAction;
import com.mv.schelokov.carent.actions.ShowPageAction;
import com.mv.schelokov.carent.actions.SignUpAction;
import com.mv.schelokov.carent.actions.admin.ApproveOrderAction;
import com.mv.schelokov.carent.actions.admin.CloseOrderAction;
import com.mv.schelokov.carent.actions.admin.DeleteCarAction;
import com.mv.schelokov.carent.actions.admin.DeleteOrderAction;
import com.mv.schelokov.carent.actions.admin.DeleteUserAction;
import com.mv.schelokov.carent.actions.admin.EditOrderAction;
import com.mv.schelokov.carent.actions.admin.SaveUserAction;
import com.mv.schelokov.carent.actions.admin.CreateCarPageAction;
import com.mv.schelokov.carent.actions.admin.CreateUserPageAction;
import com.mv.schelokov.carent.actions.admin.AdminActionsPageAction;
import com.mv.schelokov.carent.actions.admin.BillOrderPageAction;
import com.mv.schelokov.carent.actions.admin.CarListAction;
import com.mv.schelokov.carent.actions.admin.EditCarPageAction;
import com.mv.schelokov.carent.actions.admin.EditUserPageAction;
import com.mv.schelokov.carent.actions.admin.OpenedOrdersPageAction;
import com.mv.schelokov.carent.actions.admin.OrderListAction;
import com.mv.schelokov.carent.actions.admin.OrderViewPageAction;
import com.mv.schelokov.carent.actions.admin.RejectionReasonPageAction;
import com.mv.schelokov.carent.actions.admin.UsersListAction;
import com.mv.schelokov.carent.actions.admin.UpdateCarAction;
import com.mv.schelokov.carent.actions.admin.UpdateOrderAction;
import com.mv.schelokov.carent.actions.admin.UpdateRejectionReasonAction;
import com.mv.schelokov.carent.actions.admin.UpdateUserAction;
import com.mv.schelokov.carent.actions.consts.Actions;
import com.mv.schelokov.carent.actions.user.CreateOrderAction;
import com.mv.schelokov.carent.actions.user.PayInvoiceAction;
import com.mv.schelokov.carent.actions.user.EditProfilePageAction;
import com.mv.schelokov.carent.actions.user.InvoicePageAction;
import com.mv.schelokov.carent.actions.user.SelectCarPageAction;
import com.mv.schelokov.carent.actions.user.WelcomePageAction;
import com.mv.schelokov.carent.actions.user.UpdateProfileAction;
import com.mv.schelokov.carent.actions.consts.Jsps;
import com.mv.schelokov.carent.actions.user.AlreadyRentedPageAction;
import com.mv.schelokov.carent.actions.user.ProceedToOrderAction;
import com.mv.schelokov.carent.actions.user.UserDataPageAction;
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
        ACTIONS.put(Actions.HOME, new ShowPageAction(Jsps.HOME));
        ACTIONS.put(Actions.LOGIN, new LoginAction());
        ACTIONS.put(Actions.SIGN_UP, new SignUpAction());
        ACTIONS.put(Actions.LOG_OUT, new LogOutAction());
        ACTIONS.put(Actions.ADMIN_ACTIONS, new AdminActionsPageAction());
        ACTIONS.put(Actions.USER_LIST, new UsersListAction());
        ACTIONS.put(Actions.EDIT_USER, new EditUserPageAction());
        ACTIONS.put(Actions.UPDATE_USER, new UpdateUserAction());
        ACTIONS.put(Actions.DELETE_USER, new DeleteUserAction());
        ACTIONS.put(Actions.CREATE_USER, new CreateUserPageAction());
        ACTIONS.put(Actions.SAVE_USER, new SaveUserAction());
        ACTIONS.put(Actions.CAR_LIST, new CarListAction());
        ACTIONS.put(Actions.EDIT_CAR, new EditCarPageAction());
        ACTIONS.put(Actions.UPDATE_CAR, new UpdateCarAction());
        ACTIONS.put(Actions.DELETE_CAR, new DeleteCarAction());
        ACTIONS.put(Actions.CREATE_CAR, new CreateCarPageAction());
        ACTIONS.put(Actions.ORDER_LIST, new OrderListAction());
        ACTIONS.put(Actions.VIEW_ORDER, new OrderViewPageAction());
        ACTIONS.put(Actions.APPROVE_ORDER, new ApproveOrderAction());
        ACTIONS.put(Actions.EDIT_PROFILE, new EditProfilePageAction());
        ACTIONS.put(Actions.UPDATE_PROFILE, new UpdateProfileAction());
        ACTIONS.put(Actions.USER_DATA, new UserDataPageAction());
        ACTIONS.put(Actions.PROCEED_TO_ORDER, new ProceedToOrderAction());
        ACTIONS.put(Actions.SELECT_CAR, new SelectCarPageAction());
        ACTIONS.put(Actions.CREATE_ORDER, new CreateOrderAction());
        ACTIONS.put(Actions.ORDER_COMPLETED, new ShowPageAction(
                Jsps.USER_ORDER_COMPLETED));
        ACTIONS.put(Actions.ERROR_PAGE, new ShowPageAction(Jsps.ERROR_PAGE));
        ACTIONS.put(Actions.DELETE_ORDER, new DeleteOrderAction());
        ACTIONS.put(Actions.EDIT_ORDER, new EditOrderAction());
        ACTIONS.put(Actions.UPDATE_ORDER, new UpdateOrderAction());
        ACTIONS.put(Actions.OPENED_ORDERS, new OpenedOrdersPageAction());
        ACTIONS.put(Actions.BILL_ORDER, new BillOrderPageAction());
        ACTIONS.put(Actions.CLOSE_ORDER, new CloseOrderAction());
        ACTIONS.put(Actions.INVOICE, new InvoicePageAction());
        ACTIONS.put(Actions.PAY_CHECK, new PayInvoiceAction());
        ACTIONS.put(Actions.CHANGE_LOCALE, new ChangeLocaleAction());
        ACTIONS.put(Actions.WELCOME, new WelcomePageAction());
        ACTIONS.put(Actions.ALREADY_RENTED, new AlreadyRentedPageAction());
        ACTIONS.put(Actions.REJECTION_REASON, new RejectionReasonPageAction());
        ACTIONS.put(Actions.UPDATE_REASON, new UpdateRejectionReasonAction());
    }
    
    public Action createAction(HttpServletRequest req) {
        String actionName = req.getPathInfo().replaceAll("/", "").toLowerCase();
        Action result = ACTIONS.get(actionName);
        return result;
    }
}
