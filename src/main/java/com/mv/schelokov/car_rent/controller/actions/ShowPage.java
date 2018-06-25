package com.mv.schelokov.car_rent.controller.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class ShowPage implements Action {
    
    private String url;
    
    public ShowPage(String url) {
        this.url = url;
    }

    @Override
    public JspRedirect execute(HttpServletRequest req, HttpServletResponse res) {
        return new JspRedirect(url);
    }
    
}
