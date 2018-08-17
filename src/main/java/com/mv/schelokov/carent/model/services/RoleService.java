
package com.mv.schelokov.carent.model.services;

import com.mv.schelokov.carent.model.db.dao.exceptions.DaoException;
import com.mv.schelokov.carent.model.db.dao.exceptions.DbException;
import com.mv.schelokov.carent.model.db.dao.factories.CriteriaFactory;
import com.mv.schelokov.carent.model.db.dao.factories.DaoFactory;
import com.mv.schelokov.carent.model.db.dao.interfaces.Criteria;
import com.mv.schelokov.carent.model.db.dao.interfaces.Dao;
import com.mv.schelokov.carent.model.services.exceptions.ServiceException;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class RoleService {
    
    private static final Logger LOG = Logger.getLogger(RoleService.class);
    private static final String ROLE_DAO_ERROR = "Failed to get roles "
            + "list from the dao";

    public List getAllRoles() throws ServiceException {
        try (DaoFactory daoFactory = new DaoFactory()) {
            Dao roleDao = daoFactory.createRoleDao();
            Criteria criteria = new CriteriaFactory().createAllRolesCriteria();
            return roleDao.read(criteria);
        }
        catch (DaoException | DbException ex) {
            LOG.error(ROLE_DAO_ERROR, ex);
            throw new ServiceException(ROLE_DAO_ERROR, ex);
        }
    }
}
