package com.mv.schelokov.car_rent.model.db.repository.factories;

import com.mv.schelokov.car_rent.model.db.datasource.ConnectionPool;
import com.mv.schelokov.car_rent.model.db.datasource.exceptions.DataSourceException;
import com.mv.schelokov.car_rent.model.db.repository.CarRepository;
import com.mv.schelokov.car_rent.model.db.repository.InvoiceLineRepository;
import com.mv.schelokov.car_rent.model.db.repository.InvoiceRepository;
import com.mv.schelokov.car_rent.model.db.repository.MakeRepository;
import com.mv.schelokov.car_rent.model.db.repository.ModelRepository;
import com.mv.schelokov.car_rent.model.db.repository.RentOrderRepository;
import com.mv.schelokov.car_rent.model.db.repository.RoleRepository;
import com.mv.schelokov.car_rent.model.db.repository.UserDataRepository;
import com.mv.schelokov.car_rent.model.db.repository.UserRepository;
import com.mv.schelokov.car_rent.model.db.repository.exceptions.RepositoryException;
import com.mv.schelokov.car_rent.model.db.repository.interfaces.Repository;
import java.sql.Connection;
import org.apache.log4j.Logger;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class RepositoryFactory implements AutoCloseable {
    
    private static final Logger LOG = Logger.getLogger(RepositoryFactory.class);
    private static final String ERROR_CLOSE_CONNECTION = "Failed to close the connection";
    private static final String ERROR_COMMIT = "Failed to commit to database";
    private final ConnectionPool connectionPool;
    private final Connection connection;
    
    public RepositoryFactory() throws RepositoryException {
        try {
            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.getConnection();
        } catch(DataSourceException ex) {
            throw new RepositoryException("Failed to get connection pool "
                    + "instance", ex);
        }
    }
    
    public Repository getCarRepository() {
        return new CarRepository(connection);
    }
    
    public Repository getInvoiceLineRepository() {
        return new InvoiceLineRepository(connection);
    }
    
    public Repository getInvoiceRepository() {
        return new InvoiceRepository(connection);
    }

    public Repository getMakeRepository() {
        return new MakeRepository(connection);
    }
    
    public Repository getModelRepository() {
        return new ModelRepository(connection);
    }
    
    public Repository getRentOrderRepository() {
        return new RentOrderRepository(connection);
    }
    
    public Repository getRoleRepository() {
        return new RoleRepository(connection);
    }
    
    public Repository getUserDataRepository() {
        return new UserDataRepository(connection);
    }
    
    public Repository getUserRepository() {
        return new UserRepository(connection);
    }
    
    public void commit() throws RepositoryException {
        try {
            connectionPool.commit(connection);
        } catch (DataSourceException ex) {
            LOG.error(ERROR_COMMIT, ex);
            throw new RepositoryException(ERROR_COMMIT, ex);
        }
    }
    
    @Override
    public void close() throws RepositoryException {
        try {
            connectionPool.freeConnection(connection);
        } catch (DataSourceException ex) {
            LOG.error(ERROR_CLOSE_CONNECTION, ex);
            throw new RepositoryException(ERROR_CLOSE_CONNECTION, ex);
        }
    }
}
