package com.mv.schelokov.carent.actions.user;

import com.mv.schelokov.carent.actions.interfaces.AbstractAction;
import com.mv.schelokov.carent.actions.JspForward;
import com.mv.schelokov.carent.actions.consts.Actions;
import com.mv.schelokov.carent.actions.consts.Jsps;
import com.mv.schelokov.carent.actions.consts.SessionAttr;
import com.mv.schelokov.carent.actions.exceptions.ActionException;
import com.mv.schelokov.carent.model.entity.RentOrder;
import com.mv.schelokov.carent.model.entity.User;
import com.mv.schelokov.carent.model.entity.builders.CarBuilder;
import com.mv.schelokov.carent.model.services.CarService;
import com.mv.schelokov.carent.model.services.RentOrderService;
import com.mv.schelokov.carent.model.services.exceptions.ServiceException;
import com.mv.schelokov.carent.model.validators.RentOrderValidator;
import com.mv.schelokov.carent.model.validators.ValidationResult;
import java.text.ParseException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class CreateOrderAction extends AbstractAction {
    private static final Logger LOG = Logger.getLogger(CreateOrderAction.class);
    private static final String ERROR = "Failed to create the rent order";
    private static final String DATE_PARSE_ERROR = "Failed to parse a date";
    
    @Override
    public JspForward execute(HttpServletRequest req, HttpServletResponse res)
            throws ActionException {
        
        JspForward forward = new JspForward();
        
        if (isUser(req) || isAdmin(req)) {
            User user = (User) req.getSession().getAttribute(SessionAttr.USER);
            
            String start = req.getParameter(START_DATE);
            String end = req.getParameter(END_DATE);
            

            
            try {
                
                RentOrder order = new RentOrderService()
                        .getLastOrderByUser(user);
                
                order.setCar(new CarBuilder()
                        .withId(getIntParam(req, SELECTED_CAR))
                        .getCar());
                
                int validationResult = ValidationResult.OK;

                try {
                    order.setStartDate(FORMAT.parse(start));
                    order.setEndDate(FORMAT.parse(end));
                }
                catch (ParseException ex) {
                    validationResult = ValidationResult.INVALID_DATE;
                    LOG.error(DATE_PARSE_ERROR, ex);
                }

                if (validationResult == ValidationResult.OK) {
                    validationResult = new RentOrderValidator(order).validate();
                }
                
                if (validationResult == ValidationResult.OK) {
                    
                    if (order.getId() == 0)
                        new RentOrderService().addOrder(order);
                    else
                        new RentOrderService().updateOrder(order);
                    
                    forward.setUrl(Actions.getActionName(
                            Actions.ORDER_COMPLETED));
                    forward.setRedirect(true);
                    
                    return forward;
                }
                
                CarService carService = new CarService();
                
                req.setAttribute(ERROR_NUMBER, validationResult);
                req.setAttribute(CAR_LIST, carService.getAvailableCars());
                req.setAttribute(ORDER, order);
                req.setAttribute(START_DATE, FORMAT.format(order.getStartDate()));
                req.setAttribute(END_DATE, FORMAT.format(order.getEndDate()));
                
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
