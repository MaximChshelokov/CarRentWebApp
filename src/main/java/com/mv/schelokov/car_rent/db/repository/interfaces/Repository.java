package com.mv.schelokov.car_rent.db.repository.interfaces;

import com.mv.schelokov.car_rent.db.repository.exceptions.DbException;
import java.util.List;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 * @param <T> entity class
 */
public interface Repository<T> {
    
    boolean add(T item) throws DbException;

    boolean add(Iterable<T> items) throws DbException;

    boolean update(T item) throws DbException;

    boolean remove(T item) throws DbException;

    boolean remove(Criteria criteria) throws DbException;

    List<T> query(Criteria criteria) throws DbException;
    
}
