package com.mv.schelokov.carent.actions.admin;

import com.mv.schelokov.carent.actions.interfaces.AbstractAction;
import com.mv.schelokov.carent.actions.JspForward;
import com.mv.schelokov.carent.actions.consts.Actions;
import com.mv.schelokov.carent.actions.consts.Jsps;
import com.mv.schelokov.carent.actions.exceptions.ActionException;
import com.mv.schelokov.carent.model.entity.User;
import com.mv.schelokov.carent.model.entity.UserData;
import com.mv.schelokov.carent.model.services.UserDataService;
import com.mv.schelokov.carent.model.services.UserService;
import com.mv.schelokov.carent.model.services.exceptions.ServiceException;
import com.mv.schelokov.carent.model.validators.UserDataValidator;
import com.mv.schelokov.carent.model.validators.ValidationResult;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class UpdateUserAction extends AbstractAction {
    
    private static final Logger LOG = Logger.getLogger(UpdateUserAction.class);
    private static final String ERROR = "Unable to write data to database";

    @Override
    public JspForward execute(HttpServletRequest req, HttpServletResponse res)
            throws ActionException {
        
        JspForward forward = new JspForward();
        
        if (isAdmin(req)) {
            int userDataId = getIntParam(req, ID);
            if (userDataId < 1) {
                throw new ActionException(WRONG_ID);
            }
            try {
                UserDataService userDataService = new UserDataService();
                
                UserData userData = userDataService.getUserDataById(userDataId);
                userData.setName(req.getParameter(NAME));
                userData.setAddress(req.getParameter(ADDRESS));
                userData.setPhone(req.getParameter(PHONE)
                        .replaceAll(REGEX_NUMBER, ""));
                userData.getUser().setLogin(req.getParameter(LOGIN));
                
                int validationResult = new UserDataValidator(userData).validate();
                UserService userService = new UserService();
                if (validationResult == ValidationResult.OK) {
                        List<User> userList = userService.getUserByLogin(
                                userData.getUser().getLogin());
                        if (!userList.isEmpty() && userList.get(0).getId()
                                != userDataId)
                            validationResult = ValidationResult.SAME_LOGIN;
                }

                if (validationResult == ValidationResult.OK) {
                    if (userData.getId() == 0) {
                        userDataService.addUserData(userData);
                    } else {
                        userDataService.updateUserData(userData);
                    }
                    
                    forward.setUrl(Actions.getActionName(Actions.USER_LIST));
                    forward.setRedirect(true);
                    
                    return forward;
                }
                req.setAttribute(USER_DATA, userData);
                req.setAttribute(ERROR_NUMBER, validationResult);
                
                forward.setUrl(Jsps.ADMIN_EDIT_USER);
                
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
