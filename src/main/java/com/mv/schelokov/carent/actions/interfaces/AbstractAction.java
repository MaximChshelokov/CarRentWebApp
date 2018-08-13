package com.mv.schelokov.carent.actions.interfaces;

import com.mv.schelokov.carent.actions.consts.SessionAttr;
import com.mv.schelokov.carent.actions.exceptions.ActionException;
import com.mv.schelokov.carent.model.entity.User;
import java.io.IOException;
import java.net.HttpURLConnection;
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
    private static final int ADMIN_ID = 1;
    private static final int USER_ID = 2;
    
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
