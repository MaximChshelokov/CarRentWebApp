package com.mv.schelokov.carent.actions.admin;

import com.mv.schelokov.carent.actions.interfaces.AbstractAction;
import com.mv.schelokov.carent.actions.JspForward;
import com.mv.schelokov.carent.actions.consts.Jsps;
import com.mv.schelokov.carent.actions.exceptions.ActionException;
import com.mv.schelokov.carent.model.entity.UserData;
import com.mv.schelokov.carent.model.services.UserDataService;
import com.mv.schelokov.carent.model.services.UserService;
import com.mv.schelokov.carent.model.services.exceptions.ServiceException;
import com.mv.schelokov.carent.model.validators.UserDataValidator;
import com.mv.schelokov.carent.model.validators.ValidationResult;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class UpdateUser extends AbstractAction {
    
    private static final Logger LOG = Logger.getLogger(UpdateUser.class);
    private static final String ERROR = "Unable to write data to database";
    private static final String WRONG_ID = "Wrong id parameter for user entity";

    @Override
    public JspForward execute(HttpServletRequest req, HttpServletResponse res)
            throws ActionException {
        
        JspForward forward = new JspForward();
        
        if (isAdmin(req)) {
            int userDataId = getIntParam(req, "id");
            if (userDataId < 1) {
                throw new ActionException(WRONG_ID);
            }
            try {
                UserDataService userDataService = UserDataService.getInstance();
                
                UserData userData = userDataService.getUserDataById(userDataId);
                userData.setName(req.getParameter("name"));
                userData.setAddress(req.getParameter("address"));
                userData.setPhone(req.getParameter("phone")
                        .replaceAll("[^0-9]+", ""));
                userData.getUser().setLogin(req.getParameter("login"));
                
                int validationResult = new UserDataValidator(userData).validate();
                UserService userService = UserService.getInstance();
                if (validationResult == ValidationResult.OK && !userService
                        .getUserByLogin(userData.getUser().getLogin()).isEmpty())
                    validationResult = ValidationResult.SAME_LOGIN;

                if (validationResult == ValidationResult.OK) {
                    if (userData.getId() == 0) {
                        userDataService.addUserData(userData);
                    } else {
                        userDataService.updateUserData(userData);
                    }
                    
                    forward.setUrl("action/user_list");
                    forward.setRedirect(true);
                    
                    return forward;
                }
                req.setAttribute("user_data", userData);
                req.setAttribute("errParam", validationResult);
                
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
