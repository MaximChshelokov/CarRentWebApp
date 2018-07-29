package com.mv.schelokov.car_rent.model.services;

import com.mv.schelokov.car_rent.model.db.dao.exceptions.DbException;
import com.mv.schelokov.car_rent.model.db.dao.exceptions.DaoException;
import com.mv.schelokov.car_rent.model.db.dao.factories.CriteriaFactory;
import com.mv.schelokov.car_rent.model.db.dao.factories.DaoFactory;
import com.mv.schelokov.car_rent.model.db.dao.interfaces.Criteria;
import com.mv.schelokov.car_rent.model.entity.Car;
import com.mv.schelokov.car_rent.model.entity.CarMake;
import com.mv.schelokov.car_rent.model.entity.CarModel;
import com.mv.schelokov.car_rent.model.entity.builders.CarMakeBuilder;
import com.mv.schelokov.car_rent.model.entity.builders.CarModelBuilder;
import com.mv.schelokov.car_rent.model.services.exceptions.ServiceException;
import java.util.List;
import org.apache.log4j.Logger;
import com.mv.schelokov.car_rent.model.db.dao.interfaces.Dao;

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
    private static enum Operation { CREATE, UPDATE, DELETE }
    
    public static List getAllCars() throws ServiceException {
        Criteria criteria = CriteriaFactory.getAllCars();
        return getCarsByCriteria(criteria);
    }
    
    public static List getAvailableCars() throws ServiceException {
        Criteria criteria = CriteriaFactory.getAvailableCars();
        return getCarsByCriteria(criteria);
    }
    
    public static Car getCarById(int id) throws ServiceException {
        Criteria criteria = CriteriaFactory.getCarById(id);
        List result = getCarsByCriteria(criteria);
        if (result.isEmpty())
            return null;
        else
            return (Car) result.get(0);
    }
    
    public static void deleteCar(Car car) throws ServiceException {
        operateCar(car, Operation.DELETE);
    }
    
    public static void updateCar(Car car) throws ServiceException {
        operateCar(car, Operation.UPDATE);
    }
    
    public static void createCar(Car car) throws ServiceException {
        operateCar(car, Operation.CREATE);
    }
    
    private static void operateCar(Car car, Operation operation)
            throws ServiceException {
        try (DaoFactory repositoryFactory = new DaoFactory()) {
            Dao carRepository = repositoryFactory.getCarDao();
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
        catch (DaoException | DbException ex) {
            LOG.error(MODEL_NAME_ERROR, ex);
            throw new ServiceException(MODEL_NAME_ERROR, ex);
        }   
    }
    
    public static CarModel getModelByNameOrCreate(String modelName, String makeName)
            throws ServiceException {
        if (modelName == null || modelName.isEmpty()) {
            return new CarModelBuilder().setId(0).getCarModel();
        }
        CarModel model = new CarModelBuilder()
                .setName(modelName)
                .setCarMake(getMakeByNameOrCreate(makeName))
                .getCarModel();
        List modelList = getModel(model);
        if (modelList.isEmpty()) {
            modelList = createModel(model);
        }
        return (CarModel) modelList.get(0);
    }

    private static CarMake getMakeByNameOrCreate(String makeName)
            throws ServiceException {
        if (makeName == null || makeName.isEmpty()) {
            return new CarMakeBuilder().setId(0).getCarMake();
        }
        List makeList = getMakeByName(makeName);
        if (makeList.isEmpty()) {
            makeList = createMake(new CarMakeBuilder()
                    .setName(makeName)
                    .getCarMake());
        }
        return (CarMake) makeList.get(0);
    }
    
    public static List getModel(CarModel model) throws ServiceException {
        Criteria criteria = CriteriaFactory.findModel(model);
        try (DaoFactory repositoryFactory = new DaoFactory()) {
            Dao modelRepository = repositoryFactory.getModelDao();
            return modelRepository.read(criteria);
        }
        catch (DaoException | DbException ex) {
            LOG.error(MODEL_NAME_ERROR, ex);
            throw new ServiceException(MODEL_NAME_ERROR, ex);
        }
    }
    
    public static List createModel(CarModel model) throws ServiceException {
        try (DaoFactory repositoryFactory = new DaoFactory()) {
            Dao modelRepository = repositoryFactory.getModelDao();
            modelRepository.add(model);
            repositoryFactory.commit();
            return getModel(model);
        } catch (DaoException | DbException ex) {
            LOG.error(MODEL_CREATE_ERROR, ex);
            throw new ServiceException(MODEL_CREATE_ERROR, ex);
        }
    }
    
    public static List getMakeByName(String name) throws ServiceException {
        Criteria criteria = CriteriaFactory.getMakeByName(name);
        try (DaoFactory repositoryFactory = new DaoFactory()) {
            Dao makeRepository = repositoryFactory.getMakeDao();
            return makeRepository.read(criteria);
        }
        catch (DaoException | DbException ex) {
            LOG.error(MAKE_NAME_ERROR, ex);
            throw new ServiceException(MAKE_NAME_ERROR, ex);
        }
    }
    
    public static List createMake(CarMake make) throws ServiceException {
        try (DaoFactory repositoryFactory = new DaoFactory()) {
            Dao makeRepository = repositoryFactory.getMakeDao();
            makeRepository.add(make);
            repositoryFactory.commit();
            return getMakeByName(make.getName());
        } catch (DaoException | DbException ex) {
            LOG.error(MAKE_CREATE_ERROR, ex);
            throw new ServiceException(MAKE_CREATE_ERROR, ex);
        }
    }
    
    private static List getCarsByCriteria(Criteria criteria) throws ServiceException {
        try (DaoFactory repositoryFactory = new DaoFactory()) {
            Dao carRepository = repositoryFactory.getCarDao();
            return carRepository.read(criteria);
        } catch (DaoException | DbException ex) {
            LOG.error(CAR_CRITERIA_ERROR, ex);
            throw new ServiceException(CAR_CRITERIA_ERROR, ex);
        }
    }
}
