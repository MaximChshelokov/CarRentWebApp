package com.mv.schelokov.car_rent.model.services;

import com.mv.schelokov.car_rent.model.db.dao.exceptions.DaoException;
import com.mv.schelokov.car_rent.model.db.dao.exceptions.DbException;
import com.mv.schelokov.car_rent.model.db.dao.factories.CriteriaFactory;
import com.mv.schelokov.car_rent.model.db.dao.factories.DaoFactory;
import com.mv.schelokov.car_rent.model.db.dao.interfaces.Criteria;
import com.mv.schelokov.car_rent.model.db.dao.interfaces.Dao;
import com.mv.schelokov.car_rent.model.entity.CarModel;
import com.mv.schelokov.car_rent.model.entity.builders.CarModelBuilder;
import com.mv.schelokov.car_rent.model.services.exceptions.ServiceException;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class CarModelService {
    
    private static final Logger LOG = Logger.getLogger(CarService.class);
    private static final String MODEL_NAME_ERROR = "Failed to get model by "
            + "name from the DAO";
    private static final String MODEL_CREATE_ERROR = "Failed to create new model";
    private static final String INSTANCE_ERROR = "Failed to get instance";
    private static volatile CarModelService instance;

    public static CarModelService getInstance() throws ServiceException {
        CarModelService localInstance = instance;
        if (localInstance == null) {
            synchronized (CarModelService.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new CarModelService();
                }
            }
        }
        if (localInstance == null) {
            LOG.error(INSTANCE_ERROR);
            throw new ServiceException(INSTANCE_ERROR);
        }
        return localInstance;
    }
    
    public CarModel getModelByNameOrCreate(String modelName, String makeName)
            throws ServiceException {
        if (modelName == null || modelName.isEmpty()) {
            return new CarModelBuilder().setId(0).getCarModel();
        }
        
        CarMakeService carMakeService = CarMakeService.getInstance();
        
        CarModel model = new CarModelBuilder()
                .setName(modelName)
                .setCarMake(carMakeService.getMakeByNameOrCreate(makeName))
                .getCarModel();
        List modelList = getModel(model);
        if (modelList.isEmpty()) {
            modelList = createModel(model);
        }
        return (CarModel) modelList.get(0);
    }
    
    public List getModel(CarModel model) throws ServiceException {
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
    
    public List createModel(CarModel model) throws ServiceException {
        try (DaoFactory repositoryFactory = new DaoFactory()) {
            Dao modelRepository = repositoryFactory.getModelDao();
            modelRepository.add(model);
            repositoryFactory.commit();
            return getModel(model);
        }
        catch (DaoException | DbException ex) {
            LOG.error(MODEL_CREATE_ERROR, ex);
            throw new ServiceException(MODEL_CREATE_ERROR, ex);
        }
    }
    
    private CarModelService() {}
    
}
