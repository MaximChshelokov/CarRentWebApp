package com.mv.schelokov.car_rent.controller.actions;

import com.mv.schelokov.car_rent.controller.exceptions.ActionException;
import com.mv.schelokov.car_rent.model.entities.User;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public abstract class AbstractAction implements Action {
    
    private static final Logger log = Logger.getLogger(AbstractAction.class);
    private static final String SEND_ERROR = "Failed to send an HTTP error";
    private static final int ADMIN_ID = 1;
    private static final int USER_ID = 2;
    
    public boolean isAdmin(HttpServletRequest req) {
        return isUserLogged(req, ADMIN_ID);
    }
    
    public boolean isUser(HttpServletRequest req) {
        return isUserLogged(req, USER_ID);
    }
    
    public Object pickSessionAttribute(HttpServletRequest req,
            String attributeName) {
        HttpSession session = req.getSession(false);
        if (session != null) {
            Object result = session.getAttribute(attributeName);
            if (result != null)
                session.removeAttribute(attributeName);
            return result;
        }
        return null;
    }
    
    public int getIntParam(HttpServletRequest req, String name) {
        String param = req.getParameter(name);
        if (param != null && param.length() > 0)
            return Integer.parseInt(param);
        else
            return 0;
    }
    
    public boolean isUserLogged(HttpServletRequest req, int roleId) {
        HttpSession session = req.getSession(false);
        if (session != null) {
            User user = (User)session.getAttribute("user");
            return user != null && user.getRole().getId() == roleId;
        }
        return false;
    }
    
    public void sendForbidden(HttpServletResponse res) throws ActionException {
        try {
            res.sendError(403);
        }
        catch (IOException ex) {
            log.error(SEND_ERROR, ex);
            throw new ActionException(SEND_ERROR, ex);
        }
    }
}
