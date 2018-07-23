package com.mv.schelokov.car_rent.model.services;

import com.mv.schelokov.car_rent.model.db.repository.exceptions.DbException;
import com.mv.schelokov.car_rent.model.db.repository.exceptions.RepositoryException;
import com.mv.schelokov.car_rent.model.db.repository.factories.CriteriaFactory;
import com.mv.schelokov.car_rent.model.db.repository.factories.RepositoryFactory;
import com.mv.schelokov.car_rent.model.db.repository.interfaces.Criteria;
import com.mv.schelokov.car_rent.model.db.repository.interfaces.Repository;
import com.mv.schelokov.car_rent.model.entities.RentOrder;
import com.mv.schelokov.car_rent.model.entities.User;
import com.mv.schelokov.car_rent.model.services.exceptions.ServiceException;
import com.mv.schelokov.car_rent.model.utils.DateUtils;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class OrderService {
    
    private static final Logger LOG = Logger.getLogger(OrderService.class);
    private static final String ORDER_REPOSITORY_ERROR = "Failed to get an order "
            + "list form the repository by the criteria";
    private static final String ORDER_OPERATION_ERROR = "Failed to write an order";
    
    private static enum Operation {CREATE, UPDATE, DELETE}

    
    public static List getAllOrders() throws ServiceException {
        Criteria criteria = CriteriaFactory.getAllOrdersOrderByApproved();
        List<RentOrder> orders = getOrdersByCriteria(criteria);
        if (!orders.isEmpty()) {
            for (RentOrder order : orders)
                calculateSum(order);
        }
        return orders;
    }
    
    public static List getOpenedOrders() throws ServiceException {
        Criteria criteria = CriteriaFactory.getAllOpenedOrders();
        List<RentOrder> orders = getOrdersByCriteria(criteria);
        if (!orders.isEmpty()) {
            for (RentOrder order : orders) {
                calculateSum(order);
            }
        }
        return orders;
    }
    
    public static RentOrder getOrderById(int id) throws ServiceException {
        Criteria criteria = CriteriaFactory.findOrderById(id);
        List resultList = getOrdersByCriteria(criteria);
        if (resultList.isEmpty())
            return null;
        RentOrder result = (RentOrder) resultList.get(0);
        calculateSum(result);
        return result;
    }
    
    public static RentOrder getOrdersByUser(User user) throws ServiceException {
        Criteria criteria = CriteriaFactory.findOrderByUserId(user.getId());
        List resultList = getOrdersByCriteria(criteria);
        if (resultList.isEmpty())
            return null;
        RentOrder result = (RentOrder) resultList.get(0);
        calculateSum(result);
        return result;
    }
    
    public static void addOrder(RentOrder order) throws ServiceException {
        operateOrder(order, Operation.CREATE);
    }
    
    public static void updateOrder(RentOrder order) throws ServiceException {
        operateOrder(order, Operation.UPDATE);
    }
    
    public static void deleteOrder(RentOrder order) throws ServiceException {
        operateOrder(order, Operation.DELETE);
    }
    
    private static void operateOrder(RentOrder order, Operation operation)
            throws ServiceException {
        try (RepositoryFactory repositoryFactory = new RepositoryFactory()) {
            Repository orderRepository = repositoryFactory
                    .getRentOrderRepository();
            boolean result = false;
            switch (operation) {
                case CREATE:
                    result = orderRepository.add(order);
                    break;
                case UPDATE:
                    result = orderRepository.update(order);
                    break;
                case DELETE:
                    result = orderRepository.remove(order);
            }
            if (!result)
                throw new ServiceException("Unable to operate to OrderRepository");
        }
        catch (RepositoryException | DbException ex) {
            LOG.error(ORDER_OPERATION_ERROR, ex);
            throw new ServiceException(ORDER_OPERATION_ERROR, ex);
        } 
    }
    
    private static void calculateSum(RentOrder order) {
        order.setSum(order.getCar().getPrice() * DateUtils.days(order.getStartDate(),
                order.getEndDate()));
    }
    
    
    private static List getOrdersByCriteria(Criteria criteria)
            throws ServiceException {
        try(RepositoryFactory repositoryFactory = new RepositoryFactory()) {
            Repository orderRepository = repositoryFactory
                    .getRentOrderRepository();
            return orderRepository.read(criteria);
        } catch (RepositoryException | DbException ex) {
            LOG.error(ORDER_REPOSITORY_ERROR, ex);
            throw new ServiceException(ORDER_REPOSITORY_ERROR, ex);
        }
    }
    
}
