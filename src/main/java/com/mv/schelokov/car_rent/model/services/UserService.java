package com.mv.schelokov.car_rent.model.services;

import com.mv.schelokov.car_rent.model.db.dao.exceptions.DbException;
import com.mv.schelokov.car_rent.model.db.dao.exceptions.DaoException;
import com.mv.schelokov.car_rent.model.db.dao.factories.CriteriaFactory;
import com.mv.schelokov.car_rent.model.db.dao.factories.DaoFactory;
import com.mv.schelokov.car_rent.model.db.dao.interfaces.Criteria;
import com.mv.schelokov.car_rent.model.entity.User;
import com.mv.schelokov.car_rent.model.entity.UserData;
import com.mv.schelokov.car_rent.model.services.exceptions.ServiceException;
import com.mv.schelokov.car_rent.model.utils.ShaHash;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import org.apache.log4j.Logger;
import com.mv.schelokov.car_rent.model.db.dao.interfaces.Dao;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class UserService {
    
    private static final Logger LOG = Logger.getLogger(UserService.class);
    private static final String SALT = "NuiF9cD32Kaw3";
    
    private static final String USER_REPOSITORY_ERROR = "Failed to write the user";
    private static final String USER_CRITERIA_ERROR = "Failed to get user list"
            + " from the repository by the criteria";
    private static final String USER_DATA_REPOSITORY_ERROR = "Failed to get user"
            + " data from the repository by the criteria";
    private static final String ROLE_REPOSITORY_ERROR = "Failed to get roles "
            + "list from the repository";
    private static final String USER_REPOSITORY_ADD = "Failed to write the user data";
    private static final String HASH_ERROR = "Hash algorithm SHA-512 not found";
    
    private static enum Operation { CREATE, UPDATE, DELETE }
    
    public static void registerNewUser(User user) throws ServiceException {
        operateUser(user, Operation.CREATE);
    }
    
    public static void updateUser(User user) throws ServiceException {
        operateUser(user, Operation.UPDATE);
    }
    
    public static void deleteUser(User user) throws ServiceException {
        operateUser(user, Operation.DELETE);
    }
    
    public static List getUserByCredentials(User user) 
            throws ServiceException {
        Criteria criteria = CriteriaFactory.getUserFindLoginPassword(
                user.getLogin(), hashPassword(user.getPassword()));
        return getUsersByCriteria(criteria);
    }
    
    public static List getUserByLogin(String login) throws ServiceException {
        Criteria criteria = CriteriaFactory.getUserFindLogin(login);
        return getUsersByCriteria(criteria);
    }
    
    public static int getUserNumber() throws ServiceException {
        return getAllUsers().size();
    }
    
    public static List getAllUsers() throws ServiceException {
        Criteria criteria = CriteriaFactory.getAllUsersData();
        return getUserDataByCriteria(criteria);       
    }
    
    public static UserData getUserDataById(int id) throws ServiceException {
        Criteria criteria = CriteriaFactory.getUserDataById(id);
        List result = getUserDataByCriteria(criteria);
        if (result.isEmpty())
            return null;
        else
            return (UserData) result.get(0);
    }
    
    public static void addUserData(UserData userData) throws ServiceException {
        operateUserData(userData, Operation.CREATE);
        if (userData.getUser().getId() == 0)
            registerNewUser(userData.getUser());
        else
            updateUser(userData.getUser());
    }
    
    public static void updateUserData(UserData userData) throws ServiceException {
        operateUserData(userData, Operation.UPDATE);
        updateUser(userData.getUser());
    }
    
    public static void deleteUserData(UserData userData) throws ServiceException {
        operateUserData(userData, Operation.DELETE);
        deleteUser(userData.getUser());
    }
    
    public static List getAllRoles() throws ServiceException {
        try (DaoFactory repositoryFactory = new DaoFactory()) {
            Dao roleRepository = repositoryFactory.getRoleDao();
            Criteria criteria = CriteriaFactory.getAllRoles();
            return roleRepository.read(criteria);
        }
        catch (DaoException | DbException ex) {
            LOG.error(ROLE_REPOSITORY_ERROR, ex);
            throw new ServiceException(ROLE_REPOSITORY_ERROR, ex);
        }    
    }
    
    private static void operateUser(User user, Operation operation)
            throws ServiceException {
        try(DaoFactory repositoryFactory = new DaoFactory()) {
            User userCopy = (User) user.clone();
            Dao userRepository = repositoryFactory.getUserDao();
            boolean result = false;
            switch (operation) {
                case UPDATE:
                    userCopy.setPassword(hashPassword(userCopy.getPassword()));
                    result = userRepository.update(userCopy);
                    break;
                case CREATE:
                    userCopy.setPassword(hashPassword(userCopy.getPassword()));
                    result = userRepository.add(userCopy);
                    break;
                case DELETE:
                    result = userRepository.remove(user);
            }
            if (!result)
                throw new ServiceException("Failed to operate UserRepository");
        } catch (DaoException | DbException | CloneNotSupportedException
                ex) {
            LOG.error(USER_REPOSITORY_ERROR, ex);
            throw new ServiceException(USER_REPOSITORY_ERROR, ex);
        }
    } 
    
    private static void operateUserData(UserData userData, Operation operation) 
            throws ServiceException {
        try (DaoFactory repositoryFactory = new DaoFactory()) {
            Dao userDataRepository = repositoryFactory.getUserDataDao();
            boolean result = false;
            switch(operation) {
                case UPDATE:   
                    result = userDataRepository.update(userData);
                    break;
                case CREATE:
                    userData.setId(userData.getUser().getId());
                    result = userDataRepository.add(userData);
                    break;
                case DELETE:
                    result = userDataRepository.remove(userData);
            }
            if (!result) {
                throw new ServiceException("Failed to operate UserDataRepository");
            }  
        } catch (DaoException | DbException ex) {
            LOG.error(USER_REPOSITORY_ADD, ex);
            throw new ServiceException(USER_REPOSITORY_ADD, ex);
        }   
    }
    
    private static List getUsersByCriteria(Criteria criteria) throws ServiceException {
        try(DaoFactory repositoryFactory = new DaoFactory()) {
            Dao userRepository = repositoryFactory.getUserDao();
            return userRepository.read(criteria);
        }
        catch (DaoException | DbException ex) {
            LOG.error(USER_CRITERIA_ERROR, ex);
            throw new ServiceException(USER_CRITERIA_ERROR, ex);
        }
    }
    
    private static List getUserDataByCriteria(Criteria criteria)
            throws ServiceException {
        try (DaoFactory repositoryFactory = new DaoFactory()) {
            Dao userDataRepository = 
                    repositoryFactory.getUserDataDao();
            return userDataRepository.read(criteria);
        } catch (DaoException | DbException ex) {
            LOG.error(USER_DATA_REPOSITORY_ERROR, ex);
            throw new ServiceException(USER_DATA_REPOSITORY_ERROR, ex);
        }
    }
    
    private static String hashPassword(String password) throws ServiceException {
        try {
            String result = ShaHash.getSHA512Hash(password, SALT);
            if (result == null)
                throw new ServiceException(HASH_ERROR);
            return result;
        } catch(NoSuchAlgorithmException ex) {
            LOG.error(HASH_ERROR, ex);
            throw new ServiceException(HASH_ERROR, ex);
        }
    }
}
