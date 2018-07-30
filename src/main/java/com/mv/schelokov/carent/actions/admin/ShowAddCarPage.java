package com.mv.schelokov.carent.actions.admin;

import com.mv.schelokov.carent.actions.AbstractAction;
import com.mv.schelokov.carent.actions.JspForward;
import com.mv.schelokov.carent.consts.Jsps;
import com.mv.schelokov.carent.exceptions.ActionException;
import com.mv.schelokov.carent.model.entity.Car;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class ShowAddCarPage extends AbstractAction {

    @Override
    public JspForward execute(HttpServletRequest req, HttpServletResponse res)
            throws ActionException {
        
        JspForward forward = new JspForward();
        
        if (isAdmin(req)) {
            req.setAttribute("car", new Car());
            
            forward.setUrl(Jsps.ADMIN_EDIT_CAR);
            
            return forward;
        }
        sendForbidden(res);
        return forward;
    }
}
