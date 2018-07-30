package com.mv.schelokov.car_rent.actions.user;

import com.mv.schelokov.car_rent.actions.AbstractAction;
import com.mv.schelokov.car_rent.actions.JspForward;
import com.mv.schelokov.car_rent.consts.Jsps;
import com.mv.schelokov.car_rent.consts.SessionAttr;
import com.mv.schelokov.car_rent.exceptions.ActionException;
import com.mv.schelokov.car_rent.model.entity.RentOrder;
import com.mv.schelokov.car_rent.model.entity.User;
import com.mv.schelokov.car_rent.model.services.OrderService;
import com.mv.schelokov.car_rent.model.services.exceptions.ServiceException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class ShowWelcomePage extends AbstractAction {
    private static final Logger LOG = Logger.getLogger(ShowWelcomePage.class);
    private static final String ERROR = "Failed to get data from database";

    @Override
    public JspForward execute(HttpServletRequest req, HttpServletResponse res)
            throws ActionException {
        
        JspForward forward = new JspForward();
        
        if (isUser(req)) {
            User user = (User) req.getSession().getAttribute(SessionAttr.USER);
            try {
                boolean showButton = true;
                RentOrder order = OrderService.getInstance()
                        .getOrdersByUser(user);
                if (order != null && !order.getCar().isAvailable()) {
                    showButton = false;
                }
                req.setAttribute("show_button", showButton);
                
                forward.setUrl(Jsps.USER_WELCOME);
                
                return forward;
            }
            catch (ServiceException ex) {
                LOG.error(ERROR, ex);
                throw new ActionException(ERROR, ex);
            }
        } else {
            sendForbidden(res);
            return forward;
        }
    }
    
}
