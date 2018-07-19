package com.mv.schelokov.car_rent.controller.actions.admin;

import com.mv.schelokov.car_rent.controller.actions.AbstractAction;
import com.mv.schelokov.car_rent.controller.actions.JspForward;
import com.mv.schelokov.car_rent.controller.consts.SessionAttr;
import com.mv.schelokov.car_rent.controller.exceptions.ActionException;
import com.mv.schelokov.car_rent.model.entities.RentOrder;
import com.mv.schelokov.car_rent.model.entities.User;
import com.mv.schelokov.car_rent.model.services.OrderService;
import com.mv.schelokov.car_rent.model.services.exceptions.ServiceException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class ApproveOrder extends AbstractAction {
    
    private static final Logger LOG = Logger.getLogger(ApproveOrder.class);
    private static final String ERROR = "Failed to update order";

    @Override
    public JspForward execute(HttpServletRequest req, HttpServletResponse res)
            throws ActionException {
        
        if (isAdmin(req)) {
            int orderId = getIntParam(req, "id");
            if (orderId < 1) {
                throw new ActionException("Wrong id parameter for order entity");
            }
            OrderService orderService = new OrderService();
            try {
                RentOrder order = (RentOrder) orderService.getOrderById(orderId);
                order.setApprovedBy((User) req.getSession()
                        .getAttribute(SessionAttr.USER));
                orderService.updateOrder(order);
                return new JspForward("action/order_list", true);
            } catch (ServiceException ex) {
                LOG.error(ERROR, ex);
                throw new ActionException(ERROR, ex);
            }
        } else {
            sendForbidden(res);
            return null;
        }
    }
    
}
