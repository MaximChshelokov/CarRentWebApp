package com.mv.schelokov.car_rent.model.services;

import com.mv.schelokov.car_rent.model.db.repository.exceptions.DbException;
import com.mv.schelokov.car_rent.model.db.repository.exceptions.RepositoryException;
import com.mv.schelokov.car_rent.model.db.repository.factories.CriteriaFactory;
import com.mv.schelokov.car_rent.model.db.repository.factories.RepositoryFactory;
import com.mv.schelokov.car_rent.model.db.repository.interfaces.Criteria;
import com.mv.schelokov.car_rent.model.db.repository.interfaces.Repository;
import com.mv.schelokov.car_rent.model.entities.User;
import com.mv.schelokov.car_rent.model.entities.UserData;
import com.mv.schelokov.car_rent.model.services.exceptions.ServiceException;
import com.mv.schelokov.car_rent.model.utils.ShaHash;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import org.apache.log4j.Logger;

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
        try (RepositoryFactory repositoryFactory = new RepositoryFactory()) {
            Repository roleRepository = repositoryFactory.getRoleRepository();
            Criteria criteria = CriteriaFactory.getAllRoles();
            return roleRepository.read(criteria);
        }
        catch (RepositoryException | DbException ex) {
            LOG.error(ROLE_REPOSITORY_ERROR, ex);
            throw new ServiceException(ROLE_REPOSITORY_ERROR, ex);
        }    
    }
    
    private static void operateUser(User user, Operation operation)
            throws ServiceException {
        try(RepositoryFactory repositoryFactory = new RepositoryFactory()) {
            Repository userRepository = repositoryFactory.getUserRepository();
            boolean result = false;
            switch (operation) {
                case UPDATE:
                    user.setPassword(hashPassword(user.getPassword()));
                    result = userRepository.update(user);
                    break;
                case CREATE:
                    user.setPassword(hashPassword(user.getPassword()));
                    result = userRepository.add(user);
                    break;
                case DELETE:
                    result = userRepository.remove(user);
            }
            if (!result)
                throw new ServiceException("Failed to operate UserRepository");
        } catch (RepositoryException | DbException ex) {
            LOG.error(USER_REPOSITORY_ERROR, ex);
            throw new ServiceException(USER_REPOSITORY_ERROR, ex);
        }
    } 
    
    private static void operateUserData(UserData userData, Operation operation) 
            throws ServiceException {
        try (RepositoryFactory repositoryFactory = new RepositoryFactory()) {
            Repository userDataRepository = repositoryFactory.getUserDataRepository();
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
        } catch (RepositoryException | DbException ex) {
            LOG.error(USER_REPOSITORY_ADD, ex);
            throw new ServiceException(USER_REPOSITORY_ADD, ex);
        }   
    }
    
    private static List getUsersByCriteria(Criteria criteria) throws ServiceException {
        try(RepositoryFactory repositoryFactory = new RepositoryFactory()) {
            Repository userRepository = repositoryFactory.getUserRepository();
            return userRepository.read(criteria);
        }
        catch (RepositoryException | DbException ex) {
            LOG.error(USER_CRITERIA_ERROR, ex);
            throw new ServiceException(USER_CRITERIA_ERROR, ex);
        }
    }
    
    private static List getUserDataByCriteria(Criteria criteria)
            throws ServiceException {
        try (RepositoryFactory repositoryFactory = new RepositoryFactory()) {
            Repository userDataRepository = 
                    repositoryFactory.getUserDataRepository();
            return userDataRepository.read(criteria);
        } catch (RepositoryException | DbException ex) {
            LOG.error(USER_DATA_REPOSITORY_ERROR, ex);
            throw new ServiceException(USER_DATA_REPOSITORY_ERROR, ex);
        }
    }
    
    private static String hashPassword(String password) throws ServiceException {
        try {
            return ShaHash.getSHA512Hash(password, SALT);
        } catch(NoSuchAlgorithmException ex) {
            LOG.error(HASH_ERROR, ex);
            throw new ServiceException(HASH_ERROR, ex);
        }
    }
}
