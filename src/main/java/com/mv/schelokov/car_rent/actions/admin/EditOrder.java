package com.mv.schelokov.car_rent.actions.admin;

import com.mv.schelokov.car_rent.actions.AbstractAction;
import com.mv.schelokov.car_rent.actions.JspForward;
import com.mv.schelokov.car_rent.actions.user.CreateOrder;
import com.mv.schelokov.car_rent.consts.Jsps;
import com.mv.schelokov.car_rent.exceptions.ActionException;
import com.mv.schelokov.car_rent.model.entity.RentOrder;
import com.mv.schelokov.car_rent.model.services.CarService;
import com.mv.schelokov.car_rent.model.services.OrderService;
import com.mv.schelokov.car_rent.model.services.exceptions.ServiceException;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class EditOrder extends AbstractAction {
    private static final Logger LOG = Logger.getLogger(EditOrder.class);
    private static final String ERROR = "Failed to prepare rent order edit page";
    private static final SimpleDateFormat FORMAT
            = new SimpleDateFormat("yyyy-MM-dd");
    @Override
    public JspForward execute(HttpServletRequest req, HttpServletResponse res)
            throws ActionException {
        
        JspForward forward = new JspForward();
        
        if (isAdmin(req)) {
            int id = getIntParam(req, "id");
            if (id < 1)
                throw new ActionException("Invalid order id");
            try {
                RentOrder order = OrderService.getOrderById(id);
                
                CarService carService = CarService.getInstance();
                
                req.setAttribute("car_list", carService.getAvailableCars());
                req.setAttribute("order", order);
                req.setAttribute("start_date", 
                        FORMAT.format(order.getStartDate()));
                req.setAttribute("end_date", FORMAT.format(order.getEndDate()));
                req.setAttribute("action", String.format("update_order?id=%d",
                        order.getId()));
                
                forward.setUrl(Jsps.USER_SELECT_CAR);

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
