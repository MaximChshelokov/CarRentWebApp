package com.mv.schelokov.car_rent.model.services;

import com.mv.schelokov.car_rent.model.db.repository.exceptions.DbException;
import com.mv.schelokov.car_rent.model.db.repository.exceptions.RepositoryException;
import com.mv.schelokov.car_rent.model.db.repository.factories.CriteriaFactory;
import com.mv.schelokov.car_rent.model.db.repository.factories.RepositoryFactory;
import com.mv.schelokov.car_rent.model.db.repository.interfaces.Criteria;
import com.mv.schelokov.car_rent.model.db.repository.interfaces.Repository;
import com.mv.schelokov.car_rent.model.services.exceptions.ServiceException;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class OrderService {
    
    private static final Logger LOG = Logger.getLogger(OrderService.class);
    private static final String ORDER_REPOSITORY_ERROR = "Failed to get order "
            + "list form the repository by the criteria";
    
    public List getAllOrders() throws ServiceException {
        Criteria criteria = CriteriaFactory.getAllRentOrders();
        return getOrdersByCriteria(criteria);
    }
    
    private List getOrdersByCriteria(Criteria criteria) throws ServiceException {
        try(RepositoryFactory repositoryFactory = new RepositoryFactory()) {
            Repository orderRepository = repositoryFactory.getRentOrderRepository();
            return orderRepository.read(criteria);
        } catch (RepositoryException | DbException ex) {
            LOG.error(ORDER_REPOSITORY_ERROR, ex);
            throw new ServiceException(ORDER_REPOSITORY_ERROR, ex);
        }
    }
    
}
