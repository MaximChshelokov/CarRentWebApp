package com.mv.schelokov.carent.model.services;

import com.mv.schelokov.carent.model.db.dao.exceptions.DbException;
import com.mv.schelokov.carent.model.db.dao.exceptions.DaoException;
import com.mv.schelokov.carent.model.db.dao.factories.CriteriaFactory;
import com.mv.schelokov.carent.model.db.dao.factories.DaoFactory;
import com.mv.schelokov.carent.model.db.dao.interfaces.Criteria;
import com.mv.schelokov.carent.model.entity.Car;
import com.mv.schelokov.carent.model.services.exceptions.ServiceException;
import java.util.List;
import org.apache.log4j.Logger;
import com.mv.schelokov.carent.model.db.dao.interfaces.Dao;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class CarService {
    
    private static final Logger LOG = Logger.getLogger(CarService.class);
    private static final String CAR_CRITERIA_ERROR = "Failed to get car list "
            + "from the DAO by the criteria";
    private static final String DELETE_ERROR = "Failed to delete a car";
    private static final String UPDATE_ERROR = "Failed to update a car";
    private static final String CREATE_ERROR = "Failed to create a car";
    private static final String INSTANCE_ERROR = "Failed to get instance";
    private static volatile CarService instance;

    public static CarService getInstance() throws ServiceException {
        CarService localInstance = instance;
        if (localInstance == null) {
            synchronized (CarService.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new CarService();
                }
            }
        }
        if (localInstance == null) {
            LOG.error(INSTANCE_ERROR);
            throw new ServiceException(INSTANCE_ERROR);
        }
        return localInstance;
    }

    public List getAllCars() throws ServiceException {
        Criteria criteria = CriteriaFactory.getAllCarsCriteria();
        return getCarsByCriteria(criteria);
    }
    
    public List getAvailableCars() throws ServiceException {
        Criteria criteria = CriteriaFactory.getAvailableCarsCriteria();
        return getCarsByCriteria(criteria);
    }
    
    public Car getCarById(int id) throws ServiceException {
        Criteria criteria = CriteriaFactory.getCarByIdCriteria(id);
        List result = getCarsByCriteria(criteria);
        if (result.isEmpty())
            return null;
        else
            return (Car) result.get(0);
    }
    
    public void deleteCar(Car car) throws ServiceException {
        try (DaoFactory daoFactory = new DaoFactory()) {
            Dao carDao = daoFactory.getCarDao();
            carDao.remove(car);
        }
        catch (DaoException | DbException ex) {
            LOG.error(DELETE_ERROR, ex);
            throw new ServiceException(DELETE_ERROR, ex);
        }
    }
    
    public void updateCar(Car car) throws ServiceException {
        try (DaoFactory daoFactory = new DaoFactory()) {
            Dao carDao = daoFactory.getCarDao();
            carDao.update(car);
        }
        catch (DaoException | DbException ex) {
            LOG.error(UPDATE_ERROR, ex);
            throw new ServiceException(UPDATE_ERROR, ex);
        }
    }
    
    public void createCar(Car car) throws ServiceException {
        try (DaoFactory daoFactory = new DaoFactory()) {
            Dao carDao = daoFactory.getCarDao();
            carDao.add(car);
        }
        catch (DaoException | DbException ex) {
            LOG.error(CREATE_ERROR, ex);
            throw new ServiceException(CREATE_ERROR, ex);
        }
    }
    
    private List getCarsByCriteria(Criteria criteria) throws ServiceException {
        try (DaoFactory daoFactory = new DaoFactory()) {
            Dao carDao = daoFactory.getCarDao();
            return carDao.read(criteria);
        } catch (DaoException | DbException ex) {
            LOG.error(CAR_CRITERIA_ERROR, ex);
            throw new ServiceException(CAR_CRITERIA_ERROR, ex);
        }
    }
    
    private CarService() {}
}
