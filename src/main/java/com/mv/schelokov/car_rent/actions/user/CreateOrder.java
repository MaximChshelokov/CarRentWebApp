package com.mv.schelokov.car_rent.actions.user;

import com.mv.schelokov.car_rent.actions.AbstractAction;
import com.mv.schelokov.car_rent.actions.JspForward;
import com.mv.schelokov.car_rent.consts.Jsps;
import com.mv.schelokov.car_rent.consts.SessionAttr;
import com.mv.schelokov.car_rent.exceptions.ActionException;
import com.mv.schelokov.car_rent.model.entity.RentOrder;
import com.mv.schelokov.car_rent.model.entity.User;
import com.mv.schelokov.car_rent.model.entity.builders.CarBuilder;
import com.mv.schelokov.car_rent.model.entity.builders.RentOrderBuilder;
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
        
        JspForward forward = new JspForward();
        
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
                validationResult = new RentOrderValidator(order).validate();
            
            try {
                if (validationResult == ValidationResult.OK) {

                    OrderService.addOrder(order);
                    
                    forward.setUrl("action/order_completed");
                    forward.setRedirect(true);
                    
                    return forward;
                }
                
                CarService carService = CarService.getInstance();
                
                req.setAttribute("errParam", validationResult);
                req.setAttribute("car_list", carService.getAvailableCars());
                req.setAttribute("order", order);
                req.setAttribute("start_date", FORMAT.format(order.getStartDate()));
                req.setAttribute("end_date", FORMAT.format(order.getEndDate()));
                req.setAttribute("action", "create_order");
                
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
