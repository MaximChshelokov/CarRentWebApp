package com.mv.schelokov.car_rent.controller.actions.admin;

import com.mv.schelokov.car_rent.controller.actions.AbstractAction;
import com.mv.schelokov.car_rent.controller.actions.JspForward;
import com.mv.schelokov.car_rent.controller.consts.Jsps;
import com.mv.schelokov.car_rent.controller.exceptions.ActionException;
import com.mv.schelokov.car_rent.model.entities.UserData;
import com.mv.schelokov.car_rent.model.services.UserService;
import com.mv.schelokov.car_rent.model.services.exceptions.ServiceException;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class UpdateUser extends AbstractAction {
    
    private static final Logger LOG = Logger.getLogger(AbstractAction.class);
    private static final String ERROR = "Unable to write data to base.";
    private static final UserService USER_SERVICE = new UserService();
    private static final int OK = 0;
    private static final int EMPTY_FIELD = 1;
    private static final int WRONG_PHONE = 2;
    private static final Pattern VALID_PHONE_NUMBER = 
            Pattern.compile("^[0-9]{10,12}$");


    @Override
    public JspForward execute(HttpServletRequest req, HttpServletResponse res)
            throws ActionException {
        if (isAdmin(req)) {
            String login = req.getParameter("login");
            String name = req.getParameter("name");
            String address = req.getParameter("address");
            String phone = req.getParameter("phone").replaceAll("[^0-9]+", "");
            LOG.debug(phone);
            int userDataId = getIntParam(req, "id");
            if (userDataId < 1) {
                throw new ActionException("Wrong user_id parameter");
            }
            int validationResult = validate(login, name, address, phone);
            LOG.debug(String.format("Validation result = %d", validationResult));

            try {
                UserData userData = USER_SERVICE.getUserDataById(userDataId);
                userData.getUser().setLogin(login);
                userData.setName(name);
                userData.setAddress(address);
                userData.setPhone(phone);
                if (validationResult == OK) {
                    if (userData.getId() == 0) {
                        USER_SERVICE.addUserData(userData);
                    } else {
                        USER_SERVICE.updateUserData(userData);
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
    
    
    private int validate(String login, String name, String address,
            String phone) {
        if (login == null || login.isEmpty() || name == null || name.isEmpty()
                || address == null || address.isEmpty() || phone == null
                || phone.isEmpty())
            return EMPTY_FIELD;
        if (!VALID_PHONE_NUMBER.matcher(phone).find())
            return WRONG_PHONE;
        return OK;
    }
    
}
