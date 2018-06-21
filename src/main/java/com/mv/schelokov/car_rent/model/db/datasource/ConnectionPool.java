package com.mv.schelokov.car_rent.model.db.datasource;

import com.mv.schelokov.car_rent.model.db.datasource.exceptions.DataSourceException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class ConnectionPool {
    
    private static final String DB_PROPERTY_NAME = "db_params.properties";
    private Queue<Connection> freeConnections;
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
            params = new ParametersLoader(this.getClass()
                    .getResourceAsStream(DB_PROPERTY_NAME));
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
            throw new DataSourceException("Failed to create connection", ex);
        } catch(InstantiationException | ClassNotFoundException 
                | IllegalAccessException ex) {
            throw new DataSourceException("Failed to load MySQL JDBC driver", ex);
        }
    }
    
    public Connection getConnection() {
        return freeConnections.poll();
    }
    
    public void freeConnection(Connection connection) {
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
