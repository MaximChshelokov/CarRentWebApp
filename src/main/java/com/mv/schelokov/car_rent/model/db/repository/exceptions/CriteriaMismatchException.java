package com.mv.schelokov.car_rent.model.db.repository.exceptions;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class CriteriaMismatchException extends DbException {
    public CriteriaMismatchException(String err, Throwable ex) {
        super(err, ex);
    }

    public CriteriaMismatchException() {
        super();
    }
    
}
