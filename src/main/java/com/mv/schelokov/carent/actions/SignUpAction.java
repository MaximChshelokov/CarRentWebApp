package com.mv.schelokov.carent.actions;

import com.mv.schelokov.carent.actions.consts.Actions;
import com.mv.schelokov.carent.actions.consts.Jsps;
import com.mv.schelokov.carent.actions.consts.SessionAttr;
import com.mv.schelokov.carent.actions.exceptions.ActionException;
import com.mv.schelokov.carent.actions.interfaces.AbstractAction;
import com.mv.schelokov.carent.model.entity.User;
import com.mv.schelokov.carent.model.entity.builders.UserBuilder;
import com.mv.schelokov.carent.model.services.UserService;
import com.mv.schelokov.carent.model.services.exceptions.ServiceException;
import com.mv.schelokov.carent.model.validators.ValidationResult;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class SignUpAction extends AbstractAction {
    
    private static final Logger LOG = Logger.getLogger(SignUpAction.class);
    private static final String ERROR = "Unable to create a new user";
    
    @Override
    public JspForward execute(HttpServletRequest req, HttpServletResponse res)
            throws ActionException {

        JspForward forward = new JspForward();

        try {
            User user = new UserBuilder()
                    .withLogin(req.getParameter(LOGIN))
                    .withPassword(req.getParameter(PASSWORD))
                    .withRole(getRoleWithName(req, USER))
                    .getUser();
            String repeatPassword = req.getParameter(REPEAT_PASSWORD);

            UserService userService = new UserService();
            int validationResult = userService.validateAndCreateUser(user,
                    repeatPassword);
            
            if (validationResult == ValidationResult.OK) {
                req.getSession().setAttribute(SessionAttr.USER,
                    userService.getUserByLogin(user.getLogin()).get(0));

                forward.setUrl(Actions.getActionName(Actions.WELCOME));
                forward.setRedirect(true);

                return forward;
            }
            
            req.setAttribute(ERROR_NUMBER, validationResult);
            req.setAttribute(SIGN_UP, true);
            req.setAttribute(USER_EDIT, user);

            forward.setUrl(Jsps.HOME);

            return forward;
        }
        catch (ServiceException ex) {
            LOG.error(ERROR, ex);
            throw new ActionException(ERROR, ex);
        }
    }
}
