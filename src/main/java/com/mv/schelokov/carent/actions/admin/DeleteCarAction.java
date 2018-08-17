package com.mv.schelokov.carent.actions.admin;

import com.mv.schelokov.carent.actions.interfaces.AbstractAction;
import com.mv.schelokov.carent.actions.JspForward;
import com.mv.schelokov.carent.actions.consts.Actions;
import com.mv.schelokov.carent.actions.exceptions.ActionException;
import com.mv.schelokov.carent.model.entity.builders.CarBuilder;
import com.mv.schelokov.carent.model.services.CarService;
import com.mv.schelokov.carent.model.services.exceptions.ServiceException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class DeleteCarAction extends AbstractAction {
    private static final Logger LOG = Logger.getLogger(DeleteCarAction.class);
    private static final String ERROR = "Unable to delete car";

    @Override
    public JspForward execute(HttpServletRequest req, HttpServletResponse res)
            throws ActionException {
        
        JspForward forward = new JspForward();
        if (isAdmin(req)) {
            int carId = getIntParam(req, ID);
            if (carId < 1)
                throw new ActionException(WRONG_ID);
            try {
                CarService carService = new CarService();

                carService.deleteCar(new CarBuilder().withId(carId).getCar());
                
                forward.setUrl(Actions.getActionName(Actions.CAR_LIST));
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
