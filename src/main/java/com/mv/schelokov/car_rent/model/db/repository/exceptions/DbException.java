package com.mv.schelokov.car_rent.model.db.repository.exceptions;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class DbException extends Exception {
    public DbException() {
        super();
    }
    public DbException(String err, Throwable ex) {
        super(err, ex);
    }
}
