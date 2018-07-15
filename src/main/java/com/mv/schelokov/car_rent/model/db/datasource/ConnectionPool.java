package com.mv.schelokov.car_rent.model.db.datasource;

import com.mv.schelokov.car_rent.model.db.datasource.exceptions.DataSourceException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.log4j.Logger;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class ConnectionPool {
    
    private static final Logger LOG = Logger.getLogger(ConnectionPool.class);
    private static final String DB_PROPERTIES_NAME = "db_params.properties";
    private static final String DRIVER_ERROR = "Failed to load MySQL JDBC driver";
    private static final String CREATE_CONNECTIOIN_ERROR = "Failed to create a"
            + " connection";
    private static final String ERROR_SETUP_CONNECTION = "Can't set up connection";
    private static final String ERROR_TAKE_CONNECTION = "Unable to take a "
            + "connection from the pool";
    private static final String ERROR_COMMIT = "Failed to commit";
    private static final String ERROR_CLOSE = "Failed to close connections";

    private BlockingQueue<Connection> freeConnections;
    private Set<Connection> allConnections;
    private ParametersLoader params;
    
    private static class SingletonHolder {
        private static final ConnectionPool INSTANCE = new ConnectionPool();
    }
    
    public static ConnectionPool getInstance() throws DataSourceException {
        if (SingletonHolder.INSTANCE == null)
            throw new DataSourceException("Connection pool instance doesn't "
                    + "exist");
        return SingletonHolder.INSTANCE;
    }
    
    private ConnectionPool() {
        try {
            params = new ParametersLoader(this.getClass().getClassLoader()
                    .getResourceAsStream(DB_PROPERTIES_NAME));
            freeConnections = new ArrayBlockingQueue<>(params.getPoolSize());
            allConnections = Collections.newSetFromMap(
                    new ConcurrentHashMap<Connection, Boolean>());
            establishPool();
        }
        catch (DataSourceException ex) {
            LOG.error("Failed to initialize connection pool", ex);
        }

    }
    
    private void establishPool() throws DataSourceException {
        try {
            Class.forName(params.getDriver()).newInstance();
            for (int i = 0; i < params.getPoolSize(); i++) {
                Connection connection = DriverManager.getConnection(params.getUrl(),
                        params.getLogin(), params.getPassword());
                freeConnections.offer(connection);
                allConnections.add(connection);
            }
        } catch(SQLException ex) {
            LOG.error(CREATE_CONNECTIOIN_ERROR, ex);
            throw new DataSourceException(CREATE_CONNECTIOIN_ERROR, ex);
        } catch(InstantiationException | ClassNotFoundException 
                | IllegalAccessException ex) {
            LOG.error(DRIVER_ERROR, ex);
            throw new DataSourceException(DRIVER_ERROR, ex);
        }
    }

    
    public Connection getConnection() throws DataSourceException {
        try {
            Connection result = freeConnections.take();
            result.setAutoCommit(false);
            result.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            return result;
        } catch (InterruptedException ex) {
            LOG.error(ERROR_TAKE_CONNECTION, ex);
            throw new DataSourceException(ERROR_TAKE_CONNECTION, ex);
        }
        catch (SQLException ex) {
            LOG.error(ERROR_SETUP_CONNECTION, ex);
            throw new DataSourceException(ERROR_SETUP_CONNECTION, ex);
        }
    }

    
    public void freeConnection(Connection connection) throws DataSourceException {
        commit(connection);
        freeConnections.offer(connection);
    }
    
    public void commit(Connection connection) throws DataSourceException {
        try {
            connection.commit();
        }
        catch (SQLException ex) {
            LOG.error(ERROR_COMMIT, ex);
            throw new DataSourceException(ERROR_COMMIT, ex);
        }
    }
    
    public void closeAll() throws DataSourceException {
        try {
        for(Connection connection : allConnections)
            if (!connection.isClosed()) {
                connection.commit();
                connection.close();
            }
        } catch(SQLException ex) {
            LOG.error(ERROR_CLOSE, ex);
            throw new DataSourceException(ERROR_CLOSE);
        }
    }
}
