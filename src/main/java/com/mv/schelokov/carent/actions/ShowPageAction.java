package com.mv.schelokov.carent.actions;

import com.mv.schelokov.carent.actions.interfaces.AbstractAction;
import com.mv.schelokov.carent.actions.exceptions.ActionException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class ShowPageAction extends AbstractAction {
    
    private final String url;
    
    public ShowPageAction(String url) {
        this.url = url;
    }

    @Override
    public JspForward execute(HttpServletRequest req, HttpServletResponse res)
            throws ActionException {
        return new JspForward(url);
    }
    
}
