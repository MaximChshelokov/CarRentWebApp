package com.mv.schelokov.car_rent.actions.admin;

import com.mv.schelokov.car_rent.actions.AbstractAction;
import com.mv.schelokov.car_rent.actions.JspForward;
import com.mv.schelokov.car_rent.consts.Jsps;
import com.mv.schelokov.car_rent.exceptions.ActionException;
import com.mv.schelokov.car_rent.model.entity.Invoice;
import com.mv.schelokov.car_rent.model.entity.RentOrder;
import com.mv.schelokov.car_rent.model.services.CarService;
import com.mv.schelokov.car_rent.model.services.InvoiceService;
import com.mv.schelokov.car_rent.model.services.OrderService;
import com.mv.schelokov.car_rent.model.services.UserService;
import com.mv.schelokov.car_rent.model.services.exceptions.ServiceException;
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

    @Override
    public JspForward execute(HttpServletRequest req, HttpServletResponse res)
            throws ActionException {
        
        JspForward forward = new JspForward();
        
        if (isAdmin(req)) {
            int orderId = getIntParam(req, "id");
            if (orderId < 1)
                throw new ActionException("Incorrect order id");
            try {
                RentOrder order = OrderService.getOrderById(orderId);
                InvoiceService.recalculateInvoice(order);
                
                req.setAttribute("user_data",
                        UserService.getUserDataById(order.getUser().getId()));
                req.setAttribute("car",
                        CarService.getCarById(order.getCar().getId()));
                
                Invoice invoice = InvoiceService.getInvoiceById(order.getId());
                req.setAttribute("invoice", invoice);
                
                req.setAttribute("invoice_lines", InvoiceService
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
