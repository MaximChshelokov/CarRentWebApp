package com.mv.schelokov.carent.actions.admin;

import com.mv.schelokov.carent.actions.interfaces.AbstractAction;
import com.mv.schelokov.carent.actions.JspForward;
import com.mv.schelokov.carent.actions.consts.Actions;
import com.mv.schelokov.carent.actions.consts.Jsps;
import com.mv.schelokov.carent.actions.exceptions.ActionException;
import com.mv.schelokov.carent.model.entity.RentOrder;
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
public class UpdateOrderAction extends AbstractAction {
    
    private static final Logger LOG = Logger.getLogger(UpdateOrderAction.class);
    private static final String ERROR = "Failed to update the rent order";
    private static final String DATE_PARSE_ERROR = "Date parse error";


    @Override
    public JspForward execute(HttpServletRequest req, HttpServletResponse res)
            throws ActionException {
        
        JspForward forward = new JspForward();
        
        if (isAdmin(req)) {

            int orderId = getIntParam(req, ID);
            if (orderId < 1)
                throw new ActionException(WRONG_ID);
            try {
                RentOrderService orderService = new RentOrderService();
                RentOrder order = orderService.getOrderById(orderId);
                order.getCar().setId(getIntParam(req, SELECTED_CAR));

                int validationResult = ValidationResult.OK;

                try {
                    order.setStartDate(FORMAT.parse(req.getParameter(START_DATE)));
                    order.setEndDate(FORMAT.parse(req.getParameter(END_DATE)));
                } catch (ParseException ex) {
                    LOG.error(DATE_PARSE_ERROR, ex);
                    validationResult = ValidationResult.INVALID_DATE;
                }
                
                if (validationResult == ValidationResult.OK)
                    validationResult = new RentOrderValidator(order).validate();
                if (validationResult == ValidationResult.OK) {
                    orderService.updateOrder(order);
                    
                    forward.setUrl(Actions.getActionName(Actions.ORDER_LIST));
                    forward.setRedirect(true);
                    
                    return forward;
                }
                
                CarService carService = new CarService();

                req.setAttribute(ERROR_NUMBER, validationResult);
                req.setAttribute(CAR_LIST, carService.getAvailableCars());
                req.setAttribute(ORDER, order);
                req.setAttribute(START_DATE,
                        FORMAT.format(order.getStartDate()));
                req.setAttribute(END_DATE, FORMAT.format(order.getEndDate()));
                
                forward.setUrl(Jsps.ADMIN_SELECT_CAR);
                
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
