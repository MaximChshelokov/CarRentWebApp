package com.mv.schelokov.carent.model.db.dao.exceptions;

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
    
    public DbException(String err) {
        super(err);
    }
}
