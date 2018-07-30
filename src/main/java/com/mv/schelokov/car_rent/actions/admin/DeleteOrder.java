package com.mv.schelokov.car_rent.actions.admin;

import com.mv.schelokov.car_rent.actions.AbstractAction;
import com.mv.schelokov.car_rent.actions.JspForward;
import com.mv.schelokov.car_rent.exceptions.ActionException;
import com.mv.schelokov.car_rent.model.entity.builders.RentOrderBuilder;
import com.mv.schelokov.car_rent.model.services.OrderService;
import com.mv.schelokov.car_rent.model.services.exceptions.ServiceException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class DeleteOrder extends AbstractAction {
    private static final Logger LOG = Logger.getLogger(DeleteOrder.class);
    private static final String ERROR = "Unable to delete the order";

    @Override
    public JspForward execute(HttpServletRequest req, HttpServletResponse res)
            throws ActionException {
        
        JspForward forward = new JspForward();
        
        if (isAdmin(req)) {
            int orderId = getIntParam(req, "id");
            if (orderId < 1) {
                throw new ActionException("Failed to delete order, wrong id");
            }
            try {
                OrderService.getInstance().deleteOrder(new RentOrderBuilder()
                        .setId(orderId)
                        .getRentOrder());
                
                forward.setUrl("action/order_list");
                forward.setRedirect(true);
                
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
