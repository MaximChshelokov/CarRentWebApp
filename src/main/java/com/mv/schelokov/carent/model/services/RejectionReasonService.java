package com.mv.schelokov.carent.model.services;

import com.mv.schelokov.carent.model.db.dao.exceptions.DaoException;
import com.mv.schelokov.carent.model.db.dao.exceptions.DbException;
import com.mv.schelokov.carent.model.db.dao.factories.CriteriaFactory;
import com.mv.schelokov.carent.model.db.dao.factories.DaoFactory;
import com.mv.schelokov.carent.model.db.dao.interfaces.Criteria;
import com.mv.schelokov.carent.model.db.dao.interfaces.Dao;
import com.mv.schelokov.carent.model.entity.RejectionReason;
import com.mv.schelokov.carent.model.entity.builders.RejectionReasonBuilder;
import com.mv.schelokov.carent.model.services.exceptions.ServiceException;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class RejectionReasonService {
    
    private static final Logger LOG = Logger.getLogger(RejectionReasonService.class);
    private static final String REJECTION_REASON_DAO_ERROR = "Failed to get a"
            + " rejection reasons list form the dao by the criteria";
    private static final String ERROR_CREATE = "Failed to create a"
            + " rejection reason";
    private static final String ERROR_UPDATE = "Failed to update a"
            + " rejection reason";
    private static final String ERROR_DELETE = "Failed to delete a"
            + " rejection reason";
    
    public RejectionReason getReasonById(int id) throws ServiceException {
        Criteria criteria = new CriteriaFactory()
                .createRejectionReasonByIdCriteria(id);
        List<RejectionReason> reasonList = getReasonByCriteria(criteria);
        if (reasonList.isEmpty())
            return new RejectionReasonBuilder()
                    .withId(id)
                    .getRejectionReason();
        return reasonList.get(0);
    }
    
    public void createReason(RejectionReason rejectionReason)
            throws ServiceException {
        try (DaoFactory daoFactory = new DaoFactory()) {
            Dao rejectionReasonDao = daoFactory.createRejectionReasonDao();
            if (!rejectionReasonDao.add(rejectionReason)) {
                LOG.error(ERROR_CREATE);
                throw new ServiceException(ERROR_CREATE);
            }
        } catch (DaoException | DbException ex) {
            LOG.error(ERROR_CREATE, ex);
            throw new ServiceException(ERROR_CREATE, ex);
        }
    }

    public void updateReason(RejectionReason rejectionReason)
            throws ServiceException {
        try (DaoFactory daoFactory = new DaoFactory()) {
            Dao rejectionReasonDao = daoFactory.createRejectionReasonDao();
            if (!rejectionReasonDao.update(rejectionReason)) {
                LOG.error(ERROR_CREATE);
                throw new ServiceException(ERROR_CREATE);
            }
        } catch (DaoException | DbException ex) {
            LOG.error(ERROR_UPDATE, ex);
            throw new ServiceException(ERROR_UPDATE, ex);
        }
    }

    public void deleteReason(RejectionReason rejectionReason)
            throws ServiceException {
        try (DaoFactory daoFactory = new DaoFactory()) {
            Dao rejectionReasonDao = daoFactory.createRejectionReasonDao();
            if (!rejectionReasonDao.remove(rejectionReason)) {
                LOG.error(ERROR_CREATE);
                throw new ServiceException(ERROR_CREATE);
            }
        } catch (DaoException | DbException ex) {
            LOG.error(ERROR_DELETE, ex);
            throw new ServiceException(ERROR_DELETE, ex);
        }
    }

    private List getReasonByCriteria(Criteria criteria)
            throws ServiceException {
        try (DaoFactory daoFactory = new DaoFactory()) {
            Dao rejectionReasonDao = daoFactory
                    .createRejectionReasonDao();
            return rejectionReasonDao.read(criteria);
        } catch (DaoException | DbException ex) {
            LOG.error(REJECTION_REASON_DAO_ERROR, ex);
            throw new ServiceException(REJECTION_REASON_DAO_ERROR, ex);
        }
    }
}
