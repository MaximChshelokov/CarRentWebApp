package com.mv.schelokov.carent.model.services;

import com.mv.schelokov.carent.model.db.dao.exceptions.DbException;
import com.mv.schelokov.carent.model.db.dao.exceptions.DaoException;
import com.mv.schelokov.carent.model.db.dao.factories.CriteriaFactory;
import com.mv.schelokov.carent.model.db.dao.factories.DaoFactory;
import com.mv.schelokov.carent.model.db.dao.interfaces.Criteria;
import com.mv.schelokov.carent.model.entity.User;
import com.mv.schelokov.carent.model.services.exceptions.ServiceException;
import com.mv.schelokov.carent.model.utils.ShaHash;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import org.apache.log4j.Logger;
import com.mv.schelokov.carent.model.db.dao.interfaces.Dao;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class UserService {
    
    private static final Logger LOG = Logger.getLogger(UserService.class);
    private static final String USER_CRITERIA_ERROR = "Failed to get user list"
            + " from the dao by the criteria";
    private static final String HASH_ERROR = "Hash algorithm SHA-512 not found";
    private static final String REGISTER_ERROR = "Failed to create new user";
    private static final String UPDATE_ERROR = "Failed to update a user";
    private static final String DELETE_ERROR = "Failed to delete a user";
    private static final String INSTANCE_ERROR = "Failed to get instance";
    private static volatile UserService instance;

    public static UserService getInstance() throws ServiceException {
        UserService localInstance = instance;
        if (localInstance == null) {
            synchronized (UserService.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new UserService();
                }
            }
        }
        if (localInstance == null) {
            LOG.error(INSTANCE_ERROR);
            throw new ServiceException(INSTANCE_ERROR);
        }
        return localInstance;
    }
    
    public void registerNewUser(User user) throws ServiceException {
        try (DaoFactory daoFactory = new DaoFactory()) {
            User userCopy = (User) user.clone();
            Dao userDao = daoFactory.getUserDao();
            userCopy.setPassword(hashPassword(userCopy.getPassword()));
            if (!userDao.add(userCopy)) {
                LOG.error(REGISTER_ERROR);
                throw new ServiceException(REGISTER_ERROR);
            }
        }
        catch (DaoException | DbException | CloneNotSupportedException ex) {
            LOG.error(REGISTER_ERROR, ex);
            throw new ServiceException(REGISTER_ERROR, ex);
        }
    }
    
    public void updateUser(User user) throws ServiceException {
        try (DaoFactory daoFactory = new DaoFactory()) {
            User userCopy = (User) user.clone();
            Dao userDao = daoFactory.getUserDao();
            userCopy.setPassword(hashPassword(userCopy.getPassword()));
            if (!userDao.update(userCopy)) {
                LOG.error(UPDATE_ERROR);
                throw new ServiceException(UPDATE_ERROR);
            }
        }
        catch (DaoException | DbException | CloneNotSupportedException ex) {
            LOG.error(UPDATE_ERROR, ex);
            throw new ServiceException(UPDATE_ERROR, ex);
        }
    }
    
    public void deleteUser(User user) throws ServiceException {
        try (DaoFactory daoFactory = new DaoFactory()) {
            Dao userDao = daoFactory.getUserDao();
            if (!userDao.remove(user)) {
                LOG.error(DELETE_ERROR);
                throw new ServiceException(DELETE_ERROR);
            }
        }
        catch (DaoException | DbException ex) {
            LOG.error(DELETE_ERROR, ex);
            throw new ServiceException(DELETE_ERROR, ex);
        }
    }
    
    public List getUserByCredentials(User user) 
            throws ServiceException {
        Criteria criteria = CriteriaFactory.getUserFindLoginPassword(
                user.getLogin(), hashPassword(user.getPassword()));
        return getUsersByCriteria(criteria);
    }
    
    public List getUserByLogin(String login) throws ServiceException {
        Criteria criteria = CriteriaFactory.getUserFindLogin(login);
        return getUsersByCriteria(criteria);
    }
    
    private List getUsersByCriteria(Criteria criteria) throws ServiceException {
        try(DaoFactory daoFactory = new DaoFactory()) {
            Dao userDao = daoFactory.getUserDao();
            return userDao.read(criteria);
        }
        catch (DaoException | DbException ex) {
            LOG.error(USER_CRITERIA_ERROR, ex);
            throw new ServiceException(USER_CRITERIA_ERROR, ex);
        }
    }
    
    private String hashPassword(String password) throws ServiceException {
        try {
            String result = new ShaHash(password).getSHA512Hash();
            if (result == null) {
                LOG.error(HASH_ERROR);
                throw new ServiceException(HASH_ERROR);
            }
            return result;
        } catch(NoSuchAlgorithmException ex) {
            LOG.error(HASH_ERROR, ex);
            throw new ServiceException(HASH_ERROR, ex);
        }
    }
    
    private UserService() {}
}
