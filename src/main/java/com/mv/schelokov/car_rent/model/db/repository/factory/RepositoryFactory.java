package com.mv.schelokov.car_rent.model.db.repository.factory;

import com.mv.schelokov.car_rent.model.db.datasource.ConnectionPool;
import com.mv.schelokov.car_rent.model.db.datasource.exceptions.DataSourceException;
import com.mv.schelokov.car_rent.model.db.repository.CarPartRepository;
import com.mv.schelokov.car_rent.model.db.repository.CarRepository;
import com.mv.schelokov.car_rent.model.db.repository.DefectRepository;
import com.mv.schelokov.car_rent.model.db.repository.InvoiceLineRepository;
import com.mv.schelokov.car_rent.model.db.repository.InvoiceRepository;
import com.mv.schelokov.car_rent.model.db.repository.InvoiceTypeRepository;
import com.mv.schelokov.car_rent.model.db.repository.MakeRepository;
import com.mv.schelokov.car_rent.model.db.repository.ModelRepository;
import com.mv.schelokov.car_rent.model.db.repository.RentOrderRepository;
import com.mv.schelokov.car_rent.model.db.repository.RoleRepository;
import com.mv.schelokov.car_rent.model.db.repository.UserDataRepository;
import com.mv.schelokov.car_rent.model.db.repository.UserRepository;
import com.mv.schelokov.car_rent.model.db.repository.exceptions.RepositoryException;
import com.mv.schelokov.car_rent.model.db.repository.interfaces.Repository;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class RepositoryFactory implements AutoCloseable {
    
    private final ConnectionPool connectionPool;
    private final Connection connection;
    
    public RepositoryFactory() throws RepositoryException {
        try {
            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.getConnection();
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
        } catch(DataSourceException ex) {
            throw new RepositoryException("Failed to get connection pool "
                    + "instance", ex);
        } catch (SQLException ex) {
            throw new RepositoryException("Connection setup error", ex);
        }
    }
    
    public Repository getCarPartRepositroy() {
        return new CarPartRepository(connection);
    }
    
    public Repository getCarRepository() {
        return new CarRepository(connection);
    }
    
    public Repository getDefectRepository() {
        return new DefectRepository(connection);
    }
    
    public Repository getInvoiceLineRepository() {
        return new InvoiceLineRepository(connection);
    }
    
    public Repository getInvoiceRepository() {
        return new InvoiceRepository(connection);
    }
    
    public Repository getInvoiceTypeRepository() {
        return new InvoiceTypeRepository(connection);
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
    
    @Override
    public void close() throws RepositoryException {
        try {
            connection.commit();
            connectionPool.freeConnection(connection);
        } catch (SQLException ex) {
            throw new RepositoryException("Failed to close connection", ex);
        }
    }
    
}
