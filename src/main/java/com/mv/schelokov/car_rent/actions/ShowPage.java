package com.mv.schelokov.car_rent.actions;

import com.mv.schelokov.car_rent.exceptions.ActionException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class ShowPage extends AbstractAction {
    
    private final String url;
    
    public ShowPage(String url) {
        this.url = url;
    }

    @Override
    public JspForward execute(HttpServletRequest req, HttpServletResponse res)
            throws ActionException {
        return new JspForward(url);
    }
    
}
