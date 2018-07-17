package com.mv.schelokov.car_rent.controller.actions.admin;

import com.mv.schelokov.car_rent.controller.actions.AbstractAction;
import com.mv.schelokov.car_rent.controller.actions.JspForward;
import com.mv.schelokov.car_rent.controller.consts.Jsps;
import com.mv.schelokov.car_rent.controller.exceptions.ActionException;
import com.mv.schelokov.car_rent.model.entities.builders.RoleBuilder;
import com.mv.schelokov.car_rent.model.entities.builders.UserBuilder;
import com.mv.schelokov.car_rent.model.services.UserService;
import com.mv.schelokov.car_rent.model.services.exceptions.ServiceException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class SaveUser extends AbstractAction {
    
    private static final Logger LOG = Logger.getLogger(SaveUser.class);
    private static final String ERROR = "Unable to write user to database";
    private static final int OK = 0;
    private static final int EMPTY_FIELD = 1;
    private static final int PASSWORDS_NOT_MATCH = 2;
    private static final int SAME_LOGIN = 3;

    @Override
    public JspForward execute(HttpServletRequest req, HttpServletResponse res)
            throws ActionException {
        if (isAdmin(req)) {
            UserService userService = new UserService();
            try {
                String login = req.getParameter("login");
                String password = req.getParameter("password");
                String repeat = req.getParameter("repeat");
                int roleId = getIntParam(req, "role");
                int validationResult = validate(login, password, repeat, roleId);
                if (validationResult == OK
                        && !userService.getUserByLogin(login).isEmpty()) {
                    validationResult = SAME_LOGIN;
                }
                if (validationResult == OK) {
                    userService.registerNewUser(new UserBuilder()
                            .setLogin(login)
                            .setPassword(password)
                            .setRole(new RoleBuilder().setId(roleId).getRole())
                            .getUser());
                    return new JspForward("action/user_list", true);
                }
                req.setAttribute("errParam", validationResult);
                req.setAttribute("login", login);
                req.setAttribute("password", password);
                req.setAttribute("roleId", roleId);
                req.setAttribute("roles", userService.getAllRoles());
                return new JspForward(Jsps.ADMIN_ADD_USER);
            } catch (ServiceException ex) {
                LOG.error(ERROR, ex);
                throw new ActionException(ERROR, ex);
            }
        } else {
            sendForbidden(res);
            return null;
        }
    }
    
    private int validate(String login, String password, String repeat, int roleId) {
        if (login == null || login.isEmpty() || password == null
                || password.isEmpty() || repeat == null || repeat.isEmpty()
                || roleId < 1)
            return EMPTY_FIELD;
        if (!password.equals(repeat))
            return PASSWORDS_NOT_MATCH;
        return OK;
    }
}
