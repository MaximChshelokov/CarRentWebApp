package com.mv.schelokov.carent.model.services.exceptions;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class ServiceException extends Exception {

    public ServiceException(String err, Throwable ex) {
        super(err, ex);
    }

    public ServiceException(String err) {
        super(err);
    }
    
}
