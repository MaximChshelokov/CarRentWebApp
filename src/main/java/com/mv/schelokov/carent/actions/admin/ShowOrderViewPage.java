package com.mv.schelokov.carent.actions.admin;

import com.mv.schelokov.carent.actions.interfaces.AbstractAction;
import com.mv.schelokov.carent.actions.JspForward;
import com.mv.schelokov.carent.actions.consts.Jsps;
import com.mv.schelokov.carent.actions.exceptions.ActionException;
import com.mv.schelokov.carent.model.entity.RentOrder;
import com.mv.schelokov.carent.model.services.CarService;
import com.mv.schelokov.carent.model.services.OrderService;
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
public class ShowOrderViewPage extends AbstractAction {
    
    private static final Logger LOG = Logger.getLogger(ShowOrderViewPage.class);
    private static final String ERROR = "Failed to get data from database";

    @Override
    public JspForward execute(HttpServletRequest req, HttpServletResponse res)
            throws ActionException {
        
        JspForward forward = new JspForward();
                
        if (isAdmin(req)) {
            int orderId = getIntParam(req, "id");
            try {
                RentOrder order = OrderService.getInstance()
                        .getOrderById(orderId);
                CarService carService = CarService.getInstance();

                req.setAttribute("order", order);
                req.setAttribute("user_data", 
                        UserDataService.getInstance()
                                .getUserDataById(order.getUser().getId()));
                req.setAttribute("car",
                        carService.getCarById(order.getCar().getId()));
                
                forward.setUrl(Jsps.ADMIN_ORDER_VIEW);
                
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
