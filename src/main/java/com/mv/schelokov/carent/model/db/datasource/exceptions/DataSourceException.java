package com.mv.schelokov.carent.model.db.datasource.exceptions;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class DataSourceException extends Exception {
    
    public DataSourceException() {
        super();
    }
    
    public DataSourceException(String err) {
        super(err);
    }
    
    public DataSourceException(String err, Throwable ex) {
        super(err, ex);
    }
    
     public DataSourceException(String err, String value, Throwable ex) {
        super(String.format("%s: %s", err, value), ex);
    }   
}
