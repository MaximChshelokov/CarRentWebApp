package com.mv.schelokov.car_rent.model.db.repository;

import com.mv.schelokov.car_rent.model.db.datasource.ConnectionPool;
import com.mv.schelokov.car_rent.model.db.datasource.exceptions.DataSourceException;
import com.mv.schelokov.car_rent.model.db.repository.exceptions.RepositoryException;
import com.mv.schelokov.car_rent.model.db.repository.interfaces.Repository;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
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
    
    public <T extends Repository> T getRepository(Class<T> repositoryClass) 
            throws RepositoryException {
        T repository;
        try {
            Constructor<T> constructor = repositoryClass.getConstructor(Connection.class);
            repository = constructor.newInstance(connection);
        }
        catch (NoSuchMethodException | SecurityException | 
                InstantiationException | IllegalAccessException | 
                IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(RepositoryFactory.class.getName()).log(Level.SEVERE, null, ex);
            throw new RepositoryException("Failed to create a repository "
                    + "object", ex);
        }
        return repository;
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
