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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class UserService {
    
    private static final String SALT = "NuiF9cD32Kaw3";
    
    public void RegisterNewUser(User user) throws ServiceException {
        try(RepositoryFactory repositoryFactory = new RepositoryFactory()) {
            user.setPassword(hashPassword(user.getPassword()));
            Repository userRepository = repositoryFactory.getUserRepository();
            userRepository.add(user);
        }
        catch (RepositoryException | DbException ex) {
            throw new ServiceException("Failed to create new user", ex);
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
            throw new ServiceException("Failed to check if the user does exist",
                    ex);
        }
    }   
    
    private String hashPassword(String password) throws ServiceException {
        try {
            return ShaHash.getSHA512Hash(password, SALT);
        } catch(NoSuchAlgorithmException ex) {
            throw new ServiceException("Hash algorithm SHA-512 not found", ex);
        }
    }
}
