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
import com.mv.schelokov.car_rent.model.entities.builders.MakeBuilder;
import com.mv.schelokov.car_rent.model.entities.builders.ModelBuilder;
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
    
    public static Model getModelByNameOrCreate(String modelName, String makeName)
            throws ServiceException {
        if (modelName == null || modelName.isEmpty()) {
            return new ModelBuilder().setId(0).getModel();
        }
        Model model = new ModelBuilder()
                .setName(modelName)
                .setMake(getMakeByNameOrCreate(makeName))
                .getModel();
        List modelList = getModel(model);
        if (modelList.isEmpty()) {
            modelList = createModel(model);
        }
        return (Model) modelList.get(0);
    }

    private static Make getMakeByNameOrCreate(String makeName)
            throws ServiceException {
        if (makeName == null || makeName.isEmpty()) {
            return new MakeBuilder().setId(0).getMake();
        }
        List makeList = getMakeByName(makeName);
        if (makeList.isEmpty()) {
            makeList = createMake(new MakeBuilder()
                    .setName(makeName)
                    .getMake());
        }
        return (Make) makeList.get(0);
    }
    
    public static List getModel(Model model) throws ServiceException {
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
    
    public static List createModel(Model model) throws ServiceException {
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
    
    public static List getMakeByName(String name) throws ServiceException {
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
    
    public static List createMake(Make make) throws ServiceException {
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
    
    private static List getCarsByCriteria(Criteria criteria) throws ServiceException {
        try (RepositoryFactory repositoryFactory = new RepositoryFactory()) {
            Repository carRepository = repositoryFactory.getCarRepository();
            return carRepository.read(criteria);
        } catch (RepositoryException | DbException ex) {
            LOG.error(CAR_CRITERIA_ERROR, ex);
            throw new ServiceException(CAR_CRITERIA_ERROR, ex);
        }
    }
}
