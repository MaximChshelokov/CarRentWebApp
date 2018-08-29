package com.mv.schelokov.carent.actions.admin;

import com.mv.schelokov.carent.actions.interfaces.AbstractAction;
import com.mv.schelokov.carent.actions.JspForward;
import com.mv.schelokov.carent.actions.consts.Actions;
import com.mv.schelokov.carent.actions.exceptions.ActionException;
import com.mv.schelokov.carent.model.entity.Car;
import com.mv.schelokov.carent.model.entity.InvoiceLine;
import com.mv.schelokov.carent.model.entity.RentOrder;
import com.mv.schelokov.carent.model.entity.builders.InvoiceLineBuilder;
import com.mv.schelokov.carent.model.services.CarService;
import com.mv.schelokov.carent.model.services.InvoiceLineService;
import com.mv.schelokov.carent.model.services.RentOrderService;
import com.mv.schelokov.carent.model.services.exceptions.ServiceException;
import com.mv.schelokov.carent.model.validators.InvoiceLineValidator;
import com.mv.schelokov.carent.model.validators.ValidationResult;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class CloseOrderAction extends AbstractAction {
    
    private static final Logger LOG = Logger.getLogger(CloseOrderAction.class);
    private static final String ERROR = "Failed to close order";
    private static final String AMOUNT = "amount";
    private static final String REASON = "reason";
    private static final String INVOICE_LINE = "invoice_line";

    @Override
    public JspForward execute(HttpServletRequest req, HttpServletResponse res)
            throws ActionException {
        
        JspForward forward = new JspForward();
        if (isAdmin(req)) {
            int invoiceId = getIntParam(req, ID);
            if (invoiceId < 1) {
                throw new ActionException(WRONG_ID);
            }
            try {
                RentOrder order = (RentOrder) new RentOrderService()
                        .getOrderById(invoiceId);
                
                if (req.getParameter("fine-check") != null &&
                        addFineToInvoice(req, invoiceId) != ValidationResult.OK) {
                    forward.setUrl(Actions.getActionName(Actions.BILL_ORDER));
                    return forward;
                }
                
                CarService carService = new CarService();
                
                Car car = carService.getCarById(order.getCar().getId());
                car.setAvailable(true);
                carService.updateCar(car);
                
                forward.setUrl(Actions.getActionName(Actions.OPENED_ORDERS));
                forward.setRedirect(true);
                        
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
    
    private int addFineToInvoice(HttpServletRequest req, int invoiceId)
            throws ServiceException {
        InvoiceLine invoiceLine = new InvoiceLineBuilder()
                .withInvoiceId(invoiceId)
                .paymentDetails(req.getParameter(REASON))
                .paymentAmount(getIntParam(req, AMOUNT))
                .getInvoiceLine();
        InvoiceLineValidator validator = new InvoiceLineValidator(invoiceLine);
        int validationResult = validator.validate();
        if (validationResult != ValidationResult.OK) {
            req.setAttribute(INVOICE_LINE, invoiceLine);
            req.setAttribute(ERROR_NUMBER, validationResult);

        } else {
            new InvoiceLineService().createInvoiceLine(invoiceLine);
        }
        return validationResult;
    }

}
