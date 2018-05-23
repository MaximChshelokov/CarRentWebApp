/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mv.schelokov.car_rent.db.repository.exceptions;

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
