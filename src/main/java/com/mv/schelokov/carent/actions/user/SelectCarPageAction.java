package com.mv.schelokov.carent.actions.user;

import com.mv.schelokov.carent.actions.interfaces.AbstractAction;
import com.mv.schelokov.carent.actions.JspForward;
import com.mv.schelokov.carent.actions.consts.Actions;
import com.mv.schelokov.carent.actions.consts.Jsps;
import com.mv.schelokov.carent.actions.consts.SessionAttr;
import com.mv.schelokov.carent.actions.exceptions.ActionException;
import com.mv.schelokov.carent.model.entity.RentOrder;
import com.mv.schelokov.carent.model.entity.User;
import com.mv.schelokov.carent.model.services.CarService;
import com.mv.schelokov.carent.model.services.RentOrderService;
import com.mv.schelokov.carent.model.services.exceptions.ServiceException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class SelectCarPageAction extends AbstractAction {
    
    private static final Logger LOG = Logger.getLogger(SelectCarPageAction.class);
    private static final String ERROR = "Unable to prepare data for the select"
            + " car page";

    @Override
    public JspForward execute(HttpServletRequest req, HttpServletResponse res)
            throws ActionException {
        
        JspForward forward = new JspForward();
        
        if (isUser(req) || isAdmin(req)) {
            
            User user = (User) req.getSession().getAttribute(SessionAttr.USER);

            try {
                RentOrder order = new RentOrderService().getLastOrderByUser(user);
                if (order.getApprovedBy() != null && order.getCar() != null
                        && !order.getCar().isAvailable()) {
                    forward.setUrl(Actions.getActionName(Actions.ALREADY_RENTED));
                    forward.setRedirect(true);
                    return forward;
                }
                CarService carService = new CarService();
                req.setAttribute(ORDER, order);
                req.setAttribute(CAR_LIST, carService.getAvailableCars());
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
