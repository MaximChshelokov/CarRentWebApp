package com.mv.schelokov.car_rent.controller.actions.admin;

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
public class ShowCarList extends AbstractAction {
    
    private static final Logger LOG = Logger.getLogger(ShowCarList.class);
    private static final String ERROR = "Unable to prepare data for the list of"
            + " cars";

    @Override
    public JspForward execute(HttpServletRequest req, HttpServletResponse res)
            throws ActionException {
        if (isAdmin(req)) {
            CarService carService = new CarService();
            try {
                req.setAttribute("car_list", carService.getAllCars());
                return new JspForward(Jsps.ADMIN_CARS_LIST);
            } catch (ServiceException ex) {
                LOG.error(ERROR, ex);
                throw new ActionException(ERROR, ex);
            }
        } else {
            sendForbidden(res);
            return null;
        }
    }
}
