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
    
    private enum Operation { CREATE, UPDATE, DELETE }
    
    public void registerNewUser(User user) throws ServiceException {
        operateUser(user, Operation.CREATE);
    }
    
    public void updateUser(User user) throws ServiceException {
        operateUser(user, Operation.UPDATE);
    }
    
    public void deleteUser(User user) throws ServiceException {
        operateUser(user, Operation.DELETE);
    }
    
    public List getUserByCredentials(String login, String password) 
            throws ServiceException {
        Criteria criteria = CriteriaFactory.getUserFindLoginPassword(login, 
                hashPassword(password));
        return getUsersByCriteria(criteria);
    }
    
    public List getUserByLogin(String login) throws ServiceException {
        Criteria criteria = CriteriaFactory.getUserFindLogin(login);
        return getUsersByCriteria(criteria);
    }
    
    public int getUserNumber() throws ServiceException {
        return getAllUsers().size();
    }
    
    public List getAllUsers() throws ServiceException {
        Criteria criteria = CriteriaFactory.getAllUsersData();
        return getUserDataByCriteria(criteria);       
    }
    
    public UserData getUserDataById(int id) throws ServiceException {
        Criteria criteria = CriteriaFactory.getUserDataById(id);
        List result = getUserDataByCriteria(criteria);
        if (result.isEmpty())
            return new UserData();
        else
            return (UserData) result.get(0);
    }
    
    public void addUserData(UserData userData) throws ServiceException {
        operateUserData(userData, Operation.CREATE);
        if (userData.getUser().getId() == 0)
            registerNewUser(userData.getUser());
        else
            updateUser(userData.getUser());
    }
    
    public void updateUserData(UserData userData) throws ServiceException {
        operateUserData(userData, Operation.UPDATE);
        updateUser(userData.getUser());
    }
    
    public void deleteUserData(UserData userData) throws ServiceException {
        operateUserData(userData, Operation.DELETE);
        deleteUser(userData.getUser());
    }
    
    public List getAllRoles() throws ServiceException {
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
    
    private void operateUser(User user, Operation operation)
            throws ServiceException {
        try(RepositoryFactory repositoryFactory = new RepositoryFactory()) {
            Repository userRepository = repositoryFactory.getUserRepository();
            switch (operation) {
                case UPDATE:
                    userRepository.update(user);
                    break;
                case CREATE:
                    user.setPassword(hashPassword(user.getPassword()));
                    userRepository.add(user);
                    break;
                case DELETE:
                    userRepository.remove(user);
            }
        } catch (RepositoryException | DbException ex) {
            LOG.error(USER_REPOSITORY_ERROR, ex);
            throw new ServiceException(USER_REPOSITORY_ERROR, ex);
        }
    } 
    
    private void operateUserData(UserData userData, Operation operation) 
            throws ServiceException {
        try (RepositoryFactory repositoryFactory = new RepositoryFactory()) {
            Repository userDataRepository = repositoryFactory.getUserDataRepository();
            switch(operation) {
                case UPDATE:   
                    userDataRepository.update(userData);
                    break;
                case CREATE:
                    userData.setId(userData.getUser().getId());
                    userDataRepository.add(userData);
                    break;
                case DELETE:
                    userDataRepository.remove(userData);
            }
        } catch (RepositoryException | DbException ex) {
            LOG.error(USER_REPOSITORY_ADD, ex);
            throw new ServiceException(USER_REPOSITORY_ADD, ex);
        }   
    }
    
    private List getUsersByCriteria(Criteria criteria) throws ServiceException {
        try(RepositoryFactory repositoryFactory = new RepositoryFactory()) {
            Repository userRepository = repositoryFactory.getUserRepository();
            return userRepository.read(criteria);
        }
        catch (RepositoryException | DbException ex) {
            LOG.error(USER_CRITERIA_ERROR, ex);
            throw new ServiceException(USER_CRITERIA_ERROR, ex);
        }
    }
    
    private List getUserDataByCriteria(Criteria criteria)
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
    
    private String hashPassword(String password) throws ServiceException {
        try {
            return ShaHash.getSHA512Hash(password, SALT);
        } catch(NoSuchAlgorithmException ex) {
            LOG.error(HASH_ERROR, ex);
            throw new ServiceException(HASH_ERROR, ex);
        }
    }
}
