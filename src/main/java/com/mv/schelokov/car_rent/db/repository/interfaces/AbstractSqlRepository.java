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
 */
public abstract class AbstractSqlRepository <T> implements Repository <T> {
    
    private Connection connection;

    @Override
    public List<T> query(Criteria criteria) throws DbException {
        if (criteria instanceof SqlCriteria) {
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
                // Add logger
                throw new DbException("Error while creating PreparedStatement", ex);
            }
            
        } else
            throw new CriteriaMismatchException();
        return new ArrayList<>();
    }

    @Override
    public abstract boolean remove(Criteria criteria) throws DbException;

    @Override
    public abstract boolean remove(T item) throws DbException;

    @Override
    public abstract boolean update(T item) throws DbException;

    @Override
    public abstract boolean add(Iterable<T> items) throws DbException;

    @Override
    public abstract boolean add(T item) throws DbException;
    
    protected abstract String getCreateQuery();
    
    protected abstract String getRemoveQuery();
    
    protected abstract String getUpdateQuery();
    
    protected abstract T createItem(ResultSet rs);
    
    
}
