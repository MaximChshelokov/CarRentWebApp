package com.mv.schelokov.car_rent.controller.actions.admin;

import com.mv.schelokov.car_rent.controller.actions.AbstractAction;
import com.mv.schelokov.car_rent.controller.actions.JspForward;
import com.mv.schelokov.car_rent.controller.consts.Jsps;
import com.mv.schelokov.car_rent.controller.exceptions.ActionException;
import com.mv.schelokov.car_rent.model.entities.RentOrder;
import com.mv.schelokov.car_rent.model.services.CarService;
import com.mv.schelokov.car_rent.model.services.OrderService;
import com.mv.schelokov.car_rent.model.services.UserService;
import com.mv.schelokov.car_rent.model.services.exceptions.ServiceException;
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
        if (isAdmin(req)) {
            UserService userService = new UserService();
            CarService carService = new CarService();
            OrderService orderService = new OrderService();
            int orderId = getIntParam(req, "id");
            if (orderId < 1)
                throw new ActionException("Wrong id parameter for order entity");
            try {
                RentOrder order = orderService.getOrderById(orderId);
                req.setAttribute("order", order);
                req.setAttribute("user_data", 
                        userService.getUserDataById(order.getUser().getId()));
                req.setAttribute("car",
                        carService.getCarById(order.getCar().getId()));
                return new JspForward(Jsps.ADMIN_ORDER_VIEW);
                
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
