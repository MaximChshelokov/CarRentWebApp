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
    private static final String INVOICE_DAO_ERROR = "Failed to get an"
            + " invoice list form the dao by the criteria";
    private static final String ERROR_CREATE = "Failed to create an"
            + " invoice";
    private static final String ERROR_UPDATE = "Failed to update an"
            + " invoice";
    private static final String ERROR_DELETE = "Failed to delete an"
            + " invoice";
    private static final String INSTANCE_ERROR = "Failed to get instance";
    private static volatile InvoiceService instance;

    public static InvoiceService getInstance() throws ServiceException {
        InvoiceService localInstance = instance;
        if (localInstance == null) {
            synchronized (InvoiceService.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new InvoiceService();
                }
            }
        }
        if (localInstance == null) {
            LOG.error(INSTANCE_ERROR);
            throw new ServiceException(INSTANCE_ERROR);
        }
        return localInstance;
    }
    
    public void openNewInvoice(RentOrder rentOrder)
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
        InvoiceLineService.getInstance().createInvoiceLine(invoiceLine);
    }
    
    public void recalculateInvoice(RentOrder rentOrder)
            throws ServiceException {
        Date today = DateUtils.onlyDate(new Date());
        InvoiceLineService invoiceLineService = InvoiceLineService.getInstance();
        if (invoiceLineService.getInvoiceLinesByInvoiceId(
                rentOrder.getId()).size() < 2
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
            invoiceLineService.createInvoiceLine(invoiceLine);
        }
    }
    
    public Invoice getInvoiceById(int id) throws ServiceException {
        Criteria criteria = CriteriaFactory.findInvoiceById(id);
        List invoiceList = getInvoiceByCriteria(criteria);
        if (invoiceList.isEmpty())
            return null;
        else
            return (Invoice) invoiceList.get(0);
                    
    }
    

    
    public void createInvoice(Invoice invoice) throws ServiceException {
        try (DaoFactory daoFactory = new DaoFactory()) {
            Dao invoiceDao = daoFactory.getInvoiceDao();
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
            Dao invoiceDao = daoFactory.getInvoiceDao();
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
            Dao invoiceDao = daoFactory.getInvoiceDao();
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
                    .getInvoiceDao();
            return invoiceDao.read(criteria);
        }
        catch (DaoException | DbException ex) {
            LOG.error(INVOICE_DAO_ERROR, ex);
            throw new ServiceException(INVOICE_DAO_ERROR, ex);
        }
    }
    
    private InvoiceService() {}
}
