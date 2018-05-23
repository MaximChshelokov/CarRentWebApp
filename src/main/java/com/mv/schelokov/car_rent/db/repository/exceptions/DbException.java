package com.mv.schelokov.car_rent.db.repository.exceptions;

import java.sql.SQLException;

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
