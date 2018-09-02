package com.mv.schelokov.carent.model.services;

import com.mv.schelokov.carent.model.db.dao.exceptions.DbException;
import com.mv.schelokov.carent.model.db.dao.exceptions.DaoException;
import com.mv.schelokov.carent.model.db.dao.factories.CriteriaFactory;
import com.mv.schelokov.carent.model.db.dao.factories.DaoFactory;
import com.mv.schelokov.carent.model.db.dao.interfaces.Criteria;
import com.mv.schelokov.carent.model.entity.RentOrder;
import com.mv.schelokov.carent.model.entity.User;
import com.mv.schelokov.carent.model.services.exceptions.ServiceException;
import java.util.List;
import org.apache.log4j.Logger;
import com.mv.schelokov.carent.model.db.dao.interfaces.Dao;
import com.mv.schelokov.carent.model.entity.Car;
import com.mv.schelokov.carent.model.entity.RejectionReason;
import com.mv.schelokov.carent.model.entity.builders.RentOrderBuilder;
import com.mv.schelokov.carent.model.utils.Period;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class RentOrderService {
    
    private static final Logger LOG = Logger.getLogger(RentOrderService.class);
    private static final String ORDER_DAO_ERROR = "Failed to get an order "
            + "list form the dao by the criteria";
    private static final String CREATE_ERROR = "Failed to create an order";
    private static final String UPDATE_ERROR = "Failed to update an order";
    private static final String DELETE_ERROR = "Failed to delete an order";
    
    public List getAllOrders() throws ServiceException {
        Criteria criteria = new CriteriaFactory()
                .createOrdersByApprovedCriteria();
        List<RentOrder> orders = getOrdersByCriteria(criteria);
        if (!orders.isEmpty()) {
            for (RentOrder order : orders)
                calculateSum(order);
        }
        return orders;
    }
    
    public List getOpenedOrders() throws ServiceException {
        Criteria criteria = new CriteriaFactory()
                .createAllOpenedOrdersCriteria();
        List<RentOrder> orders = getOrdersByCriteria(criteria);
        if (!orders.isEmpty()) {
            for (RentOrder order : orders) {
                calculateSum(order);
            }
        }
        return orders;
    }
    
    public RentOrder getOrderById(int id) throws ServiceException {
        Criteria criteria = new CriteriaFactory().createOrderByIdCriteria(id);
        List resultList = getOrdersByCriteria(criteria);
        if (resultList.isEmpty())
            return null;
        RentOrder result = (RentOrder) resultList.get(0);
        calculateSum(result);
        return result;
    }
    
    public RentOrder getLastOrderByUser(User user) throws ServiceException {
        Criteria criteria = new CriteriaFactory()
                .createOrderByUserIdCriteria(user.getId());
        List<RentOrder> resultList = getOrdersByCriteria(criteria);
        RentOrder result = getEmptyOrderByUser(user);
        for (RentOrder order : resultList)
            if (order.getApprovedBy().getLogin() == null
                    || !order.getCar().isAvailable()
                    && order.getRejectionReason().getReason() == null) {
                result = order;
            }
        if (result.getCar() != null)
            calculateSum(result);
        return result;
    }
    
    public RentOrder getLastRejectedOrderByUser(User user)
            throws ServiceException {
        Criteria criteria = new CriteriaFactory()
                .createOrderByUserIdCriteria(user.getId());
        List<RentOrder> resultList = getOrdersByCriteria(criteria);
        RentOrder result = getEmptyOrderByUser(user);
        RentOrder lastOrder = resultList.get(resultList.size() - 1);
        if (lastOrder.getRejectionReason().getReason() != null)
            result = lastOrder;
        if (result.getCar() != null) {
            calculateSum(result);
        }
        return result;
    }
    
    public void addOrder(RentOrder order) throws ServiceException {
        try (DaoFactory daoFactory = new DaoFactory()) {
            Dao orderDao = daoFactory.createRentOrderDao();
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
            Dao orderDao = daoFactory.createRentOrderDao();
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
            Dao orderDao = daoFactory.createRentOrderDao();
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
        order.setSum(order.getCar().getPrice()
                * new Period(order.getStartDate()
                        , order.getEndDate()).getDays());
    }
    
    
    private List getOrdersByCriteria(Criteria criteria)
            throws ServiceException {
        try(DaoFactory daoFactory = new DaoFactory()) {
            Dao orderDao = daoFactory
                    .createRentOrderDao();
            return orderDao.read(criteria);
        } catch (DaoException | DbException ex) {
            LOG.error(ORDER_DAO_ERROR, ex);
            throw new ServiceException(ORDER_DAO_ERROR, ex);
        }
    }
    
    private RentOrder getEmptyOrderByUser(User user) {
        return new RentOrderBuilder()
                .byUser(user)
                .getRentOrder();
    }
}
