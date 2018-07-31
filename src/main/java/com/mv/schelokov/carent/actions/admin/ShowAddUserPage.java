package com.mv.schelokov.carent.actions.admin;

import com.mv.schelokov.carent.actions.interfaces.AbstractAction;
import com.mv.schelokov.carent.actions.JspForward;
import com.mv.schelokov.carent.actions.consts.Jsps;
import com.mv.schelokov.carent.actions.exceptions.ActionException;
import com.mv.schelokov.carent.model.entity.User;
import com.mv.schelokov.carent.model.entity.builders.UserDataBuilder;
import com.mv.schelokov.carent.model.services.RoleService;
import com.mv.schelokov.carent.model.services.UserService;
import com.mv.schelokov.carent.model.services.exceptions.ServiceException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class ShowAddUserPage extends AbstractAction {
    private static final Logger LOG = Logger.getLogger(ShowAddUserPage.class);
    private static final String ERROR = "Unable to get roles list";
    
    @Override
    public JspForward execute(HttpServletRequest req, HttpServletResponse res)
            throws ActionException {
        
        JspForward forward = new JspForward();
        
        if (isAdmin(req)) {
            req.setAttribute("user_data",
                    new UserDataBuilder().setUser(new User()).getUserData());
            try {
                
                req.setAttribute("roles", RoleService.getInstance()
                        .getAllRoles());
                
                forward.setUrl(Jsps.ADMIN_ADD_USER);
                
                return forward;
            } catch (ServiceException ex) {
                LOG.error(ERROR, ex);
                throw new ActionException(ERROR, ex);
            }
        }
        sendForbidden(res);
        return forward;
    }
}
