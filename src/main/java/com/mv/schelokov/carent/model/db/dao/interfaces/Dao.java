package com.mv.schelokov.carent.model.db.dao.interfaces;

import com.mv.schelokov.carent.model.db.dao.exceptions.DbException;
import java.util.List;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 * @param <T> entity class
 */
public interface Dao<T> {
    
    /**
     * Creates a new item in a database table.
     *
     * @param item - some entity to be added.
     * @return true, if the entity was successfully created.
     * @throws DbException
     */ 
    boolean add(T item) throws DbException;

    /**
     * Updates a specified item in a database table.
     *
     * @param item - some entity to be updated.
     * @return true if entity was successfully updated.
     * @throws DbException
     */
    boolean update(T item) throws DbException;
    
    /**
     * Removes a specified item from a database table.
     *
     * @param item - some entity to be removed.
     * @return true if entity was successfully deleted.
     * @throws DbException
     */
    boolean remove(T item) throws DbException;
    
    /**
     * Removes records from a database table that match up to the criteria.
     *
     * @param criteria a special object implementing the criteria interface.
     * @return true if at least one record was removed.
     * @throws DbException
     */
    boolean remove(Criteria criteria) throws DbException;
    
    /**
     * Reads records from a database table that match up to the criteria.
     *
     * @param criteria a special object implementing the criteria interface.
     * @return a list of objects (entities) that match up th the criteria.
     * @throws DbException
     */
    List<T> read(Criteria criteria) throws DbException;
    
}
