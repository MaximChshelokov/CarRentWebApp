package com.mv.schelokov.car_rent.controller.actions.admin;

import com.mv.schelokov.car_rent.controller.actions.AbstractAction;
import com.mv.schelokov.car_rent.controller.actions.JspForward;
import com.mv.schelokov.car_rent.controller.exceptions.ActionException;
import com.mv.schelokov.car_rent.model.entities.UserData;
import com.mv.schelokov.car_rent.model.entities.builders.UserBuilder;
import com.mv.schelokov.car_rent.model.entities.builders.UserDataBuilder;
import com.mv.schelokov.car_rent.model.services.UserService;
import com.mv.schelokov.car_rent.model.services.exceptions.ServiceException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class DeleteUser extends AbstractAction {
    
    private static final Logger LOG = Logger.getLogger(AbstractAction.class);
    private static final String ERROR = "Unable to delete user";
    
    @Override
    public JspForward execute(HttpServletRequest req, HttpServletResponse res)
            throws ActionException {
        if (isAdmin(req)) {
            int userId = getIntParam(req, "id");
            int userDataId = getIntParam(req, "uid");

            UserData userData = new UserDataBuilder().setId(userDataId)
                    .setUser(new UserBuilder().setId(userId).getUser())
                    .getUserData();
            try {
                if (userDataId > 0) {
                    UserService.deleteUserData(userData);
                } else if (userId > 0) {
                    UserService.deleteUser(userData.getUser());
                } else {
                    throw new ActionException("Failed to delete user, wrong id");
                }

                return new JspForward("action/user_list");
            }
            catch (ServiceException ex) {
                LOG.error(ERROR);
                throw new ActionException(ERROR, ex);
            }
        } else {
            sendForbidden(res);
            return null;
        }
    }
}
