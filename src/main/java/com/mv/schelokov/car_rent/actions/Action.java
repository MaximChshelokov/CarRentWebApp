package com.mv.schelokov.car_rent.actions;

import com.mv.schelokov.car_rent.exceptions.ActionException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public interface Action {
    
    JspForward execute(HttpServletRequest req, HttpServletResponse res)
            throws ActionException;
    
}
