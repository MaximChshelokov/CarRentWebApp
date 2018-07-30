package com.mv.schelokov.carent.model.db.dao.exceptions;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class DaoException extends Exception {
    public DaoException(String err, Throwable ex) {
        super(err, ex);
    }
}
