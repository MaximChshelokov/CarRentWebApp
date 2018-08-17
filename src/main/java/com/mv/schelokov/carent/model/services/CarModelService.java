package com.mv.schelokov.carent.model.services;

import com.mv.schelokov.carent.model.db.dao.exceptions.DaoException;
import com.mv.schelokov.carent.model.db.dao.exceptions.DbException;
import com.mv.schelokov.carent.model.db.dao.factories.CriteriaFactory;
import com.mv.schelokov.carent.model.db.dao.factories.DaoFactory;
import com.mv.schelokov.carent.model.db.dao.interfaces.Criteria;
import com.mv.schelokov.carent.model.db.dao.interfaces.Dao;
import com.mv.schelokov.carent.model.entity.CarModel;
import com.mv.schelokov.carent.model.entity.builders.CarModelBuilder;
import com.mv.schelokov.carent.model.services.exceptions.ServiceException;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class CarModelService {
    
    private static final Logger LOG = Logger.getLogger(CarModelService.class);
    private static final String MODEL_NAME_ERROR = "Failed to get model by "
            + "name from the DAO";
    private static final String MODEL_CREATE_ERROR =
            "Failed to create new model";
    
    public CarModel getModelByNameOrCreate(String modelName, String makeName)
            throws ServiceException {
        if (modelName == null || modelName.isEmpty()) {
            return new CarModelBuilder().withId(0).getCarModel();
        }
        
        CarMakeService carMakeService = new CarMakeService();
        
        CarModel model = new CarModelBuilder()
                .withName(modelName)
                .withCarMake(carMakeService.getMakeByNameOrCreate(makeName))
                .getCarModel();
        List modelList = getModel(model);
        if (modelList.isEmpty()) {
            modelList = createModel(model);
        }
        return (CarModel) modelList.get(0);
    }
    
    public List getModel(CarModel model) throws ServiceException {
        Criteria criteria = new CriteriaFactory().createFindModelCriteria(model);
        try (DaoFactory daoFactory = new DaoFactory()) {
            Dao modelDao = daoFactory.createCarModelDao();
            return modelDao.read(criteria);
        }
        catch (DaoException | DbException ex) {
            LOG.error(MODEL_NAME_ERROR, ex);
            throw new ServiceException(MODEL_NAME_ERROR, ex);
        }
    }
    
    public List createModel(CarModel model) throws ServiceException {
        try (DaoFactory daoFactory = new DaoFactory()) {
            Dao modelDao = daoFactory.createCarModelDao();
            modelDao.add(model);
            daoFactory.commit();
            return getModel(model);
        }
        catch (DaoException | DbException ex) {
            LOG.error(MODEL_CREATE_ERROR, ex);
            throw new ServiceException(MODEL_CREATE_ERROR, ex);
        }
    }
    
}
