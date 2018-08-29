package com.mv.schelokov.carent.actions.admin;

import com.mv.schelokov.carent.actions.interfaces.AbstractAction;
import com.mv.schelokov.carent.actions.JspForward;
import com.mv.schelokov.carent.actions.consts.Jsps;
import com.mv.schelokov.carent.actions.exceptions.ActionException;
import com.mv.schelokov.carent.model.entity.RentOrder;
import com.mv.schelokov.carent.model.services.CarService;
import com.mv.schelokov.carent.model.services.RentOrderService;
import com.mv.schelokov.carent.model.services.exceptions.ServiceException;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class EditOrderAction extends AbstractAction {
    
    private static final Logger LOG = Logger.getLogger(EditOrderAction.class);
    private static final String ERROR = "Failed to prepare rent order edit page";
    
    @Override
    public JspForward execute(HttpServletRequest req, HttpServletResponse res)
            throws ActionException {
        
        JspForward forward = new JspForward();
        
        if (isAdmin(req)) {
            int id = getIntParam(req, ID);
            if (id < 1)
                throw new ActionException(WRONG_ID);
            try {
                RentOrder order = new RentOrderService().getOrderById(id);
                
                CarService carService = new CarService();
                
                req.setAttribute(CAR_LIST, carService.getAvailableCars());
                req.setAttribute(ORDER, order);
                req.setAttribute(START_DATE, 
                        FORMAT.format(order.getStartDate()));
                req.setAttribute(END_DATE, FORMAT.format(order.getEndDate()));
                
                forward.setUrl(Jsps.ADMIN_SELECT_CAR);

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
