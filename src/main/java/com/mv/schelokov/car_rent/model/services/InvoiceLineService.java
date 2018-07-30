package com.mv.schelokov.car_rent.model.services;

import com.mv.schelokov.car_rent.model.db.dao.exceptions.DaoException;
import com.mv.schelokov.car_rent.model.db.dao.exceptions.DbException;
import com.mv.schelokov.car_rent.model.db.dao.factories.CriteriaFactory;
import com.mv.schelokov.car_rent.model.db.dao.factories.DaoFactory;
import com.mv.schelokov.car_rent.model.db.dao.interfaces.Criteria;
import com.mv.schelokov.car_rent.model.db.dao.interfaces.Dao;
import com.mv.schelokov.car_rent.model.entity.InvoiceLine;
import com.mv.schelokov.car_rent.model.services.exceptions.ServiceException;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class InvoiceLineService {
    private static final Logger LOG = Logger.getLogger(InvoiceLineService.class);

    private static final String ERROR_CREATE = "Failed to "
            + "create an invoice line";
    private static final String INVOICE_LINE_DAO_ERROR = "Failed to get"
            + " an invoice line list form the DAO by the criteria";
    private static final String INSTANCE_ERROR = "Failed to get instance";
    private static volatile InvoiceLineService instance;

    public static InvoiceLineService getInstance() throws ServiceException {
        InvoiceLineService localInstance = instance;
        if (localInstance == null) {
            synchronized (InvoiceLineService.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new InvoiceLineService();
                }
            }
        }
        if (localInstance == null) {
            LOG.error(INSTANCE_ERROR);
            throw new ServiceException(INSTANCE_ERROR);
        }
        return localInstance;
    }
    
    public void createInvoiceLine(InvoiceLine invoiceLine)
            throws ServiceException {
        try (DaoFactory daoFactory = new DaoFactory()) {
            Dao invoiceLineDao = daoFactory.getInvoiceLineDao();
            if (!invoiceLineDao.add(invoiceLine)) {
                LOG.error(ERROR_CREATE);
                throw new ServiceException(ERROR_CREATE);
            }
        }
        catch (DaoException | DbException ex) {
            LOG.error(ERROR_CREATE, ex);
            throw new ServiceException(ERROR_CREATE, ex);
        }
    }
    
    public List getInvoiceLinesByInvoiceId(int id)
            throws ServiceException {
        Criteria criteria = CriteriaFactory.findInvoiceLinesByInvoiceId(id);
        return getInvoiceLinesByCriteria(criteria);
    }
    
    private List getInvoiceLinesByCriteria(Criteria criteria)
            throws ServiceException {
        try (DaoFactory daoFactory = new DaoFactory()) {
            Dao invoiceLineDao = daoFactory
                    .getInvoiceLineDao();
            return invoiceLineDao.read(criteria);
        }
        catch (DaoException | DbException ex) {
            LOG.error(INVOICE_LINE_DAO_ERROR, ex);
            throw new ServiceException(INVOICE_LINE_DAO_ERROR, ex);
        }
    }
    
    private InvoiceLineService() {}
}
