package com.mv.schelokov.car_rent.controller.exceptions;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class ActionException extends Exception {
    public ActionException(String err, Throwable ex) {
        super(err, ex);
    }
    
}
