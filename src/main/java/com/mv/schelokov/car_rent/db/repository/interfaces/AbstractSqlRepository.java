package com.mv.schelokov.car_rent.db.repository.interfaces;

import com.mv.schelokov.car_rent.db.repository.exceptions.CriteriaMismatchException;
import com.mv.schelokov.car_rent.db.repository.exceptions.DbException;
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
public abstract class AbstractSqlRepository <T> implements Repository <T> {
    
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
            return executeUpdateQuery(sqlCriteria.toSqlQuery(), null);
        }
        return false;
    }

    @Override
    public boolean remove(T item) throws DbException {
        return executeUpdateQuery(getRemoveQuery(), item);
    }

    @Override
    public boolean update(T item) throws DbException {
        return executeUpdateQuery(getUpdateQuery(), item);
    }
    
    private boolean executeUpdateQuery(String query, T item) throws DbException {
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            if (item != null) setStatement(ps, item);
            if (ps.executeUpdate() > 0) return true;
        } catch (SQLException ex) {
            throw new DbException("Error while creating PreparedStatement", ex); // Change to constant
        }
        return false;
    }

    @Override
    public boolean add(T item) throws DbException {
        return executeUpdateQuery(getCreateQuery(), item);
    }
    
    protected abstract String getCreateQuery();
    
    protected abstract String getRemoveQuery();
    
    protected abstract String getUpdateQuery();
    
    protected abstract T createItem(ResultSet rs);
    
    protected abstract void setStatement(PreparedStatement ps, T item);
    
    protected abstract boolean checkCriteriaInstance(Criteria criteria, 
            boolean isDeleteCriteria) throws CriteriaMismatchException;
    
    
}
