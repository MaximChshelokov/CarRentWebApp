package com.mv.schelokov.car_rent.model.services;

import com.mv.schelokov.car_rent.model.db.repository.exceptions.DbException;
import com.mv.schelokov.car_rent.model.db.repository.exceptions.RepositoryException;
import com.mv.schelokov.car_rent.model.db.repository.factories.CriteriaFactory;
import com.mv.schelokov.car_rent.model.db.repository.factories.RepositoryFactory;
import com.mv.schelokov.car_rent.model.db.repository.interfaces.Criteria;
import com.mv.schelokov.car_rent.model.db.repository.interfaces.Repository;
import com.mv.schelokov.car_rent.model.entities.Car;
import com.mv.schelokov.car_rent.model.entities.Make;
import com.mv.schelokov.car_rent.model.entities.Model;
import com.mv.schelokov.car_rent.model.services.exceptions.ServiceException;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class CarService {
    
    private static final Logger LOG = Logger.getLogger(CarService.class);
    private static final String CAR_CRITERIA_ERROR = "Failed to get car list "
            + "from the repository by the criteria";
    private static final String MODEL_NAME_ERROR = "Failed to get model by "
            + "name from the repository";
    private static final String MAKE_NAME_ERROR = "Failed to get make by "
            + "name from the repository";
    private static final String MODEL_CREATE_ERROR = "Failed to create new model";
    private static final String MAKE_CREATE_ERROR =  "Failed to create new make";
    private enum Operation { CREATE, UPDATE, DELETE }
    
    public List getAllCars() throws ServiceException {
        Criteria criteria = CriteriaFactory.getAllCars();
        return getCarsByCriteria(criteria);
    }
    
    public Car getCarById(int id) throws ServiceException {
        Criteria criteria = CriteriaFactory.getCarById(id);
        List result = getCarsByCriteria(criteria);
        if (result.isEmpty())
            return new Car();
        else
            return (Car) result.get(0);
    }
    
    public void deleteCar(Car car) throws ServiceException {
        operateCar(car, Operation.DELETE);
    }
    
    public void updateCar(Car car) throws ServiceException {
        operateCar(car, Operation.UPDATE);
    }
    
    public void createCar(Car car) throws ServiceException {
        operateCar(car, Operation.CREATE);
    }
    
    private void operateCar(Car car, Operation operation)
            throws ServiceException {
        try (RepositoryFactory repositoryFactory = new RepositoryFactory()) {
            Repository carRepository = repositoryFactory.getCarRepository();
            switch (operation) {
                case CREATE:
                    carRepository.add(car);
                    break;
                case UPDATE:
                    carRepository.update(car);
                    break;
                case DELETE:
                    carRepository.remove(car);
            }
        }
        catch (RepositoryException | DbException ex) {
            LOG.error(MODEL_NAME_ERROR, ex);
            throw new ServiceException(MODEL_NAME_ERROR, ex);
        }   
    }
    
    public List getModel(Model model) throws ServiceException {
        Criteria criteria = CriteriaFactory.findModel(model);
        try (RepositoryFactory repositoryFactory = new RepositoryFactory()) {
            Repository modelRepository = repositoryFactory.getModelRepository();
            return modelRepository.read(criteria);
        }
        catch (RepositoryException | DbException ex) {
            LOG.error(MODEL_NAME_ERROR, ex);
            throw new ServiceException(MODEL_NAME_ERROR, ex);
        }
    }
    
    public List createModel(Model model) throws ServiceException {
        try (RepositoryFactory repositoryFactory = new RepositoryFactory()) {
            Repository modelRepository = repositoryFactory.getModelRepository();
            modelRepository.add(model);
            repositoryFactory.commit();
            return getModel(model);
        } catch (RepositoryException | DbException ex) {
            LOG.error(MODEL_CREATE_ERROR, ex);
            throw new ServiceException(MODEL_CREATE_ERROR, ex);
        }
    }
    
    public List getMakeByName(String name) throws ServiceException {
        Criteria criteria = CriteriaFactory.getMakeByName(name);
        try (RepositoryFactory repositoryFactory = new RepositoryFactory()) {
            Repository makeRepository = repositoryFactory.getMakeRepository();
            return makeRepository.read(criteria);
        }
        catch (RepositoryException | DbException ex) {
            LOG.error(MAKE_NAME_ERROR, ex);
            throw new ServiceException(MAKE_NAME_ERROR, ex);
        }
    }
    
    public List createMake(Make make) throws ServiceException {
        try (RepositoryFactory repositoryFactory = new RepositoryFactory()) {
            Repository makeRepository = repositoryFactory.getMakeRepository();
            makeRepository.add(make);
            repositoryFactory.commit();
            return getMakeByName(make.getName());
        } catch (RepositoryException | DbException ex) {
            LOG.error(MAKE_CREATE_ERROR, ex);
            throw new ServiceException(MAKE_CREATE_ERROR, ex);
        }
    }
    
    private List getCarsByCriteria(Criteria criteria) throws ServiceException {
        try (RepositoryFactory repositoryFactory = new RepositoryFactory()) {
            Repository carRepository = repositoryFactory.getCarRepository();
            return carRepository.read(criteria);
        } catch (RepositoryException | DbException ex) {
            LOG.error(CAR_CRITERIA_ERROR, ex);
            throw new ServiceException(CAR_CRITERIA_ERROR, ex);
        }
    }
}
