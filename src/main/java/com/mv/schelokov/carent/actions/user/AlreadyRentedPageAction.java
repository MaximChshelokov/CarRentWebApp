package com.mv.schelokov.carent.actions.user;

import com.mv.schelokov.carent.actions.JspForward;
import com.mv.schelokov.carent.actions.consts.Jsps;
import com.mv.schelokov.carent.actions.exceptions.ActionException;
import com.mv.schelokov.carent.actions.interfaces.AbstractAction;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class AlreadyRentedPageAction extends AbstractAction {

    @Override
    public JspForward execute(HttpServletRequest req, HttpServletResponse res)
            throws ActionException {

        JspForward forward = new JspForward();

        if (isUser(req)) {

            forward.setUrl(Jsps.USER_ALREADY_RENTED);

            return forward;

        } else {
            sendForbidden(res);
            return forward;
        }
    }
}
