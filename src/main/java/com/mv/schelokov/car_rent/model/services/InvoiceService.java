package com.mv.schelokov.car_rent.model.services;

import com.mv.schelokov.car_rent.model.db.dao.exceptions.DbException;
import com.mv.schelokov.car_rent.model.db.dao.exceptions.DaoException;
import com.mv.schelokov.car_rent.model.db.dao.factories.CriteriaFactory;
import com.mv.schelokov.car_rent.model.db.dao.factories.DaoFactory;
import com.mv.schelokov.car_rent.model.db.dao.interfaces.Criteria;
import com.mv.schelokov.car_rent.model.entity.Invoice;
import com.mv.schelokov.car_rent.model.entity.InvoiceLine;
import com.mv.schelokov.car_rent.model.entity.RentOrder;
import com.mv.schelokov.car_rent.model.entity.builders.InvoiceBuilder;
import com.mv.schelokov.car_rent.model.entity.builders.InvoiceLineBuilder;
import com.mv.schelokov.car_rent.model.services.exceptions.ServiceException;
import com.mv.schelokov.car_rent.model.utils.DateUtils;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import com.mv.schelokov.car_rent.model.db.dao.interfaces.Dao;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class InvoiceService {
    
    private static final Logger LOG = Logger.getLogger(InvoiceService.class);
    private static final String INVOICE_REPOSITORY_ERROR = "Failed to get an"
            + " invoice list form the repository by the criteria";
    private static final String INVOICE_OPERATION_ERROR = "Failed to operate an"
            + " invoice";
    private static final String INVOICE_LINE_OPERATION_ERROR = "Failed to "
            + "operate an invoice line";
    private static final String INVOICE_LINE_REPOSITORY_ERROR = "Failed to get"
            + " an invoice line list form the repository by the criteria";

    private static enum Operation {
        CREATE, UPDATE, DELETE
    }
    
    public static void openNewInvoice(RentOrder rentOrder)
            throws ServiceException {
        Invoice invoice = new InvoiceBuilder()
                .setId(rentOrder.getId())
                .setDate(new Date())
                .getInvoice();
        createInvoice(invoice);

        int days = DateUtils.days(rentOrder.getStartDate(), 
                rentOrder.getEndDate());
        
        InvoiceLine invoiceLine = new InvoiceLineBuilder()
                .setDetails(String.format("Предоплата за аренду машины "
                        + "на %d дней", days))
                .setInvoiceId(invoice.getId())
                .setAmount(days * rentOrder.getCar().getPrice())
                .getInvoiceLine();
        createInvoiceLine(invoiceLine);
    }
    
    public static void recalculateInvoice(RentOrder rentOrder)
            throws ServiceException {
        Date today = DateUtils.onlyDate(new Date());
        if (getInvoiceLinesByInvoiceId(rentOrder.getId()).size() < 2
                && !rentOrder.getEndDate().equals(today)) {
            int newDays = DateUtils.days(rentOrder.getStartDate(), today);
            int days = DateUtils.days(rentOrder.getStartDate(),
                    rentOrder.getEndDate());
            int sumDifference = (newDays - days) * rentOrder.getCar().getPrice();
            String paymentType = (days < newDays) ? "Доплата" : "Возврат";
            InvoiceLine invoiceLine = new InvoiceLineBuilder()
                    .setDetails(String.format("%s за аренду машины "
                            + "за %d дней", paymentType, Math.abs(newDays - days)))
                    .setAmount(sumDifference)
                    .setInvoiceId(rentOrder.getId())
                    .getInvoiceLine();
            createInvoiceLine(invoiceLine);
        }
    }
    
    public static Invoice getInvoiceById(int id) throws ServiceException {
        Criteria criteria = CriteriaFactory.findInvoiceById(id);
        List invoiceList = getInvoiceByCriteria(criteria);
        if (invoiceList.isEmpty())
            return null;
        else
            return (Invoice) invoiceList.get(0);
                    
    }
    
    public static List getInvoiceLinesByInvoiceId(int id)
            throws ServiceException {
        Criteria criteria = CriteriaFactory.findInvoiceLinesByInvoiceId(id);
        return getInvoiceLinesByCriteria(criteria);
    }
    
    public static void createInvoice(Invoice invoice) throws ServiceException {
        operateInvoice(invoice, Operation.CREATE);
    }
    
    public static void updateInvoice(Invoice invoice) throws ServiceException {
        operateInvoice(invoice, Operation.UPDATE);
    }
    
    public static void deleteInvoice(Invoice invoice) throws ServiceException {
        operateInvoice(invoice, Operation.DELETE);
    }
    
    public static void createInvoiceLine(InvoiceLine invoiceLine) throws
            ServiceException {
        operateInvoiceLine(invoiceLine, Operation.CREATE);
    }
    
    private static void operateInvoice(Invoice invoice, Operation operation)
            throws ServiceException {
        try (DaoFactory repositoryFactory = new DaoFactory()) {
            Dao invoiceRepository = repositoryFactory
                    .getInvoiceDao();
            boolean result = false;
            switch (operation) {
                case CREATE:
                    result = invoiceRepository.add(invoice);
                    break;
                case UPDATE:
                    result = invoiceRepository.update(invoice);
                    break;
                case DELETE:
                    result = invoiceRepository.remove(invoice);
            }
            if (!result) {
                throw new ServiceException("Unable to operate to InvoiceRepository");
            }
        }
        catch (DaoException | DbException ex) {
            LOG.error(INVOICE_OPERATION_ERROR, ex);
            throw new ServiceException(INVOICE_OPERATION_ERROR, ex);
        }
    }
    
    private static void operateInvoiceLine(InvoiceLine invoiceLine, 
            Operation operation) throws ServiceException {
        try (DaoFactory repositoryFactory = new DaoFactory()) {
            Dao invoiceLineRepository = repositoryFactory
                    .getInvoiceLineDao();
            boolean result = false;
            switch (operation) {
                case CREATE:
                    result = invoiceLineRepository.add(invoiceLine);
                    break;
                case UPDATE:
                    result = invoiceLineRepository.update(invoiceLine);
                    break;
                case DELETE:
                    result = invoiceLineRepository.remove(invoiceLine);
            }
            if (!result) {
                throw new ServiceException("Unable to operate to "
                        + "InvoiceLineRepository");
            }
        }
        catch (DaoException | DbException ex) {
            LOG.error(INVOICE_LINE_OPERATION_ERROR, ex);
            throw new ServiceException(INVOICE_LINE_OPERATION_ERROR, ex);
        }
    }
    
    private static List getInvoiceByCriteria(Criteria criteria)
            throws ServiceException {
        try (DaoFactory repositoryFactory = new DaoFactory()) {
            Dao invoiceRepository = repositoryFactory
                    .getInvoiceDao();
            return invoiceRepository.read(criteria);
        }
        catch (DaoException | DbException ex) {
            LOG.error(INVOICE_REPOSITORY_ERROR, ex);
            throw new ServiceException(INVOICE_REPOSITORY_ERROR, ex);
        }
    }
    
    private static List getInvoiceLinesByCriteria(Criteria criteria)
            throws ServiceException {
        try (DaoFactory repositoryFactory = new DaoFactory()) {
            Dao invoiceLineRepository = repositoryFactory
                    .getInvoiceLineDao();
            return invoiceLineRepository.read(criteria);
        }
        catch (DaoException | DbException ex) {
            LOG.error(INVOICE_LINE_REPOSITORY_ERROR, ex);
            throw new ServiceException(INVOICE_LINE_REPOSITORY_ERROR, ex);
        }
    }
}
