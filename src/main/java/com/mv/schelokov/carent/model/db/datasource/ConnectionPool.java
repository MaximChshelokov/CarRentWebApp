package com.mv.schelokov.carent.model.db.datasource;

import com.mv.schelokov.carent.model.db.datasource.exceptions.DataSourceException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
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
    private static final String INSTANCE_ERROR = "Connection pool instance"
            + " doesn't exist";

    private BlockingQueue<Connection> freeConnections;
    private ParametersLoader params;
    
    private static volatile ConnectionPool instance;
    
    public static ConnectionPool getInstance() throws DataSourceException {
        ConnectionPool localInstance = instance;
        if (localInstance == null) {
            synchronized (ConnectionPool.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new ConnectionPool();
                }
            }
        }
        if (localInstance == null) {
            LOG.error(INSTANCE_ERROR);
            throw new DataSourceException(INSTANCE_ERROR);
        }
        return localInstance;
    }
    
    public Connection getConnection() throws DataSourceException {
        try {
            Connection result = freeConnections.take();
            result.setAutoCommit(false);
            result.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            return result;
        }
        catch (InterruptedException ex) {
            LOG.error(ERROR_TAKE_CONNECTION, ex);
            throw new DataSourceException(ERROR_TAKE_CONNECTION, ex);
        }
        catch (SQLException ex) {
            LOG.error(ERROR_SETUP_CONNECTION, ex);
            throw new DataSourceException(ERROR_SETUP_CONNECTION, ex);
        }
    }

    public void releaseConnection(Connection connection) throws DataSourceException {
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
    
    private ConnectionPool() {
        try {
            params = new ParametersLoader(this.getClass().getClassLoader()
                    .getResourceAsStream(DB_PROPERTIES_NAME));
            freeConnections = new ArrayBlockingQueue<>(params.getPoolSize());
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
}
