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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class ConnectionPool {
    
    private static final String DB_PROPERTIES_NAME = "db_params.properties";
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
            ex.printStackTrace();
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
            throw new DataSourceException("Failed to create a connection", ex);
        } catch(InstantiationException | ClassNotFoundException 
                | IllegalAccessException ex) {
            throw new DataSourceException("Failed to load MySQL JDBC driver", ex);
        }
    }
    
    public Connection getConnection() throws DataSourceException {
        try {
            Connection result = freeConnections.take();
            result.setAutoCommit(false);
            result.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            return result;
        } catch (InterruptedException ex) {
            throw new DataSourceException("Unable to take a connection from the"
                    + " pool", ex);
        }
        catch (SQLException ex) {
            throw new DataSourceException("Can't set up connection", ex);
        }
    }
    
    public void freeConnection(Connection connection) throws DataSourceException {
        try {
            connection.commit();
        } catch (SQLException ex) {
            throw new DataSourceException("Failed to commit", ex);
        }
        freeConnections.offer(connection);
    }
    
    public void closeAll() throws DataSourceException {
        try {
        for(Connection connection : allConnections)
            if (!connection.isClosed()) {
                connection.commit();
                connection.close();
            }
        } catch(SQLException ex) {
            throw new DataSourceException("Failed to close connections");
        }
    }
}
