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
public class ShowEditUserPage extends AbstractAction {
    
    private static final Logger log = Logger.getLogger(ShowEditUserPage.class);
    private static final String ERROR = "Unable to prepare an edit page for user";

    @Override
    public JspForward execute(HttpServletRequest req, HttpServletResponse res)
            throws ActionException {
        if (isAdmin(req)) {
            UserService userService = new UserService();
            try {
                int id = getIntParam(req, "id");
                req.getSession().setAttribute("user_data", userService.getUserDataById(id));
                return new JspForward("WEB-INF/jsp/admin/edit_user.jsp");
            }
            catch (ServiceException ex) {
                log.error(ERROR, ex);
                throw new ActionException(ERROR, ex);
            }
        }
        sendForbidden(res);
        return null;
    }
}
