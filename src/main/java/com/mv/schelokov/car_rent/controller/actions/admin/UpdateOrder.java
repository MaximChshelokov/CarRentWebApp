package com.mv.schelokov.car_rent.controller.actions.admin;

import com.mv.schelokov.car_rent.controller.actions.AbstractAction;
import com.mv.schelokov.car_rent.controller.actions.JspForward;
import com.mv.schelokov.car_rent.controller.consts.Jsps;
import com.mv.schelokov.car_rent.controller.exceptions.ActionException;
import com.mv.schelokov.car_rent.model.entities.RentOrder;
import com.mv.schelokov.car_rent.model.services.CarService;
import com.mv.schelokov.car_rent.model.services.OrderService;
import com.mv.schelokov.car_rent.model.services.exceptions.ServiceException;
import com.mv.schelokov.car_rent.model.validators.RentOrderValidator;
import com.mv.schelokov.car_rent.model.validators.ValidationResult;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class UpdateOrder extends AbstractAction {
    
    private static final Logger LOG = Logger.getLogger(UpdateOrder.class);
    private static final String ERROR = "Failed to update the rent order";
    private static final SimpleDateFormat FORMAT
            = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public JspForward execute(HttpServletRequest req, HttpServletResponse res)
            throws ActionException {
        if (isAdmin(req)) {

            int orderId = getIntParam(req, "id");
            if (orderId < 1)
                throw new ActionException("Incorrect rent order id");
            try {
                RentOrder order = OrderService.getOrderById(orderId);
                order.getCar().setId(getIntParam(req, "selected_car"));

                int validationResult = ValidationResult.OK;

                try {
                    order.setStartDate(FORMAT.parse(req.getParameter("start_date")));
                    order.setEndDate(FORMAT.parse(req.getParameter("end_date")));
                } catch (ParseException ex) {
                    LOG.error("Date parse error!");
                    validationResult = ValidationResult.INVALID_DATE;
                }
                
                if (validationResult == ValidationResult.OK)
                    validationResult = RentOrderValidator.validate(order);
                if (validationResult == ValidationResult.OK) {
                    OrderService.updateOrder(order);
                    return new JspForward("action/order_list", true);
                }
                req.setAttribute("errParam", validationResult);
                req.setAttribute("car_list", CarService.getAvailableCars());
                req.setAttribute("order", order);
                req.setAttribute("start_date", FORMAT.format(order.getStartDate()));
                req.setAttribute("end_date", FORMAT.format(order.getEndDate()));
                req.setAttribute("action", String.format("update_order?id=%d",
                        order.getId()));
                return new JspForward(Jsps.USER_SELECT_CAR);
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
