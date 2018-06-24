package com.mv.schelokov.car_rent.model.services;

import com.mv.schelokov.car_rent.model.db.repository.UserRepository;
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
            hashUserPassword(user);
            Repository userRepository = repositoryFactory.getUserRepository();
            userRepository.add(user);
        }
        catch (RepositoryException | DbException ex) {
            throw new ServiceException("Failed to create new user", ex);
        }
    }
    
    public boolean isExistingUser(User user) throws ServiceException {
        hashUserPassword(user);
        Criteria criteria = CriteriaFactory.getUserFindLoginPassword(user);
        return isExistingByCriteria(criteria);
    }
    
    public boolean isExistingLogin(User user) throws ServiceException {
        Criteria criteria = CriteriaFactory.getUserFindLogin(user.getLogin());
        return isExistingByCriteria(criteria);
    }
    
    private boolean isExistingByCriteria(Criteria criteria) throws ServiceException {
        try(RepositoryFactory repositoryFactory = new RepositoryFactory()) {
            Repository userRepository = repositoryFactory.getUserRepository();
            return userRepository.read(criteria).size() == 1;
        }
        catch (RepositoryException | DbException ex) {
            throw new ServiceException("Failed to check if the user does exist",
                    ex);
        }
    }   
    
    private void hashUserPassword(User user) throws ServiceException {
        try {
            user.setPassword(ShaHash.getSHA512Hash(user.getPassword(), SALT));
        } catch(NoSuchAlgorithmException ex) {
            throw new ServiceException("Hash algorithm SHA-512 not found", ex);
        }
    }
}
