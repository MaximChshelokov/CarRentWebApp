package com.mv.schelokov.carent.model.services;

import com.mv.schelokov.carent.model.db.dao.exceptions.DaoException;
import com.mv.schelokov.carent.model.db.dao.exceptions.DbException;
import com.mv.schelokov.carent.model.db.dao.factories.CriteriaFactory;
import com.mv.schelokov.carent.model.db.dao.factories.DaoFactory;
import com.mv.schelokov.carent.model.db.dao.interfaces.Criteria;
import com.mv.schelokov.carent.model.db.dao.interfaces.Dao;
import com.mv.schelokov.carent.model.entity.InvoiceLine;
import com.mv.schelokov.carent.model.services.exceptions.ServiceException;
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
    
    public void createInvoiceLine(InvoiceLine invoiceLine)
            throws ServiceException {
        try (DaoFactory daoFactory = new DaoFactory()) {
            Dao invoiceLineDao = daoFactory.createInvoiceLineDao();
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
        Criteria criteria = new CriteriaFactory()
                .createInvoiceLinesByInvoiceIdCriteria(id);
        return getInvoiceLinesByCriteria(criteria);
    }
    
    private List getInvoiceLinesByCriteria(Criteria criteria)
            throws ServiceException {
        try (DaoFactory daoFactory = new DaoFactory()) {
            Dao invoiceLineDao = daoFactory
                    .createInvoiceLineDao();
            return invoiceLineDao.read(criteria);
        }
        catch (DaoException | DbException ex) {
            LOG.error(INVOICE_LINE_DAO_ERROR, ex);
            throw new ServiceException(INVOICE_LINE_DAO_ERROR, ex);
        }
    }
}
