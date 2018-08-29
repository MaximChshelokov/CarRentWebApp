package com.mv.schelokov.carent.actions.admin;

import com.mv.schelokov.carent.actions.JspForward;
import com.mv.schelokov.carent.actions.consts.Actions;
import com.mv.schelokov.carent.actions.consts.Jsps;
import com.mv.schelokov.carent.actions.consts.SessionAttr;
import com.mv.schelokov.carent.actions.exceptions.ActionException;
import com.mv.schelokov.carent.actions.interfaces.AbstractAction;
import com.mv.schelokov.carent.model.entity.RejectionReason;
import com.mv.schelokov.carent.model.entity.RentOrder;
import com.mv.schelokov.carent.model.entity.User;
import com.mv.schelokov.carent.model.entity.builders.RejectionReasonBuilder;
import com.mv.schelokov.carent.model.services.RejectionReasonService;
import com.mv.schelokov.carent.model.services.RentOrderService;
import com.mv.schelokov.carent.model.services.exceptions.ServiceException;
import com.mv.schelokov.carent.model.validators.RejectionReasonValidator;
import com.mv.schelokov.carent.model.validators.ValidationResult;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class UpdateRejectionReasonAction extends AbstractAction {
    private static final Logger LOG = Logger.getLogger(UpdateRejectionReasonAction.class);
    private static final String ERROR = "Unable to save the rejection reason";
    private static final String REJECTION_REASON = "rejection-reason";
    private static final String REJECTION_TEXT = "rejection_text";


    @Override
    public JspForward execute(HttpServletRequest req, HttpServletResponse res)
            throws ActionException {
        
        JspForward forward = new JspForward();
        
        if (isAdmin(req)) {
            try {
                int orderId = getIntParam(req, ID);
                if (orderId < 1
                        || new RentOrderService().getOrderById(orderId).getId()
                        != orderId) {
                    throw new ActionException(WRONG_ID);
                }
                RejectionReason rejectionReason = new RejectionReasonBuilder()
                        .withId(orderId)
                        .dueReason(req.getParameter(REJECTION_REASON))
                        .getRejectionReason();
                int validationResult = new RejectionReasonValidator(rejectionReason)
                        .validate();
                if (validationResult == ValidationResult.OK) {
                    new RejectionReasonService().createReason(rejectionReason);
                    RentOrderService orderService = new RentOrderService();
                    RentOrder order = (RentOrder) orderService.getOrderById(orderId);
                    order.setApprovedBy((User) req.getSession()
                            .getAttribute(SessionAttr.USER));
                    orderService.updateOrder(order);
                    forward.setUrl(Actions.getActionName(Actions.ORDER_LIST));
                    forward.setRedirect(true);
                    return forward;
                }
                
                req.setAttribute(ERROR_NUMBER, validationResult);
                req.setAttribute(REJECTION_TEXT, rejectionReason.getReason());
                req.setAttribute(ID, orderId);
                
                forward.setUrl(Jsps.ADMIN_REJECTION_REASON);
                
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