package com.mv.schelokov.carent.model.db.dao.interfaces;

import com.mv.schelokov.carent.model.db.dao.exceptions.CriteriaMismatchException;
import com.mv.schelokov.carent.model.db.dao.exceptions.DbException;
import com.mv.schelokov.carent.model.entity.interfaces.Entity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 * @param <T> - the entity class
 */
public abstract class AbstractSqlDao <T extends Entity> 
        implements Dao <T> {
    
    private static final Logger LOG = 
            Logger.getLogger(AbstractSqlDao.class);
    private static final String RESULTSER_ERROR = "ResultSet error occurred";
    private static final String PREPAREDSTAT_ERROR = 
            "PreparedStatement error occurred";
    private static final String CRITERIA_MISTMATCH = 
            "Criteria class mistmatches";
    private static final String ROLLBACK_ERROR = "Failed to rollback";
    
    private final Connection connection;
    
    public AbstractSqlDao(Connection connection) {
        this.connection = connection;
    }

    /**
     * Reads records from a database table that match up to the criteria.
     * 
     * @param criteria a special object implementing the criteria interface.
     * @return a list of objects (entities) that match up th the criteria.
     * @throws DbException
     */
    @Override
    public List<T> read(Criteria criteria) throws DbException {
        
        if (!isReadCriteriaInstance(criteria)) {
            LOG.error(CRITERIA_MISTMATCH);
            throw new CriteriaMismatchException(CRITERIA_MISTMATCH);
        }
        
        List<T> result = new ArrayList<>();
        SqlCriteria sqlCriteria = (SqlCriteria) criteria;
        try (PreparedStatement ps = connection.prepareStatement(
                sqlCriteria.toSqlQuery())) {
            sqlCriteria.setStatement(ps);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    result.add(createItem(rs));
                }
            }
            catch (SQLException ex) {
                LOG.error(RESULTSER_ERROR);
                throw new DbException(RESULTSER_ERROR, ex);
            }
        }
        catch (SQLException ex) {
            LOG.error(PREPAREDSTAT_ERROR);
            throw new DbException(PREPAREDSTAT_ERROR, ex);
        }
        return result;
    }
    
    /**
     * Removes records from a database table that match up to the criteria.
     *
     * @param criteria a special object implementing the criteria interface.
     * @return true if at least one record was removed.
     * @throws DbException
     */
    @Override
    public boolean remove(Criteria criteria) throws DbException {
        
        if (!isDeleteCriteriaInstance(criteria)) {
            LOG.error(CRITERIA_MISTMATCH);
            throw new CriteriaMismatchException(CRITERIA_MISTMATCH);
        }
        
        SqlCriteria sqlCriteria = (SqlCriteria) criteria;
        try (PreparedStatement ps = connection.prepareStatement(
                sqlCriteria.toSqlQuery())) {
            sqlCriteria.setStatement(ps);
            return ps.executeUpdate() > 0;
        }
        catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException exc) {
                LOG.error(ROLLBACK_ERROR);
                throw new DbException(ROLLBACK_ERROR, exc);             
            }
            LOG.error(PREPAREDSTAT_ERROR);
            throw new DbException(PREPAREDSTAT_ERROR, ex);
        }
    }

    /**
     *  Removes a specified item from a database table.
     * 
     * @param item - some entity to be removed.
     * @return true if entity was successfully deleted.
     * @throws DbException
     */
    @Override
    public boolean remove(T item) throws DbException {
        try (PreparedStatement ps = connection
                .prepareStatement(getRemoveQuery())) {
            ps.setInt(1, item.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            try {
                connection.rollback();
            }
            catch (SQLException exc) {
                LOG.error(ROLLBACK_ERROR);
                throw new DbException(ROLLBACK_ERROR, exc);
            }
            LOG.error(PREPAREDSTAT_ERROR);
            throw new DbException(PREPAREDSTAT_ERROR, ex);
        }
    }

    /**
     *  Updates a specified item in a database table.
     * @param item - some entity to be updated.
     * @return true if entity was successfully updated.
     * @throws DbException
     */
    @Override
    public boolean update(T item) throws DbException {
        try (PreparedStatement ps = connection
                .prepareStatement(getUpdateQuery())) {
            setUpdateStatement(ps, item);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            try {
                connection.rollback();
            }
            catch (SQLException exc) {
                LOG.error(ROLLBACK_ERROR);
                throw new DbException(ROLLBACK_ERROR, exc);
            }
            LOG.error(PREPAREDSTAT_ERROR);
            throw new DbException(PREPAREDSTAT_ERROR, ex);
        }
    }
    
    /**
     *  Creates a new item in a database table.
     * @param item - some entity to be added.
     * @return true, if the entity was successfully created.
     * @throws DbException
     */
    @Override
    public boolean add(T item) throws DbException {
        try (PreparedStatement ps = connection
                .prepareStatement(getCreateQuery())) {
            setInsertStatement(ps, item);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            try {
                connection.rollback();
            }
            catch (SQLException exc) {
                LOG.error(ROLLBACK_ERROR);
                throw new DbException(ROLLBACK_ERROR, exc);
            }
            LOG.error(PREPAREDSTAT_ERROR);
            throw new DbException(PREPAREDSTAT_ERROR, ex);
        }
    }
    
    /**
     *
     * @return the SQL query to create item in a database table.
     */
    protected abstract String getCreateQuery();
    
    /**
     *
     * @return the SQL query to remove item from a database table.
     */
    protected abstract String getRemoveQuery();
    
    /**
     *
     * @return the SQL query to update item in a database table.
     */
    protected abstract String getUpdateQuery();
    
    /**
     *  Creates an entity object of type T with a data from ResultSet object.
     * @param rs a source ResultSet object.
     * @return an entity object of type T.
     * @throws SQLException
     */
    protected abstract T createItem(ResultSet rs) throws SQLException;
    
    /**
     * Puts the necessary data from entity to PreparedStatement object for
     * insert operation.
     *
     * @param ps a target PreparedStatement object.
     * @param item a source entity object.
     * @throws SQLException
     */
    protected abstract void setInsertStatement(PreparedStatement ps, T item)
            throws SQLException;
    
    /**
     *  Puts the necessary data from entity to PreparedStatement object for
     * update operation.
     * 
     * @param ps a target PreparedStatement object.
     * @param item a source entity object.
     * @throws SQLException
     */
    protected abstract void setUpdateStatement(PreparedStatement ps, T item)
            throws SQLException;
    
    /**
     * 
     * @param criteria an object of class, implementing Criteria interface.
     * @return true, if criteria instance matches.
     */
    protected abstract boolean isReadCriteriaInstance(Criteria criteria);
    
    /**
     *
     * @param criteria an object of class, implementing Criteria interface.
     * @return true, if criteria instance matches.
     */
    protected abstract boolean isDeleteCriteriaInstance(Criteria criteria);
}
