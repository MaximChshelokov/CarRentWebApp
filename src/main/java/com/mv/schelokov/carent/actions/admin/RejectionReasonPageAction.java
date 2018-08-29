package com.mv.schelokov.carent.actions.admin;

import com.mv.schelokov.carent.actions.JspForward;
import com.mv.schelokov.carent.actions.consts.Jsps;
import com.mv.schelokov.carent.actions.exceptions.ActionException;
import com.mv.schelokov.carent.actions.interfaces.AbstractAction;
import com.mv.schelokov.carent.model.services.RentOrderService;
import com.mv.schelokov.carent.model.services.exceptions.ServiceException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class RejectionReasonPageAction extends AbstractAction {
    
    private static final Logger LOG = Logger.getLogger(RejectionReasonPageAction.class);
    private static final String SERVICE_ERROR = "Unable to get order from service";

    @Override
    public JspForward execute(HttpServletRequest req, HttpServletResponse res)
            throws ActionException {

        JspForward forward = new JspForward();

        if (isAdmin(req)) {
            try {
            int orderId = getIntParam(req, ID);
            if (orderId < 1 ||
                    new RentOrderService().getOrderById(orderId).getId()
                    != orderId) {
                throw new ActionException(WRONG_ID);
            }
            req.setAttribute(ID, orderId);
            } catch (ServiceException ex) {
                LOG.error(SERVICE_ERROR, ex);
                throw new ActionException(SERVICE_ERROR, ex);
            }
            forward.setUrl(Jsps.ADMIN_REJECTION_REASON);
            return forward;
        }
        sendForbidden(res);
        return forward;
    }
}
