package com.mv.schelokov.carent.model.db.dao.factories;

import com.mv.schelokov.carent.model.db.datasource.ConnectionPool;
import com.mv.schelokov.carent.model.db.datasource.exceptions.DataSourceException;
import com.mv.schelokov.carent.model.db.dao.CarDao;
import com.mv.schelokov.carent.model.db.dao.InvoiceLineDao;
import com.mv.schelokov.carent.model.db.dao.InvoiceDao;
import com.mv.schelokov.carent.model.db.dao.MakeDao;
import com.mv.schelokov.carent.model.db.dao.ModelDao;
import com.mv.schelokov.carent.model.db.dao.RentOrderDao;
import com.mv.schelokov.carent.model.db.dao.RoleDao;
import com.mv.schelokov.carent.model.db.dao.UserDataDao;
import com.mv.schelokov.carent.model.db.dao.UserDao;
import com.mv.schelokov.carent.model.db.dao.exceptions.DaoException;
import java.sql.Connection;
import org.apache.log4j.Logger;
import com.mv.schelokov.carent.model.db.dao.interfaces.Dao;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class DaoFactory implements AutoCloseable {
    
    private static final Logger LOG = Logger.getLogger(DaoFactory.class);
    private static final String ERROR_CLOSE_CONNECTION = "Failed to close the connection";
    private static final String ERROR_COMMIT = "Failed to commit to database";
    private final ConnectionPool connectionPool;
    private final Connection connection;
    
    public DaoFactory() throws DaoException {
        try {
            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.getConnection();
        } catch(DataSourceException ex) {
            throw new DaoException("Failed to get connection pool "
                    + "instance", ex);
        }
    }
    
    public Dao getCarDao() {
        return new CarDao(connection);
    }
    
    public Dao getInvoiceLineDao() {
        return new InvoiceLineDao(connection);
    }
    
    public Dao getInvoiceDao() {
        return new InvoiceDao(connection);
    }

    public Dao getMakeDao() {
        return new MakeDao(connection);
    }
    
    public Dao getModelDao() {
        return new ModelDao(connection);
    }
    
    public Dao getRentOrderDao() {
        return new RentOrderDao(connection);
    }
    
    public Dao getRoleDao() {
        return new RoleDao(connection);
    }
    
    public Dao getUserDataDao() {
        return new UserDataDao(connection);
    }
    
    public Dao getUserDao() {
        return new UserDao(connection);
    }
    
    public void commit() throws DaoException {
        try {
            connectionPool.commit(connection);
        } catch (DataSourceException ex) {
            LOG.error(ERROR_COMMIT, ex);
            throw new DaoException(ERROR_COMMIT, ex);
        }
    }
    
    @Override
    public void close() throws DaoException {
        try {
            connectionPool.freeConnection(connection);
        } catch (DataSourceException ex) {
            LOG.error(ERROR_CLOSE_CONNECTION, ex);
            throw new DaoException(ERROR_CLOSE_CONNECTION, ex);
        }
    }
}
