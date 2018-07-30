package com.mv.schelokov.carent.actions.user;

import com.mv.schelokov.carent.actions.AbstractAction;
import com.mv.schelokov.carent.actions.JspForward;
import com.mv.schelokov.carent.consts.Jsps;
import com.mv.schelokov.carent.consts.SessionAttr;
import com.mv.schelokov.carent.exceptions.ActionException;
import com.mv.schelokov.carent.model.entity.Invoice;
import com.mv.schelokov.carent.model.entity.RentOrder;
import com.mv.schelokov.carent.model.entity.User;
import com.mv.schelokov.carent.model.services.CarService;
import com.mv.schelokov.carent.model.services.InvoiceService;
import com.mv.schelokov.carent.model.services.OrderService;
import com.mv.schelokov.carent.model.services.exceptions.ServiceException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class PayInvoice extends AbstractAction {
    private static final Logger LOG = Logger.getLogger(PayInvoice.class);
    private static final String ERROR = "Failed to pay invoice";

    @Override
    public JspForward execute(HttpServletRequest req, HttpServletResponse res)
            throws ActionException {
        
        JspForward forward = new JspForward();
        
        if (isUser(req)) {
            User user = (User) req.getSession().getAttribute(SessionAttr.USER);
            try {
                RentOrder order = OrderService.getInstance()
                        .getOrdersByUser(user);
                
                InvoiceService invoiceService = InvoiceService.getInstance();
                Invoice invoice = invoiceService.getInvoiceById(order.getId());

                invoice.setPaid(invoice.getTotal());
                
                invoiceService.updateInvoice(invoice);
                
                forward.setUrl("action/home");
                forward.setRedirect(true);

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
