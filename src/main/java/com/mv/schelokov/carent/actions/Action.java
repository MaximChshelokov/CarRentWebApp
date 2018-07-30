package com.mv.schelokov.carent.actions;

import com.mv.schelokov.carent.exceptions.ActionException;
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
