package com.mv.schelokov.carent.actions.admin;

import com.mv.schelokov.carent.actions.interfaces.AbstractAction;
import com.mv.schelokov.carent.actions.JspForward;
import com.mv.schelokov.carent.actions.consts.Jsps;
import com.mv.schelokov.carent.actions.exceptions.ActionException;
import com.mv.schelokov.carent.model.services.UserDataService;
import com.mv.schelokov.carent.model.services.UserService;
import com.mv.schelokov.carent.model.services.exceptions.ServiceException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class UsersListAction extends AbstractAction {
    
    private static final String ERROR = "Failed to get the full list of users";
    private static final Logger LOG = Logger.getLogger(UsersListAction.class);

    @Override
    public JspForward execute(HttpServletRequest req, HttpServletResponse res) 
            throws ActionException {
        
        JspForward forward = new JspForward();
        
        if (isAdmin(req)) {
            try {
                req.setAttribute(USER_DATA, new UserDataService()
                        .getAllUsers());
                
                forward.setUrl(Jsps.ADMIN_USERS_LIST);
                
                return forward;
            } catch (ServiceException ex) {
                LOG.error(ERROR, ex);
                throw new ActionException(ERROR, ex);
            }

        } else {
            sendForbidden(res);
            return forward;
        }
    }

}
