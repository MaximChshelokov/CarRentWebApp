package com.mv.schelokov.carent.actions.admin;

import com.mv.schelokov.carent.actions.interfaces.AbstractAction;
import com.mv.schelokov.carent.actions.JspForward;
import com.mv.schelokov.carent.actions.exceptions.ActionException;
import com.mv.schelokov.carent.model.entity.Car;
import com.mv.schelokov.carent.model.entity.RentOrder;
import com.mv.schelokov.carent.model.services.CarService;
import com.mv.schelokov.carent.model.services.OrderService;
import com.mv.schelokov.carent.model.services.exceptions.ServiceException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class CloseOrder extends AbstractAction {
    
    private static final Logger LOG = Logger.getLogger(CloseOrder.class);
    private static final String ERROR = "Failed to close order";
    private static final String WRONG_ID = "Wrong id parameter for invoice entity";

    @Override
    public JspForward execute(HttpServletRequest req, HttpServletResponse res)
            throws ActionException {
        
        JspForward forward = new JspForward();
        if (isAdmin(req)) {
            int invoiceId = getIntParam(req, "id");
            if (invoiceId < 1) {
                throw new ActionException(WRONG_ID);
            }
            try {
                RentOrder order = (RentOrder) new OrderService()
                        .getOrderById(invoiceId);
                
                CarService carService = new CarService();
                
                Car car = carService.getCarById(order.getCar().getId());
                car.setAvailable(true);
                carService.updateCar(car);
                
                forward.setUrl("action/opened_orders");
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
