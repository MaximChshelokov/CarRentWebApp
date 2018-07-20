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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
    private static final OrderService ORDER_SERVICE = new OrderService();
    private static final CarService CAR_SERVICE = new CarService();
    private static final SimpleDateFormat FORMAT = 
            new SimpleDateFormat("yyyy-MM-dd");
    private static final int OK = 0;
    private static final int EMPTY_PARAM = 1;
    private static final int WRONG_DATE = 2;
    
    @Override
    public JspForward execute(HttpServletRequest req, HttpServletResponse res)
            throws ActionException {
        if (isUser(req) || isAdmin(req)) {
            int car = getIntParam(req, "selected_car");
            String start = req.getParameter("start_date");
            String end = req.getParameter("end_date");
            User user = (User) req.getSession().getAttribute(SessionAttr.USER);
            int validationResult = validate(car, start, end);
            
            Date startDate = new Date();
            Date endDate = new Date();
            
            if (validationResult == OK) {
                try {
                    startDate = FORMAT.parse(start);
                    endDate = FORMAT.parse(end);
                } catch (ParseException ex) {
                    validationResult = WRONG_DATE;
                }
                Date now = dateRemoveTime(new Date());
                LOG.debug(String.format("now > startDate = %s", now.after(startDate)));
                if (now.after(startDate) || startDate.after(endDate))
                    validationResult = WRONG_DATE;
            }
            
            RentOrder order = new RentOrderBuilder()
                    .setCar(new CarBuilder()
                            .setId(car)
                            .getCar())
                    .setStartDate(startDate)
                    .setEndDate(endDate)
                    .setUser(user)
                    .getRentOrder();

            try {
                if (validationResult == OK) {

                    ORDER_SERVICE.addOrder(order);
                    return new JspForward("action/order_completed", true);
                }
                req.setAttribute("errParam", validationResult);
                req.setAttribute("car_list", CAR_SERVICE.getAvailableCars());
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
    
    private static int validate(int car, String start, String end) {
        if (car < 1 || start == null || start.isEmpty() 
               || end == null || end.isEmpty())
            return EMPTY_PARAM;
        return OK;
    }
    
    private static Date dateRemoveTime(Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime();
    }
    
}
