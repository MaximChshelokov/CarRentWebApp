package com.mv.schelokov.car_rent.actions.admin;

import com.mv.schelokov.car_rent.actions.AbstractAction;
import com.mv.schelokov.car_rent.actions.JspForward;
import com.mv.schelokov.car_rent.consts.Jsps;
import com.mv.schelokov.car_rent.exceptions.ActionException;
import com.mv.schelokov.car_rent.model.entities.UserData;
import com.mv.schelokov.car_rent.model.services.UserService;
import com.mv.schelokov.car_rent.model.services.exceptions.ServiceException;
import com.mv.schelokov.car_rent.model.validators.UserDataValidator;
import com.mv.schelokov.car_rent.model.validators.ValidationResult;
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

    @Override
    public JspForward execute(HttpServletRequest req, HttpServletResponse res)
            throws ActionException {
        
        JspForward forward = new JspForward();
        
        if (isAdmin(req)) {
            int userDataId = getIntParam(req, "id");
            if (userDataId < 1) {
                throw new ActionException("Wrong user_id parameter");
            }
            try {
                UserData userData = UserService.getUserDataById(userDataId);
                userData.setName(req.getParameter("name"));
                userData.setAddress(req.getParameter("address"));
                userData.setPhone(req.getParameter("phone").replaceAll("[^0-9]+", ""));
                userData.getUser().setLogin(req.getParameter("login"));
                
                int validationResult = new UserDataValidator(userData).validate();
                if (validationResult == ValidationResult.OK && !UserService
                        .getUserByLogin(userData.getUser().getLogin()).isEmpty())
                    validationResult = ValidationResult.SAME_LOGIN;

                if (validationResult == ValidationResult.OK) {
                    if (userData.getId() == 0) {
                        UserService.addUserData(userData);
                    } else {
                        UserService.updateUserData(userData);
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
