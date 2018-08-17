package com.mv.schelokov.carent.actions.interfaces;

import com.mv.schelokov.carent.actions.consts.SessionAttr;
import com.mv.schelokov.carent.actions.exceptions.ActionException;
import com.mv.schelokov.carent.model.entity.User;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public abstract class AbstractAction implements Action {
    
    private static final Logger LOG = Logger.getLogger(AbstractAction.class);
    private static final String SEND_ERROR = "Failed to send an HTTP error";
    private static final String NUMER_PARSE_ERROR = "Numeric parameter parse "
            + "error";
    protected static final String WRONG_ID = "Wrong id parameter for order entity";

    private static final int ADMIN_ID = 1;
    private static final int USER_ID = 2;
    
    protected static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    protected static final String ID = "id";
    protected static final String CAR = "car";
    protected static final String INVOICE = "invoice";
    protected static final String INVOICE_LINES = "invoice_lines";
    protected static final String USER_DATA = "user_data";
    protected static final String CAR_LIST = "car_list";
    protected static final String ROLES = "roles";
    protected static final String UID = "uid";
    protected static final String END_DATE = "end_date";
    protected static final String ORDER = "order";
    protected static final String START_DATE = "start_date";
    protected static final String ORDER_LIST = "order_list";
    protected static final String ERROR_NUMBER = "errParam";
    protected static final String LOGIN = "login";
    protected static final String SELECTED_CAR = "selected_car";
    protected static final String ADDRESS = "address";
    protected static final String NAME = "name";
    protected static final String PHONE = "phone";
    protected static final String PASSWORD = "password";
    protected static final String REGEX_NUMBER = "[^0-9]+";
    protected static final String REPEAT_PASSWORD = "repeat";
    protected static final String SIGN_UP = "sign";
    protected static final String USER_EDIT = "user_edit";
    
    
    public boolean isAdmin(HttpServletRequest req) {
        return isUserLogged(req, ADMIN_ID);
    }
    
    public boolean isUser(HttpServletRequest req) {
        return isUserLogged(req, USER_ID);
    }
    
    public int getIntParam(HttpServletRequest req, String name) {
        String param = req.getParameter(name);
        if (param != null && param.length() > 0)
            try {
                return Integer.parseInt(param);
            } catch (NumberFormatException ex) {
                LOG.warn(NUMER_PARSE_ERROR, ex);
                return -1;
            }
        else
            return 0;
    }
    
    public boolean isUserLogged(HttpServletRequest req, int roleId) {
        HttpSession session = req.getSession(false);
        if (session != null) {
            User user = (User)session.getAttribute(SessionAttr.USER);
            return user != null && user.getRole().getId() == roleId;
        }
        return false;
    }
    
    public void sendForbidden(HttpServletResponse res) throws ActionException {
        try {
            res.sendError(HttpURLConnection.HTTP_FORBIDDEN);
        }
        catch (IOException ex) {
            LOG.error(SEND_ERROR, ex);
            throw new ActionException(SEND_ERROR, ex);
        }
    }
}
