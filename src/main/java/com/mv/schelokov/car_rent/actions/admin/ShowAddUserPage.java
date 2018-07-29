package com.mv.schelokov.car_rent.actions.admin;

import com.mv.schelokov.car_rent.actions.AbstractAction;
import com.mv.schelokov.car_rent.actions.JspForward;
import com.mv.schelokov.car_rent.consts.Jsps;
import com.mv.schelokov.car_rent.exceptions.ActionException;
import com.mv.schelokov.car_rent.model.entity.User;
import com.mv.schelokov.car_rent.model.entity.builders.UserDataBuilder;
import com.mv.schelokov.car_rent.model.services.UserService;
import com.mv.schelokov.car_rent.model.services.exceptions.ServiceException;
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
                req.setAttribute("roles", UserService.getAllRoles());
                
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
