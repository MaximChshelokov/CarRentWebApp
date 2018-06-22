package com.mv.schelokov.car_rent.model.db.repository.exceptions;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class RepositoryException extends Exception {
    public RepositoryException(String err, Throwable ex) {
        super(err, ex);
    }
}
