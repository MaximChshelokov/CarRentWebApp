package com.mv.schelokov.carent.actions.user;

import com.mv.schelokov.carent.actions.interfaces.AbstractAction;
import com.mv.schelokov.carent.actions.JspForward;
import com.mv.schelokov.carent.actions.consts.Jsps;
import com.mv.schelokov.carent.actions.consts.SessionAttr;
import com.mv.schelokov.carent.actions.exceptions.ActionException;
import com.mv.schelokov.carent.model.entity.User;
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
public class UpdateProfile extends AbstractAction {

    private static final Logger LOG = Logger.getLogger(UpdateProfile.class);
    private static final String ERROR = "Unable to write data to database.";
    
    @Override
    public JspForward execute(HttpServletRequest req, HttpServletResponse res)
            throws ActionException {
        
        JspForward forward = new JspForward();
        
        if (isUser(req) || isAdmin(req)) {
            try {
                User user = (User) req.getSession()
                        .getAttribute(SessionAttr.USER);
                
                UserDataService userDataService = UserDataService.getInstance();
                UserData userData = userDataService
                        .getUserDataById(user.getId());
                
                userData.setName(req.getParameter("name"));
                userData.setAddress(req.getParameter("address"));
                userData.setPhone(req.getParameter("phone")
                        .replaceAll("[^0-9]+", ""));
                String hashedPass = userData.getUser().getPassword();
                userData.getUser().setPassword(req.getParameter("password"));
                
                int validationResult = new UserDataValidator(userData).validate();
                
                if ("password".equals(userData.getUser().getPassword()))
                    userData.getUser().setPassword(hashedPass);
                
                if (validationResult == ValidationResult.OK) {
                    if (userData.getId() > 0)
                        userDataService.updateUserData(userData);
                    else
                        userDataService.addUserData(userData);
                    
                    forward.setUrl("action/home");
                    forward.setRedirect(true);
                    
                    return forward;
                }
                req.setAttribute("user_data", userData);
                req.setAttribute("errParam", validationResult);
                
                forward.setUrl(Jsps.USER_EDIT_PROFILE);
                
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
