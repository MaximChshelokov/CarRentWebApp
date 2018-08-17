package com.mv.schelokov.carent.actions.admin;

import com.mv.schelokov.carent.actions.interfaces.AbstractAction;
import com.mv.schelokov.carent.actions.JspForward;
import com.mv.schelokov.carent.actions.consts.Actions;
import com.mv.schelokov.carent.actions.consts.Jsps;
import com.mv.schelokov.carent.actions.exceptions.ActionException;
import com.mv.schelokov.carent.model.entity.Car;
import com.mv.schelokov.carent.model.entity.builders.CarBuilder;
import com.mv.schelokov.carent.model.services.CarModelService;
import com.mv.schelokov.carent.model.services.CarService;
import com.mv.schelokov.carent.model.services.exceptions.ServiceException;
import com.mv.schelokov.carent.model.validators.CarValidator;
import com.mv.schelokov.carent.model.validators.ValidationResult;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class UpdateCarAction extends AbstractAction {
    private static final Logger LOG = Logger.getLogger(UpdateCarAction.class);
    private static final String ERROR = "Unable to write car to database";
    private static final String MAKE = "make";
    private static final String MODEL = "model";
    private static final String YEAR = "year";
    private static final String PRICE = "price";
    private static final String PLATE = "plate";

    @Override
    public JspForward execute(HttpServletRequest req, HttpServletResponse res)
            throws ActionException {
        
        JspForward forward = new JspForward();
        
        if (isAdmin(req)) {
            try {
                int carId = getIntParam(req, ID);
                
                CarModelService carModelService = new CarModelService();
                
                Car car = new CarBuilder()
                        .withId(carId)
                        .withLicensePlate(req.getParameter(PLATE))
                        .withPrice(getIntParam(req, PRICE))
                        .inYearOfMake(getIntParam(req, YEAR))
                        .withModel(carModelService.getModelByNameOrCreate(
                                req.getParameter(MODEL),
                                req.getParameter(MAKE)))
                        .getCar();
                int validationResult = new CarValidator(car).validate();
                if (validationResult != ValidationResult.OK) {
                    req.setAttribute(CAR, car);
                    req.setAttribute(ERROR_NUMBER, validationResult);
                    
                    forward.setUrl(Jsps.ADMIN_EDIT_CAR);
                    
                    return forward;
                }
                
                CarService carService = new CarService();
                
                if (car.getId() == 0) {
                    car.setAvailable(true);
                    carService.createCar(car);
                } else
                    carService.updateCar(car);
                
                forward.setUrl(Actions.getActionName(Actions.CAR_LIST));
                forward.setRedirect(true);
                
                return forward;
            }
            catch (ServiceException ex) {
                LOG.error(ERROR, ex);
                throw new ActionException(ERROR, ex);
            }
        }
        sendForbidden(res);
        return forward;
    }
}
