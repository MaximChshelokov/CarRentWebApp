package com.mv.schelokov.carent.model.services;

import com.mv.schelokov.carent.model.db.dao.exceptions.DaoException;
import com.mv.schelokov.carent.model.db.dao.exceptions.DbException;
import com.mv.schelokov.carent.model.db.dao.factories.CriteriaFactory;
import com.mv.schelokov.carent.model.db.dao.factories.DaoFactory;
import com.mv.schelokov.carent.model.db.dao.interfaces.Criteria;
import com.mv.schelokov.carent.model.db.dao.interfaces.Dao;
import com.mv.schelokov.carent.model.entity.UserData;
import com.mv.schelokov.carent.model.services.exceptions.ServiceException;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class UserDataService {
    
    private static final Logger LOG = Logger.getLogger(UserDataService.class);
    private static final String ERROR_CREATE = "Failed to create user data";
    private static final String UPDATE_ERROR = "Failed to update user data";
    private static final String DELETE_ERROR = "Failed to delete user data";
    private static final String USER_DATA_DAO_ERROR = "Failed to get user"
            + " data from the DAO by the criteria";

    public UserData getUserDataById(int id) throws ServiceException {
        Criteria criteria = new CriteriaFactory()
                .createUserDataByIdCriteria(id);
        List result = getUserDataByCriteria(criteria);
        if (result.isEmpty()) {
            return null;
        } else {
            return (UserData) result.get(0);
        }
    }
    
    public void addUserData(UserData userData) throws ServiceException {
        try (DaoFactory daoFactory = new DaoFactory()) {
            Dao userDataDao = daoFactory.createUserDataDao();
            if (!userDataDao.add(userData)) {
                LOG.error(ERROR_CREATE);
                throw new ServiceException(ERROR_CREATE);
            }
        }
        catch (DaoException | DbException ex) {
            LOG.error(ERROR_CREATE, ex);
            throw new ServiceException(ERROR_CREATE, ex);
        }
        
        UserService userService = new UserService();
        
        if (userData.getUser().getId() == 0) {
            userService.registerNewUser(userData.getUser());
        } else {
            userService.updateUser(userData.getUser());
        }
    }

    public void updateUserData(UserData userData) throws ServiceException {
        try (DaoFactory daoFactory = new DaoFactory()) {
            Dao userDataDao = daoFactory.createUserDataDao();
            if (!userDataDao.update(userData)) {
                LOG.error(UPDATE_ERROR);
                throw new ServiceException(UPDATE_ERROR);
            }
        }
        catch (DaoException | DbException ex) {
            LOG.error(UPDATE_ERROR, ex);
            throw new ServiceException(UPDATE_ERROR, ex);
        }
        
        UserService userService = new UserService();
        userService.updateUser(userData.getUser());
    }

    public void deleteUserData(UserData userData) throws ServiceException {
        try (DaoFactory daoFactory = new DaoFactory()) {
            Dao userDataDao = daoFactory.createUserDataDao();
            if (!userDataDao.remove(userData)) {
                LOG.error(DELETE_ERROR);
                throw new ServiceException(DELETE_ERROR);
            }
        }
        catch (DaoException | DbException ex) {
            LOG.error(DELETE_ERROR, ex);
            throw new ServiceException(DELETE_ERROR, ex);
        }
        
        UserService userService = new UserService();
        userService.deleteUser(userData.getUser());
    }
    
    public List getAllUsers() throws ServiceException {
        Criteria criteria = new CriteriaFactory().createAllUsersDataCriteria();
        return getUserDataByCriteria(criteria);
    }
    
    private List getUserDataByCriteria(Criteria criteria)
            throws ServiceException {
        try (DaoFactory daoFactory = new DaoFactory()) {
            Dao userDataDao
                    = daoFactory.createUserDataDao();
            return userDataDao.read(criteria);
        }
        catch (DaoException | DbException ex) {
            LOG.error(USER_DATA_DAO_ERROR, ex);
            throw new ServiceException(USER_DATA_DAO_ERROR, ex);
        }
    }
}
