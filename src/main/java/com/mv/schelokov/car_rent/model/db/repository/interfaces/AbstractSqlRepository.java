package com.mv.schelokov.car_rent.model.db.repository.interfaces;

import com.mv.schelokov.car_rent.model.db.repository.exceptions.CriteriaMismatchException;
import com.mv.schelokov.car_rent.model.db.repository.exceptions.DbException;
import com.mv.schelokov.car_rent.model.entities.interfaces.Entity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 * @param <T> - the entity class
 */
public abstract class AbstractSqlRepository <T extends Entity> implements Repository <T> {
    
    private Connection connection;

    @Override
    public List<T> read(Criteria criteria) throws DbException {
        if (checkCriteriaInstance(criteria, false)) {
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
            } catch (SQLException ex) {
                throw new DbException("Error while creating PreparedStatement", ex); // Change to constant
            }
            return result;
        }
        return new ArrayList<>();
    }

    @Override
    public boolean remove(Criteria criteria) throws DbException {
        if (checkCriteriaInstance(criteria, true)) {
            SqlCriteria sqlCriteria = (SqlCriteria) criteria;
            try (PreparedStatement ps = connection.prepareStatement(
                    sqlCriteria.toSqlQuery())) {
                sqlCriteria.setStatement(ps);
                return ps.executeUpdate() > 0;                
            } catch (SQLException ex) {
                throw new DbException("Error while creating PreparedStatement", ex); // Change to constant
            }
        }
        return false;
    }

    /**
     *
     * @param item
     * @return
     * @throws DbException
     */
    @Override
    public boolean remove(T item) throws DbException {
        try (PreparedStatement ps = connection.prepareStatement(getRemoveQuery())) {
            ps.setInt(1, item.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
                throw new DbException("Error while creating PreparedStatement", ex); // Change to constant
        }
    }

    @Override
    public boolean update(T item) throws DbException {
        try (PreparedStatement ps = connection.prepareStatement(getUpdateQuery())) {
            setStatement(ps, item, true);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new DbException("Error while creating PreparedStatement", ex); // Change to constant
        }
    }
    
    @Override
    public boolean add(T item) throws DbException {
        try (PreparedStatement ps = connection.prepareStatement(getCreateQuery())) {
            setStatement(ps, item, false);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new DbException("Error while creating PreparedStatement", ex); // Change to constant
        }
    }
    
    protected abstract String getCreateQuery();
    
    protected abstract String getRemoveQuery();
    
    protected abstract String getUpdateQuery();
    
    protected abstract T createItem(ResultSet rs) throws SQLException;
    
    protected abstract void setStatement(PreparedStatement ps, T item,
            boolean isUpdateStatement) throws SQLException;
    
    protected abstract boolean checkCriteriaInstance(Criteria criteria, 
            boolean isDeleteCriteria) throws CriteriaMismatchException;
    
}
