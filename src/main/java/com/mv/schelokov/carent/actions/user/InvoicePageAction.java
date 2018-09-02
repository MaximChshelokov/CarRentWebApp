package com.mv.schelokov.carent.actions.user;

import com.mv.schelokov.carent.actions.interfaces.AbstractAction;
import com.mv.schelokov.carent.actions.JspForward;
import com.mv.schelokov.carent.actions.consts.Jsps;
import com.mv.schelokov.carent.actions.consts.SessionAttr;
import com.mv.schelokov.carent.actions.exceptions.ActionException;
import com.mv.schelokov.carent.model.entity.Invoice;
import com.mv.schelokov.carent.model.entity.RentOrder;
import com.mv.schelokov.carent.model.entity.User;
import com.mv.schelokov.carent.model.services.CarService;
import com.mv.schelokov.carent.model.services.InvoiceLineService;
import com.mv.schelokov.carent.model.services.InvoiceService;
import com.mv.schelokov.carent.model.services.RentOrderService;
import com.mv.schelokov.carent.model.services.exceptions.ServiceException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class InvoicePageAction extends AbstractAction {
    private static final Logger LOG = Logger.getLogger(InvoicePageAction.class);
    private static final String ERROR = "Failed to get data from database";

    @Override
    public JspForward execute(HttpServletRequest req, HttpServletResponse res)
            throws ActionException {
        
        JspForward forward = new JspForward();
        
        if (isUser(req)) {
            User user = (User) req.getSession().getAttribute(SessionAttr.USER);
            try {
                RentOrder order = new RentOrderService()
                        .getLastOrderByUser(user);
                if (order == null) {
                    forward.setUrl(Jsps.USER_INVOICE);
                    return forward;
                }

                Invoice invoice = new InvoiceService()
                        .getInvoiceById(order.getId());
                
                if (invoice != null && invoice.getTotal() != invoice.getPaid()) {
                    CarService carService = new CarService();
                    
                    req.setAttribute(CAR,
                        carService.getCarById(order.getCar().getId()));

                    req.setAttribute(INVOICE, invoice);
                    
                    req.setAttribute(INVOICE_LINES, new InvoiceLineService()
                            .getInvoiceLinesByInvoiceId(invoice.getId()));
                }

                forward.setUrl(Jsps.USER_INVOICE);
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
