package com.mv.schelokov.car_rent.controller.actions.user;

import com.mv.schelokov.car_rent.controller.actions.AbstractAction;
import com.mv.schelokov.car_rent.controller.actions.JspForward;
import com.mv.schelokov.car_rent.controller.consts.Jsps;
import com.mv.schelokov.car_rent.controller.consts.SessionAttr;
import com.mv.schelokov.car_rent.controller.exceptions.ActionException;
import com.mv.schelokov.car_rent.model.entities.RentOrder;
import com.mv.schelokov.car_rent.model.entities.User;
import com.mv.schelokov.car_rent.model.entities.builders.CarBuilder;
import com.mv.schelokov.car_rent.model.entities.builders.RentOrderBuilder;
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
public class CreateOrder extends AbstractAction {
    private static final Logger LOG = Logger.getLogger(CreateOrder.class);
    private static final String ERROR = "Failed to create the rent order";
    private static final SimpleDateFormat FORMAT = 
            new SimpleDateFormat("yyyy-MM-dd");
    
    @Override
    public JspForward execute(HttpServletRequest req, HttpServletResponse res)
            throws ActionException {
        if (isUser(req) || isAdmin(req)) {
            User user = (User) req.getSession().getAttribute(SessionAttr.USER);
            
            RentOrder order = new RentOrderBuilder()
                    .setUser(user)
                    .setCar(new CarBuilder()
                            .setId(getIntParam(req, "selected_car"))
                            .getCar())
                    .getRentOrder();
            String start = req.getParameter("start_date");
            String end = req.getParameter("end_date");
            
            int validationResult = ValidationResult.OK;
            
            try {
                order.setStartDate(FORMAT.parse(start));
                order.setEndDate(FORMAT.parse(end));
            } catch (ParseException ex) {
                validationResult = ValidationResult.INVALID_DATE;
            }
            
            if (validationResult == ValidationResult.OK) 
                validationResult = RentOrderValidator.validate(order);
            
            try {
                if (validationResult == ValidationResult.OK) {

                    OrderService.addOrder(order);
                    return new JspForward("action/order_completed", true);
                }
                req.setAttribute("errParam", validationResult);
                req.setAttribute("car_list", CarService.getAvailableCars());
                req.setAttribute("order", order);
                req.setAttribute("start_date", FORMAT.format(order.getStartDate()));
                req.setAttribute("end_date", FORMAT.format(order.getEndDate()));
                return new JspForward(Jsps.USER_SELECT_CAR);
            }
            catch (ServiceException ex) {
                LOG.error(ERROR, ex);
                throw new ActionException(ERROR, ex);
            }

        } else {
            sendForbidden(res);
            return null;
        }
    }
}
