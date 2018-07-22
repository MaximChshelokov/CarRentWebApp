package com.mv.schelokov.car_rent.controller.actions.user;

import com.mv.schelokov.car_rent.controller.actions.AbstractAction;
import com.mv.schelokov.car_rent.controller.actions.JspForward;
import com.mv.schelokov.car_rent.controller.consts.Jsps;
import com.mv.schelokov.car_rent.controller.exceptions.ActionException;
import com.mv.schelokov.car_rent.model.services.CarService;
import com.mv.schelokov.car_rent.model.services.exceptions.ServiceException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class ShowSelectCarPage extends AbstractAction {
    
    private static final Logger LOG = Logger.getLogger(ShowSelectCarPage.class);
    private static final String ERROR = "Unable to prepare data for the select"
            + " car page";

    @Override
    public JspForward execute(HttpServletRequest req, HttpServletResponse res)
            throws ActionException {
        if (isUser(req) || isAdmin(req)) {
            try {
                req.setAttribute("car_list", CarService.getAvailableCars());
                return new JspForward(Jsps.USER_SELECT_CAR);
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
