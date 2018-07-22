package com.mv.schelokov.car_rent.controller.actions.admin;

import com.mv.schelokov.car_rent.controller.actions.AbstractAction;
import com.mv.schelokov.car_rent.controller.actions.JspForward;
import com.mv.schelokov.car_rent.controller.consts.Jsps;
import com.mv.schelokov.car_rent.controller.exceptions.ActionException;
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
                
                int validationResult = UserDataValidator.validate(userData);
                if (validationResult == ValidationResult.OK && !UserService
                        .getUserByLogin(userData.getUser().getLogin()).isEmpty())
                    validationResult = ValidationResult.SAME_LOGIN;

                if (validationResult == ValidationResult.OK) {
                    if (userData.getId() == 0) {
                        UserService.addUserData(userData);
                    } else {
                        UserService.updateUserData(userData);
                    }
                    return new JspForward("action/user_list", true);
                }
                req.setAttribute("user_data", userData);
                req.setAttribute("errParam", validationResult);
                return new JspForward(Jsps.ADMIN_EDIT_USER);
            }
            catch (ServiceException ex) {
                LOG.error(ERROR, ex);
                throw new ActionException(ERROR, ex);
            }

        } else {
            sendForbidden(res);
            return null;
        }
    }
}
