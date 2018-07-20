package com.mv.schelokov.car_rent.controller.actions.user;

import com.mv.schelokov.car_rent.controller.actions.AbstractAction;
import com.mv.schelokov.car_rent.controller.actions.JspForward;
import com.mv.schelokov.car_rent.controller.consts.Jsps;
import com.mv.schelokov.car_rent.controller.consts.SessionAttr;
import com.mv.schelokov.car_rent.controller.exceptions.ActionException;
import com.mv.schelokov.car_rent.model.entities.User;
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
public class UpdateProfile extends AbstractAction {

    private static final Logger LOG = Logger.getLogger(UpdateProfile.class);
    private static final String ERROR = "Unable to write data to database.";
    private static final UserService USER_SERVICE = new UserService();
    private static final int OK = 0;
    private static final int EMPTY_FIELD = 1;
    private static final int WRONG_PHONE = 2;
    private static final Pattern VALID_PHONE_NUMBER
            = Pattern.compile("^[0-9]{11}$");
    
    @Override
    public JspForward execute(HttpServletRequest req, HttpServletResponse res)
            throws ActionException {
        if (isUser(req) || isAdmin(req)) {
            String name = req.getParameter("name");
            String address = req.getParameter("address");
            String phone = req.getParameter("phone").replaceAll("[^0-9]+", "");
            int validationResult = validate(name, address, phone);
            try {
                User user = (User) req.getSession()
                        .getAttribute(SessionAttr.USER);
                UserData userData = USER_SERVICE.getUserDataById(user.getId());
                userData.setName(name);
                userData.setAddress(address);
                userData.setPhone(phone);
                if (validationResult == OK) {
                    if (userData.getId() > 0)
                        USER_SERVICE.updateUserData(userData);
                    else
                        USER_SERVICE.addUserData(userData);
                    return new JspForward("action/home", true);
                }
                req.setAttribute("user_data", userData);
                req.setAttribute("errParam", validationResult);
                return new JspForward(Jsps.USER_EDIT_PROFILE);
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
    
    private int validate(String name, String address, String phone) {
        if (name == null || name.isEmpty() || address == null 
                || address.isEmpty() || phone == null || phone.isEmpty()) {
            return EMPTY_FIELD;
        }
        if (!VALID_PHONE_NUMBER.matcher(phone).find()) {
            return WRONG_PHONE;
        }
        return OK;
    }
    
}
