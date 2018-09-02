package com.mv.schelokov.carent.actions.user;

import com.mv.schelokov.carent.actions.interfaces.AbstractAction;
import com.mv.schelokov.carent.actions.JspForward;
import com.mv.schelokov.carent.actions.consts.Jsps;
import com.mv.schelokov.carent.actions.consts.SessionAttr;
import com.mv.schelokov.carent.actions.exceptions.ActionException;
import com.mv.schelokov.carent.model.entity.RentOrder;
import com.mv.schelokov.carent.model.entity.User;
import com.mv.schelokov.carent.model.services.RentOrderService;
import com.mv.schelokov.carent.model.services.exceptions.ServiceException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class WelcomePageAction extends AbstractAction {
    
    private static final Logger LOG = Logger.getLogger(WelcomePageAction.class);
    private static final String ERROR = "Unable to get order data from service";
    private static final String RENT_ORDER = "rent_order";

    @Override
    public JspForward execute(HttpServletRequest req, HttpServletResponse res)
            throws ActionException {
        
        JspForward forward = new JspForward();

        if (isUser(req)) {
            
            try {
            User user = (User) req.getSession().getAttribute(SessionAttr.USER);
            RentOrder rentOrder = new RentOrderService()
                    .getLastRejectedOrderByUser(user);
            if (rentOrder.getId() > 0)
                req.setAttribute(RENT_ORDER, rentOrder);

            forward.setUrl(Jsps.USER_WELCOME);

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
