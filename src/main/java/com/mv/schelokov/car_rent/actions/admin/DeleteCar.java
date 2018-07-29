package com.mv.schelokov.car_rent.actions.admin;

import com.mv.schelokov.car_rent.actions.AbstractAction;
import com.mv.schelokov.car_rent.actions.JspForward;
import com.mv.schelokov.car_rent.exceptions.ActionException;
import com.mv.schelokov.car_rent.model.entity.builders.CarBuilder;
import com.mv.schelokov.car_rent.model.services.CarService;
import com.mv.schelokov.car_rent.model.services.exceptions.ServiceException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class DeleteCar extends AbstractAction {
    private static final Logger LOG = Logger.getLogger(DeleteCar.class);
    private static final String ERROR = "Unable to delete car";
    
    @Override
    public JspForward execute(HttpServletRequest req, HttpServletResponse res)
            throws ActionException {
        
        JspForward forward = new JspForward();
        if (isAdmin(req)) {
            int carId = getIntParam(req, "id");
            if (carId < 1)
                throw new ActionException("Failed to delete car, wrong id");
            try {
                CarService.deleteCar(new CarBuilder().setId(carId).getCar());
                
                forward.setUrl("action/car_list");
                forward.setRedirect(true);
                
                return forward;
            } catch (ServiceException ex) {
                LOG.error(ERROR, ex);
                throw new ActionException(ERROR, ex);
            }
        } else {
            sendForbidden(res);
            return forward;            
        }
    }
}
