package com.mv.schelokov.carent.actions;

import com.mv.schelokov.carent.actions.consts.Actions;
import com.mv.schelokov.carent.actions.interfaces.AbstractAction;
import com.mv.schelokov.carent.actions.consts.Jsps;
import com.mv.schelokov.carent.actions.consts.SessionAttr;
import com.mv.schelokov.carent.actions.exceptions.ActionException;
import com.mv.schelokov.carent.model.entity.User;
import com.mv.schelokov.carent.model.entity.builders.UserBuilder;
import com.mv.schelokov.carent.model.services.UserService;
import com.mv.schelokov.carent.model.services.exceptions.ServiceException;
import com.mv.schelokov.carent.model.validators.UserValidator;
import com.mv.schelokov.carent.model.validators.ValidationResult;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class LoginAction extends AbstractAction {
    
    private static final Logger LOG = Logger.getLogger(LoginAction.class);
    private static final String ERROR = "Failed to login";

    @Override
    public JspForward execute(HttpServletRequest req, HttpServletResponse res)
            throws ActionException {
        User user = new UserBuilder()
                .withLogin(req.getParameter(LOGIN))
                .withPassword(req.getParameter(PASSWORD))
                .withRole(getRoleWithName(req, ADMIN))
                .getUser();
        try {
            UserService userService = new UserService();
            int validationResult = userService.validateLoginUser(user);
            if (validationResult == ValidationResult.OK) {
                HttpSession session = req.getSession();
                session.setAttribute(SessionAttr.USER, 
                        userService.getUserByCredentials(user));
                if (isAdmin(req)) {
                    return new JspForward(Actions.getActionName(
                            Actions.ADMIN_ACTIONS), true);
                } else {
                    return new JspForward(Actions.getActionName(
                            Actions.WELCOME), true);
                }
            }
            req.setAttribute(ERROR_NUMBER, validationResult);
            req.setAttribute(SIGN_UP, false);
            req.setAttribute(USER_EDIT, user);
            return new JspForward(Jsps.HOME);
        } catch (ServiceException ex) {
            LOG.error(ERROR, ex);
            throw new ActionException(ERROR, ex);
        }
    }
}
