package com.mv.schelokov.carent.actions.admin;

import com.mv.schelokov.carent.actions.interfaces.AbstractAction;
import com.mv.schelokov.carent.actions.JspForward;
import com.mv.schelokov.carent.actions.consts.Actions;
import com.mv.schelokov.carent.actions.exceptions.ActionException;
import com.mv.schelokov.carent.model.entity.UserData;
import com.mv.schelokov.carent.model.entity.builders.UserBuilder;
import com.mv.schelokov.carent.model.entity.builders.UserDataBuilder;
import com.mv.schelokov.carent.model.services.UserDataService;
import com.mv.schelokov.carent.model.services.UserService;
import com.mv.schelokov.carent.model.services.exceptions.ServiceException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class DeleteUserAction extends AbstractAction {
    
    private static final Logger LOG = Logger.getLogger(AbstractAction.class);
    private static final String ERROR = "Unable to delete user";

    @Override
    public JspForward execute(HttpServletRequest req, HttpServletResponse res)
            throws ActionException {
        
        JspForward forward = new JspForward();
        
        if (isAdmin(req)) {
            int userId = getIntParam(req, ID);
            int userDataId = getIntParam(req, UID);

            UserData userData = new UserDataBuilder()
                    .withId(userDataId)
                    .withUser(new UserBuilder()
                            .withId(userId)
                            .getUser())
                    .getUserData();
            try {
                UserDataService userDataService = new UserDataService();
                UserService userService = new UserService();
                
                if (userDataId > 0) {
                    userDataService.deleteUserData(userData);
                } else if (userId > 0) {
                    userService.deleteUser(userData.getUser());
                } else {
                    throw new ActionException(WRONG_ID);
                }

                forward.setUrl(Actions.getActionName(Actions.USER_LIST));
                forward.setRedirect(true);
                
                return forward;
            }
            catch (ServiceException ex) {
                LOG.error(ERROR);
                throw new ActionException(ERROR, ex);
            }
        } else {
            sendForbidden(res);
            return forward;
        }
    }
}
