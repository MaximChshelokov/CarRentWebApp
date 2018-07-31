package com.mv.schelokov.carent.actions.exceptions;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class ActionException extends Exception {
    public ActionException(String err, Throwable ex) {
        super(err, ex);
    }

    public ActionException(String err) {
        super(err);
    }
    
}
