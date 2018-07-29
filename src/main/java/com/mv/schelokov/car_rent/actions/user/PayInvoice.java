package com.mv.schelokov.car_rent.actions.user;

import com.mv.schelokov.car_rent.actions.AbstractAction;
import com.mv.schelokov.car_rent.actions.JspForward;
import com.mv.schelokov.car_rent.consts.Jsps;
import com.mv.schelokov.car_rent.consts.SessionAttr;
import com.mv.schelokov.car_rent.exceptions.ActionException;
import com.mv.schelokov.car_rent.model.entities.Invoice;
import com.mv.schelokov.car_rent.model.entities.RentOrder;
import com.mv.schelokov.car_rent.model.entities.User;
import com.mv.schelokov.car_rent.model.services.CarService;
import com.mv.schelokov.car_rent.model.services.InvoiceService;
import com.mv.schelokov.car_rent.model.services.OrderService;
import com.mv.schelokov.car_rent.model.services.exceptions.ServiceException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class PayInvoice extends AbstractAction {
    private static final Logger LOG = Logger.getLogger(ShowSelectCarPage.class);
    private static final String ERROR = "Failed to pay invoice";

    @Override
    public JspForward execute(HttpServletRequest req, HttpServletResponse res)
            throws ActionException {
        
        JspForward forward = new JspForward();
        
        if (isUser(req)) {
            User user = (User) req.getSession().getAttribute(SessionAttr.USER);
            try {
                RentOrder order = OrderService.getOrdersByUser(user);
                Invoice invoice = InvoiceService.getInvoiceById(order.getId());

                invoice.setPaid(invoice.getTotal());
                
                InvoiceService.updateInvoice(invoice);
                
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
