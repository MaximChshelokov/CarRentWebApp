package com.mv.schelokov.carent.actions.consts;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class Actions {
    public static final String HOME = "home";
    public static final String LOGIN = "login";
    public static final String SIGN_UP = "sign_up";
    public static final String LOG_OUT = "log_out";
    public static final String ADMIN_ACTIONS = "admin_actions";
    public static final String USER_LIST = "user_list";
    public static final String EDIT_USER = "edit_user";
    public static final String UPDATE_USER = "update_user";
    public static final String DELETE_USER = "delete_user";
    public static final String CREATE_USER = "create_user";
    public static final String SAVE_USER = "save_user";
    public static final String CAR_LIST = "car_list";
    public static final String EDIT_CAR = "edit_car";
    public static final String UPDATE_CAR = "update_car";
    public static final String DELETE_CAR = "delete_car";
    public static final String CREATE_CAR = "create_car";
    public static final String ORDER_LIST = "order_list";
    public static final String VIEW_ORDER = "view_order";
    public static final String APPROVE_ORDER = "approve_order";
    public static final String EDIT_PROFILE = "edit_profile";
    public static final String UPDATE_PROFILE = "update_profile";
    public static final String USER_DATA = "user_data";
    public static final String PROCEED_TO_ORDER = "proceed_to_order";
    public static final String SELECT_CAR = "select_car";
    public static final String CREATE_ORDER = "create_order";
    public static final String ORDER_COMPLETED = "order_completed";
    public static final String ERROR_PAGE = "error_page";
    public static final String DELETE_ORDER = "delete_order";
    public static final String EDIT_ORDER = "edit_order";
    public static final String UPDATE_ORDER = "update_order";
    public static final String OPENED_ORDERS = "opened_orders";
    public static final String BILL_ORDER = "bill_order";
    public static final String CLOSE_ORDER = "close_order";
    public static final String INVOICE = "invoice";
    public static final String PAY_CHECK = "pay_check";
    public static final String CHANGE_LOCALE = "change_locale";
    public static final String WELCOME = "welcome";
    public static final String ALREADY_RENTED = "already_rented";
    public static final String REJECTION_REASON = "rejection_reason";
    public static final String UPDATE_REASON = "update_reason";
    private static final String ACTION = "action";
    
    public static String getActionName(String action) {
        return String.format("%s/%s", ACTION, action);
    }
}
