package com.mv.schelokov.carent.model.db.dao.factories;

import com.mv.schelokov.carent.model.db.datasource.ConnectionPool;
import com.mv.schelokov.carent.model.db.datasource.exceptions.DataSourceException;
import com.mv.schelokov.carent.model.db.dao.CarDao;
import com.mv.schelokov.carent.model.db.dao.InvoiceLineDao;
import com.mv.schelokov.carent.model.db.dao.InvoiceDao;
import com.mv.schelokov.carent.model.db.dao.CarMakeDao;
import com.mv.schelokov.carent.model.db.dao.CarModelDao;
import com.mv.schelokov.carent.model.db.dao.RejectionReasonDao;
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
    private static final String ERROR_CLOSE_CONNECTION = "Failed to close the"
            + " connection";
    private static final String ERROR_COMMIT = "Failed to commit to database";
    private static final String ERROR_GET_INSTANCE = "Failed to get connection"
            + " pool instance";

    private final ConnectionPool connectionPool;
    private final Connection connection;
    
    public DaoFactory() throws DaoException {
        try {
            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.getConnection();
        } catch(DataSourceException ex) {
            throw new DaoException(ERROR_GET_INSTANCE, ex);
        }
    }
    
    public Dao createCarDao() {
        return new CarDao(connection);
    }
    
    public Dao createInvoiceLineDao() {
        return new InvoiceLineDao(connection);
    }
    
    public Dao createInvoiceDao() {
        return new InvoiceDao(connection);
    }

    public Dao createCarMakeDao() {
        return new CarMakeDao(connection);
    }
    
    public Dao createCarModelDao() {
        return new CarModelDao(connection);
    }
    
    public Dao createRentOrderDao() {
        return new RentOrderDao(connection);
    }
    
    public Dao createRoleDao() {
        return new RoleDao(connection);
    }
    
    public Dao createUserDataDao() {
        return new UserDataDao(connection);
    }
    
    public Dao createUserDao() {
        return new UserDao(connection);
    }
    
    public Dao createRejectionReasonDao() {
        return new RejectionReasonDao(connection);
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
            connectionPool.releaseConnection(connection);
        } catch (DataSourceException ex) {
            LOG.error(ERROR_CLOSE_CONNECTION, ex);
            throw new DaoException(ERROR_CLOSE_CONNECTION, ex);
        }
    }
}
