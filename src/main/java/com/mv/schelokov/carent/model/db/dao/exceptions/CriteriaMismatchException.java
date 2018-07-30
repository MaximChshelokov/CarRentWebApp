package com.mv.schelokov.carent.model.db.dao.exceptions;

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
    
    public CriteriaMismatchException(String err) {
        super(err);
    }
    
}
