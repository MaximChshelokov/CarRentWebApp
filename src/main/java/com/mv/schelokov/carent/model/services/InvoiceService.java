package com.mv.schelokov.carent.model.services;

import com.mv.schelokov.carent.model.db.dao.exceptions.DbException;
import com.mv.schelokov.carent.model.db.dao.exceptions.DaoException;
import com.mv.schelokov.carent.model.db.dao.factories.CriteriaFactory;
import com.mv.schelokov.carent.model.db.dao.factories.DaoFactory;
import com.mv.schelokov.carent.model.db.dao.interfaces.Criteria;
import com.mv.schelokov.carent.model.entity.Invoice;
import com.mv.schelokov.carent.model.entity.InvoiceLine;
import com.mv.schelokov.carent.model.entity.RentOrder;
import com.mv.schelokov.carent.model.entity.builders.InvoiceBuilder;
import com.mv.schelokov.carent.model.entity.builders.InvoiceLineBuilder;
import com.mv.schelokov.carent.model.services.exceptions.ServiceException;
import com.mv.schelokov.carent.model.utils.OnlyDate;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import com.mv.schelokov.carent.model.db.dao.interfaces.Dao;
import com.mv.schelokov.carent.model.utils.Period;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class InvoiceService {
    
    private static final Logger LOG = Logger.getLogger(InvoiceService.class);
    private static final String INVOICE_DAO_ERROR = "Failed to get an"
            + " invoice list form the dao by the criteria";
    private static final String ERROR_CREATE = "Failed to create an"
            + " invoice";
    private static final String ERROR_UPDATE = "Failed to update an"
            + " invoice";
    private static final String ERROR_DELETE = "Failed to delete an"
            + " invoice";
    private static final String CAR_PREPAY = "Предоплата за аренду машины на %d дней";
    private static final String FOR_THE_RENT = "%s за аренду машины за %d дней";
    private static final String REFUND = "Возврат";
    private static final String SURCHARGE = "Доплата";
    
    public void openNewInvoice(RentOrder rentOrder)
            throws ServiceException {
        Invoice invoice = new InvoiceBuilder()
                .withId(rentOrder.getId())
                .creationDate(new Date())
                .getInvoice();
        createInvoice(invoice);

        int days = new Period(rentOrder.getStartDate(), 
                rentOrder.getEndDate()).getDays();
        
        InvoiceLine invoiceLine = new InvoiceLineBuilder()
                .paymentDetails(String.format(CAR_PREPAY, days))
                .withInvoiceId(invoice.getId())
                .paymentAmount(days * rentOrder.getCar().getPrice())
                .getInvoiceLine();
        new InvoiceLineService().createInvoiceLine(invoiceLine);
    }
    
    public void recalculateInvoice(RentOrder rentOrder)
            throws ServiceException {
        Date today = new OnlyDate().getOnlyDate();
        if (rentOrder.getStartDate().after(today))
            today = rentOrder.getStartDate();
        InvoiceLineService invoiceLineService = new InvoiceLineService();
        if (invoiceLineService.getInvoiceLinesByInvoiceId(
                rentOrder.getId()).size() < 2
                && !rentOrder.getEndDate().equals(today)) {
            int newDays = new Period(rentOrder.getStartDate(), today).getDays();
            int days = new Period(rentOrder.getStartDate(),
                    rentOrder.getEndDate()).getDays();
            int sumDifference = (newDays - days) * rentOrder.getCar().getPrice();
            String paymentType = (days < newDays) ? SURCHARGE : REFUND;
            InvoiceLine invoiceLine = new InvoiceLineBuilder()
                    .paymentDetails(String.format(FOR_THE_RENT,
                            paymentType, Math.abs(newDays - days)))
                    .paymentAmount(sumDifference)
                    .withInvoiceId(rentOrder.getId())
                    .getInvoiceLine();
            invoiceLineService.createInvoiceLine(invoiceLine);
        }
    }
    
    public Invoice getInvoiceById(int id) throws ServiceException {
        Criteria criteria = new CriteriaFactory().createInvoiceByIdCriteria(id);
        List invoiceList = getInvoiceByCriteria(criteria);
        if (invoiceList.isEmpty())
            return null;
        else
            return (Invoice) invoiceList.get(0);
                    
    }
    

    
    public void createInvoice(Invoice invoice) throws ServiceException {
        try (DaoFactory daoFactory = new DaoFactory()) {
            Dao invoiceDao = daoFactory.createInvoiceDao();
            if (!invoiceDao.add(invoice)) {
                LOG.error(ERROR_CREATE);
                throw new ServiceException(ERROR_CREATE);
            }
        }
        catch (DaoException | DbException ex) {
            LOG.error(ERROR_CREATE, ex);
            throw new ServiceException(ERROR_CREATE, ex);
        }
    }
    
    public void updateInvoice(Invoice invoice) throws ServiceException {
        try (DaoFactory daoFactory = new DaoFactory()) {
            Dao invoiceDao = daoFactory.createInvoiceDao();
            if (!invoiceDao.update(invoice)) {
                LOG.error(ERROR_UPDATE);
                throw new ServiceException(ERROR_UPDATE);
            }
        }
        catch (DaoException | DbException ex) {
            LOG.error(ERROR_UPDATE, ex);
            throw new ServiceException(ERROR_UPDATE, ex);
        }
    }
    
    public void deleteInvoice(Invoice invoice) throws ServiceException {
        try (DaoFactory daoFactory = new DaoFactory()) {
            Dao invoiceDao = daoFactory.createInvoiceDao();
            if (!invoiceDao.remove(invoice)) {
                LOG.error(ERROR_DELETE);
                throw new ServiceException(ERROR_DELETE);
            }
        }
        catch (DaoException | DbException ex) {
            LOG.error(ERROR_DELETE, ex);
            throw new ServiceException(ERROR_DELETE, ex);
        }
    }
    
    
    private List getInvoiceByCriteria(Criteria criteria)
            throws ServiceException {
        try (DaoFactory daoFactory = new DaoFactory()) {
            Dao invoiceDao = daoFactory
                    .createInvoiceDao();
            return invoiceDao.read(criteria);
        }
        catch (DaoException | DbException ex) {
            LOG.error(INVOICE_DAO_ERROR, ex);
            throw new ServiceException(INVOICE_DAO_ERROR, ex);
        }
    }
}
