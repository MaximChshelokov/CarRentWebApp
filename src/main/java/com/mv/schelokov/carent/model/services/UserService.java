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
import com.mv.schelokov.carent.model.validators.UserValidator;
import com.mv.schelokov.carent.model.validators.ValidationResult;

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
    
    public int validateAndCreateUser(User user, String repeatPassword)
            throws ServiceException {
        
        int validationResult = new UserValidator(user).validate();

        if (validationResult == ValidationResult.OK
                && !user.getPassword().equals(repeatPassword)) {
            validationResult = ValidationResult.PASSWORDS_NOT_MATCH;
        }
        
        if (validationResult == ValidationResult.OK &&
                !getUserByLogin(user.getLogin()).isEmpty()) {
            validationResult = ValidationResult.SAME_LOGIN;
        }
        
        if (validationResult == ValidationResult.OK)
            registerNewUser(user);

        return validationResult;
    }
    
    public int validateLoginUser(User user) throws ServiceException {
        int validationResult = new UserValidator(user).validate();
        
        if (getUserByCredentials(user).getId() == 0)
            validationResult = ValidationResult.USER_NOT_FOUND;
 
        return validationResult;
    }
    
    public void registerNewUser(User user) throws ServiceException {
        try (DaoFactory daoFactory = new DaoFactory()) {
            User userCopy = (User) user.clone();
            Dao userDao = daoFactory.createUserDao();
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
        
        User oldUser = getUserById(user.getId());

        try (DaoFactory daoFactory = new DaoFactory()) {
            User userCopy = (User) user.clone();
            Dao userDao = daoFactory.createUserDao();
            if (!userCopy.getPassword().equals(oldUser.getPassword()))
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
            Dao userDao = daoFactory.createUserDao();
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
    
    public User getUserByCredentials(User user) throws ServiceException {
        List<User> userList = getUserListByCredentials(user);
        if (!userList.isEmpty())
            return userList.get(0);
        return new User();
    }
    
    public List getUserByLogin(String login) throws ServiceException {
        Criteria criteria = new CriteriaFactory()
                .createUserFindLoginCriteria(login);
        return getUsersByCriteria(criteria);
    }
    
    public User getUserById(int id) throws ServiceException {
        Criteria criteria = new CriteriaFactory()
                .createUserByIdCriteria(id);
        List userList = getUsersByCriteria(criteria);
        if (userList.isEmpty())
            return null;
        else
            return (User) userList.get(0);
    }
    
    private List getUserListByCredentials(User user)
            throws ServiceException {
        Criteria criteria = new CriteriaFactory()
                .createUserFindLoginPasswordCriteria(
                        user.getLogin(), hashPassword(user.getPassword()));
        return getUsersByCriteria(criteria);
    }
    
    private List getUsersByCriteria(Criteria criteria) throws ServiceException {
        try(DaoFactory daoFactory = new DaoFactory()) {
            Dao userDao = daoFactory.createUserDao();
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
}
