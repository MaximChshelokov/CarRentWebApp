package com.mv.schelokov.carent.actions.admin;

import com.mv.schelokov.carent.actions.interfaces.AbstractAction;
import com.mv.schelokov.carent.actions.JspForward;
import com.mv.schelokov.carent.actions.consts.Jsps;
import com.mv.schelokov.carent.actions.exceptions.ActionException;
import com.mv.schelokov.carent.model.entity.Invoice;
import com.mv.schelokov.carent.model.entity.RentOrder;
import com.mv.schelokov.carent.model.services.CarService;
import com.mv.schelokov.carent.model.services.InvoiceLineService;
import com.mv.schelokov.carent.model.services.InvoiceService;
import com.mv.schelokov.carent.model.services.OrderService;
import com.mv.schelokov.carent.model.services.UserDataService;
import com.mv.schelokov.carent.model.services.UserService;
import com.mv.schelokov.carent.model.services.exceptions.ServiceException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class ShowBillOrderPage extends AbstractAction {
    private static final Logger LOG = Logger.getLogger(ShowBillOrderPage.class);
    private static final String ERROR = "Failed to get data from database";
    private static final String WRONG_ID = "Wrong id parameter for order entity";

    @Override
    public JspForward execute(HttpServletRequest req, HttpServletResponse res)
            throws ActionException {
        
        JspForward forward = new JspForward();
        
        if (isAdmin(req)) {
            int orderId = getIntParam(req, "id");
            if (orderId < 1)
                throw new ActionException(WRONG_ID);
            try {
                RentOrder order = OrderService.getInstance()
                        .getOrderById(orderId);
                InvoiceService invoiceService = InvoiceService.getInstance();
                invoiceService.recalculateInvoice(order);
                
                CarService carService = CarService.getInstance();
                
                req.setAttribute("user_data",
                        UserDataService.getInstance()
                                .getUserDataById(order.getUser().getId()));
                req.setAttribute("car",
                        carService.getCarById(order.getCar().getId()));
                
                Invoice invoice = invoiceService.getInvoiceById(order.getId());
                req.setAttribute("invoice", invoice);
                
                req.setAttribute("invoice_lines", InvoiceLineService
                        .getInstance()
                        .getInvoiceLinesByInvoiceId(invoice.getId()));
                
                forward.setUrl(Jsps.ADMIN_BILL_ORDER);

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
