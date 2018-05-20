package com.mv.schelokov.car_rent.db.repository.interfaces;

import java.sql.Connection;
import java.util.List;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public abstract class AbstractSqlRepository <T> implements Repository <T> {
    
    private Connection connection;

    @Override
    public abstract List<T> query(Criteria criteria);

    @Override
    public abstract boolean remove(Criteria criteria);

    @Override
    public abstract boolean remove(T item);

    @Override
    public abstract boolean update(T item);

    @Override
    public abstract boolean add(Iterable<T> items);

    @Override
    public abstract boolean add(T item);
    
    protected abstract String getCreateQuery();
    
    protected abstract String getRemoveQuery();
    
    protected abstract String getUpdateQuery();
    
    
}
