package com.mv.schelokov.carent.actions.admin;

import com.mv.schelokov.carent.actions.interfaces.AbstractAction;
import com.mv.schelokov.carent.actions.JspForward;
import com.mv.schelokov.carent.actions.consts.SessionAttr;
import com.mv.schelokov.carent.actions.exceptions.ActionException;
import com.mv.schelokov.carent.model.entity.Car;
import com.mv.schelokov.carent.model.entity.RentOrder;
import com.mv.schelokov.carent.model.entity.User;
import com.mv.schelokov.carent.model.services.CarService;
import com.mv.schelokov.carent.model.services.InvoiceService;
import com.mv.schelokov.carent.model.services.OrderService;
import com.mv.schelokov.carent.model.services.exceptions.ServiceException;
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
    private static final String WRONG_ID = "Wrong id parameter for order entity";

    @Override
    public JspForward execute(HttpServletRequest req, HttpServletResponse res)
            throws ActionException {
        
        JspForward forward = new JspForward();
        if (isAdmin(req)) {
            int orderId = getIntParam(req, "id");
            if (orderId < 1) {
                throw new ActionException(WRONG_ID);
            }
            try {
                OrderService orderService = OrderService.getInstance();
                RentOrder order = (RentOrder) orderService.getOrderById(orderId);
                order.setApprovedBy((User) req.getSession()
                        .getAttribute(SessionAttr.USER));
                orderService.updateOrder(order);
                
                CarService carService = CarService.getInstance();

                Car car = carService.getCarById(order.getCar().getId());
                car.setAvailable(false);
                carService.updateCar(car);
                
                InvoiceService.getInstance().openNewInvoice(order);
                
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
