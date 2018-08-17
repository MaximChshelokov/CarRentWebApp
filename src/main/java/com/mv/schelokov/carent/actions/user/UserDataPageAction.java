package com.mv.schelokov.carent.actions.user;

import com.mv.schelokov.carent.actions.JspForward;
import com.mv.schelokov.carent.actions.consts.Jsps;
import com.mv.schelokov.carent.actions.consts.SessionAttr;
import com.mv.schelokov.carent.actions.exceptions.ActionException;
import com.mv.schelokov.carent.actions.interfaces.AbstractAction;
import com.mv.schelokov.carent.model.entity.User;
import com.mv.schelokov.carent.model.services.UserDataService;
import com.mv.schelokov.carent.model.services.exceptions.ServiceException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class UserDataPageAction extends AbstractAction {

    private static final Logger LOG = Logger.getLogger(UserDataPageAction.class);
    private static final String ERROR = "Failed to read user data from database";

    @Override
    public JspForward execute(HttpServletRequest req, HttpServletResponse res)
            throws ActionException {

        JspForward forward = new JspForward();

        if (isUser(req)) {
            try {
                User user = (User) req.getSession()
                        .getAttribute(SessionAttr.USER);
                req.setAttribute(USER_DATA,
                        new UserDataService()
                                .getUserDataById(user.getId()));

                forward.setUrl(Jsps.USER_DATA);

                return forward;
            }
            catch (ServiceException ex) {
                LOG.error(ERROR, ex);
                throw new ActionException(ERROR, ex);
            }
        } else {
            sendForbidden(res);
            return forward;
        }
    }
}
