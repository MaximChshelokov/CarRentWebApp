package com.mv.schelokov.car_rent.model.services;

import com.mv.schelokov.car_rent.model.db.dao.exceptions.DaoException;
import com.mv.schelokov.car_rent.model.db.dao.exceptions.DbException;
import com.mv.schelokov.car_rent.model.db.dao.factories.CriteriaFactory;
import com.mv.schelokov.car_rent.model.db.dao.factories.DaoFactory;
import com.mv.schelokov.car_rent.model.db.dao.interfaces.Criteria;
import com.mv.schelokov.car_rent.model.db.dao.interfaces.Dao;
import com.mv.schelokov.car_rent.model.entity.CarMake;
import com.mv.schelokov.car_rent.model.entity.builders.CarMakeBuilder;
import com.mv.schelokov.car_rent.model.services.exceptions.ServiceException;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class CarMakeService {
    private static final Logger LOG = Logger.getLogger(CarMakeService.class);
    private static final String MAKE_NAME_ERROR = "Failed to get make by "
            + "name from the DAO";
    private static final String MAKE_CREATE_ERROR = "Failed to create new make";
    private static final String INSTANCE_ERROR = "Failed to get instance";
    private static volatile CarMakeService instance;

    public static CarMakeService getInstance() throws ServiceException {
        CarMakeService localInstance = instance;
        if (localInstance == null) {
            synchronized (CarMakeService.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new CarMakeService();
                }
            }
        }
        if (localInstance == null) {
            LOG.error(INSTANCE_ERROR);
            throw new ServiceException(INSTANCE_ERROR);
        }
        return localInstance;
    }
    
    public CarMake getMakeByNameOrCreate(String makeName)
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
    
    public List getMakeByName(String name) throws ServiceException {
        Criteria criteria = CriteriaFactory.getMakeByName(name);
        try (DaoFactory daoFactory = new DaoFactory()) {
            Dao makeDao = daoFactory.getMakeDao();
            return makeDao.read(criteria);
        }
        catch (DaoException | DbException ex) {
            LOG.error(MAKE_NAME_ERROR, ex);
            throw new ServiceException(MAKE_NAME_ERROR, ex);
        }
    }
    
    public List createMake(CarMake make) throws ServiceException {
        try (DaoFactory daoFactory = new DaoFactory()) {
            Dao makeDao = daoFactory.getMakeDao();
            makeDao.add(make);
            daoFactory.commit();
            return getMakeByName(make.getName());
        } catch (DaoException | DbException ex) {
            LOG.error(MAKE_CREATE_ERROR, ex);
            throw new ServiceException(MAKE_CREATE_ERROR, ex);
        }
    }
    
    private CarMakeService() {}
}
