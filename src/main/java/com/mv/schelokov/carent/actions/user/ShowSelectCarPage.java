package com.mv.schelokov.carent.actions.user;

import com.mv.schelokov.carent.actions.interfaces.AbstractAction;
import com.mv.schelokov.carent.actions.JspForward;
import com.mv.schelokov.carent.actions.consts.Jsps;
import com.mv.schelokov.carent.actions.exceptions.ActionException;
import com.mv.schelokov.carent.model.services.CarService;
import com.mv.schelokov.carent.model.services.exceptions.ServiceException;
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
        
        JspForward forward = new JspForward();
        
        if (isUser(req) || isAdmin(req)) {
            try {
                CarService carService = CarService.getInstance();
                
                req.setAttribute("car_list", carService.getAvailableCars());
                req.setAttribute("action", "create_order");
                
                forward.setUrl(Jsps.USER_SELECT_CAR);
                
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
