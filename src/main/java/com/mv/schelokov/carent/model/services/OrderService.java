package com.mv.schelokov.carent.model.services;

import com.mv.schelokov.carent.model.db.dao.exceptions.DbException;
import com.mv.schelokov.carent.model.db.dao.exceptions.DaoException;
import com.mv.schelokov.carent.model.db.dao.factories.CriteriaFactory;
import com.mv.schelokov.carent.model.db.dao.factories.DaoFactory;
import com.mv.schelokov.carent.model.db.dao.interfaces.Criteria;
import com.mv.schelokov.carent.model.entity.RentOrder;
import com.mv.schelokov.carent.model.entity.User;
import com.mv.schelokov.carent.model.services.exceptions.ServiceException;
import com.mv.schelokov.carent.model.utils.DateUtils;
import java.util.List;
import org.apache.log4j.Logger;
import com.mv.schelokov.carent.model.db.dao.interfaces.Dao;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class OrderService {
    
    private static final Logger LOG = Logger.getLogger(OrderService.class);
    private static final String ORDER_DAO_ERROR = "Failed to get an order "
            + "list form the dao by the criteria";
    private static final String CREATE_ERROR = "Failed to create an order";
    private static final String UPDATE_ERROR = "Failed to update an order";
    private static final String DELETE_ERROR = "Failed to delete an order";
    private static final String INSTANCE_ERROR = "Failed to get instance";
    private static volatile OrderService instance;

    public static OrderService getInstance() throws ServiceException {
        OrderService localInstance = instance;
        if (localInstance == null) {
            synchronized (OrderService.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new OrderService();
                }
            }
        }
        if (localInstance == null) {
            LOG.error(INSTANCE_ERROR);
            throw new ServiceException(INSTANCE_ERROR);
        }
        return localInstance;
    }
    
    public List getAllOrders() throws ServiceException {
        Criteria criteria = CriteriaFactory.getAllOrdersOrderByApproved();
        List<RentOrder> orders = getOrdersByCriteria(criteria);
        if (!orders.isEmpty()) {
            for (RentOrder order : orders)
                calculateSum(order);
        }
        return orders;
    }
    
    public List getOpenedOrders() throws ServiceException {
        Criteria criteria = CriteriaFactory.getAllOpenedOrders();
        List<RentOrder> orders = getOrdersByCriteria(criteria);
        if (!orders.isEmpty()) {
            for (RentOrder order : orders) {
                calculateSum(order);
            }
        }
        return orders;
    }
    
    public RentOrder getOrderById(int id) throws ServiceException {
        Criteria criteria = CriteriaFactory.findOrderById(id);
        List resultList = getOrdersByCriteria(criteria);
        if (resultList.isEmpty())
            return null;
        RentOrder result = (RentOrder) resultList.get(0);
        calculateSum(result);
        return result;
    }
    
    public RentOrder getOrdersByUser(User user) throws ServiceException {
        Criteria criteria = CriteriaFactory.findOrderByUserId(user.getId());
        List resultList = getOrdersByCriteria(criteria);
        if (resultList.isEmpty())
            return null;
        RentOrder result = (RentOrder) resultList.get(0);
        calculateSum(result);
        return result;
    }
    
    public void addOrder(RentOrder order) throws ServiceException {
        try (DaoFactory daoFactory = new DaoFactory()) {
            Dao orderDao = daoFactory.getRentOrderDao();
            if (!orderDao.add(order)) {
                LOG.error(CREATE_ERROR);
                throw new ServiceException(CREATE_ERROR);
            }
        }
        catch (DaoException | DbException ex) {
            LOG.error(CREATE_ERROR, ex);
            throw new ServiceException(CREATE_ERROR, ex);
        }
    }
    
    public void updateOrder(RentOrder order) throws ServiceException {
        try (DaoFactory daoFactory = new DaoFactory()) {
            Dao orderDao = daoFactory.getRentOrderDao();
            if (!orderDao.update(order)) {
                LOG.error(UPDATE_ERROR);
                throw new ServiceException(UPDATE_ERROR);
            }
        }
        catch (DaoException | DbException ex) {
            LOG.error(UPDATE_ERROR, ex);
            throw new ServiceException(UPDATE_ERROR, ex);
        }
    }
    
    public void deleteOrder(RentOrder order) throws ServiceException {
        try (DaoFactory daoFactory = new DaoFactory()) {
            Dao orderDao = daoFactory.getRentOrderDao();
            if (!orderDao.remove(order)) {
                LOG.error(DELETE_ERROR);
                throw new ServiceException(DELETE_ERROR);
            }
        }
        catch (DaoException | DbException ex) {
            LOG.error(DELETE_ERROR, ex);
            throw new ServiceException(DELETE_ERROR, ex);
        }
    }

    private void calculateSum(RentOrder order) {
        order.setSum(order.getCar().getPrice() * DateUtils.days(order.getStartDate(),
                order.getEndDate()));
    }
    
    
    private List getOrdersByCriteria(Criteria criteria)
            throws ServiceException {
        try(DaoFactory daoFactory = new DaoFactory()) {
            Dao orderDao = daoFactory
                    .getRentOrderDao();
            return orderDao.read(criteria);
        } catch (DaoException | DbException ex) {
            LOG.error(ORDER_DAO_ERROR, ex);
            throw new ServiceException(ORDER_DAO_ERROR, ex);
        }
    }
    
    private OrderService() {}
}
