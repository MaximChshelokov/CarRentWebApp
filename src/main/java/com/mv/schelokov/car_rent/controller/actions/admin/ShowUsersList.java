package com.mv.schelokov.car_rent.controller.actions.admin;

import com.mv.schelokov.car_rent.controller.actions.AbstractAction;
import com.mv.schelokov.car_rent.controller.actions.JspForward;
import com.mv.schelokov.car_rent.controller.exceptions.ActionException;
import com.mv.schelokov.car_rent.model.services.UserService;
import com.mv.schelokov.car_rent.model.services.exceptions.ServiceException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class ShowUsersList extends AbstractAction {
    
    private static final String ERROR = "Failed to get the full list of users";
    private static final Logger log = Logger.getLogger(ShowUsersList.class);

    @Override
    public JspForward execute(HttpServletRequest req, HttpServletResponse res) 
            throws ActionException {
        if (isAdmin(req)) {
            UserService userService = new UserService();
            try {
                req.setAttribute("user_data", userService.getAllUsers());
                return new JspForward("WEB-INF/jsp/admin/user_list.jsp");
            } catch (ServiceException ex) {
                log.error(ERROR, ex);
                throw new ActionException(ERROR, ex);
            }

        } else {
            sendForbidden(res);
            return null;
        }
    }

}
