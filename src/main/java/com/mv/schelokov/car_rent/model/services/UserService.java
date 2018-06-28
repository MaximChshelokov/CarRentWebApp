package com.mv.schelokov.car_rent.model.services;

import com.mv.schelokov.car_rent.model.db.repository.exceptions.DbException;
import com.mv.schelokov.car_rent.model.db.repository.exceptions.RepositoryException;
import com.mv.schelokov.car_rent.model.db.repository.factories.CriteriaFactory;
import com.mv.schelokov.car_rent.model.db.repository.factories.RepositoryFactory;
import com.mv.schelokov.car_rent.model.db.repository.interfaces.Criteria;
import com.mv.schelokov.car_rent.model.db.repository.interfaces.Repository;
import com.mv.schelokov.car_rent.model.entities.User;
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
    
    public static final Logger log = Logger.getLogger(UserService.class);
    private static final String SALT = "NuiF9cD32Kaw3";
    
    private static final String NEW_USER_ERROR = "Failed to create new user";
    private static final String CHECK_USER_ERROR = "Failed to check if the user"
            + " does exist";
    private static final String HASH_ERROR = "Hash algorithm SHA-512 not found";
    
    public void RegisterNewUser(User user) throws ServiceException {
        try(RepositoryFactory repositoryFactory = new RepositoryFactory()) {
            user.setPassword(hashPassword(user.getPassword()));
            Repository userRepository = repositoryFactory.getUserRepository();
            userRepository.add(user);
        }
        catch (RepositoryException | DbException ex) {
            log.error(NEW_USER_ERROR, ex);
            throw new ServiceException(NEW_USER_ERROR, ex);
        }
    }
    
    public List getUserByCredentials(String login, String password) 
            throws ServiceException {
        Criteria criteria = CriteriaFactory.getUserFindLoginPassword(login, 
                hashPassword(password));
        return getUsersByCriteria(criteria);
    }
    
    public List getUserByLogin(User user) throws ServiceException {
        Criteria criteria = CriteriaFactory.getUserFindLogin(user.getLogin());
        return getUsersByCriteria(criteria);
    }
    
    private List getUsersByCriteria(Criteria criteria) throws ServiceException {
        try(RepositoryFactory repositoryFactory = new RepositoryFactory()) {
            Repository userRepository = repositoryFactory.getUserRepository();
            return userRepository.read(criteria);
        }
        catch (RepositoryException | DbException ex) {
            log.error(CHECK_USER_ERROR, ex);
            throw new ServiceException(CHECK_USER_ERROR, ex);
        }
    }   
    
    private String hashPassword(String password) throws ServiceException {
        try {
            return ShaHash.getSHA512Hash(password, SALT);
        } catch(NoSuchAlgorithmException ex) {
            log.error(HASH_ERROR, ex);
            throw new ServiceException(HASH_ERROR, ex);
        }
    }
}
