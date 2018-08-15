package com.mv.schelokov.carent.actions.admin;

import com.mv.schelokov.carent.actions.interfaces.AbstractAction;
import com.mv.schelokov.carent.actions.JspForward;
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
public class UpdateCar extends AbstractAction {
    private static final Logger LOG = Logger.getLogger(UpdateCar.class);
    private static final String ERROR = "Unable to write car to database.";

    @Override
    public JspForward execute(HttpServletRequest req, HttpServletResponse res)
            throws ActionException {
        
        JspForward forward = new JspForward();
        
        if (isAdmin(req)) {
            try {
                int carId = getIntParam(req, "id");
                
                CarModelService carModelService = new CarModelService();
                
                Car car = new CarBuilder()
                        .withId(carId)
                        .withLicensePlate(req.getParameter("plate"))
                        .withPrice(getIntParam(req, "price"))
                        .inYearOfMake(getIntParam(req, "year"))
                        .withModel(carModelService.getModelByNameOrCreate(
                                req.getParameter("model"),
                                req.getParameter("make")))
                        .getCar();
                int validationResult = new CarValidator(car).validate();
                if (validationResult != ValidationResult.OK) {
                    req.setAttribute("car", car);
                    req.setAttribute("errParam", validationResult);
                    
                    forward.setUrl(Jsps.ADMIN_EDIT_CAR);
                    
                    return forward;
                }
                
                CarService carService = new CarService();
                
                if (car.getId() == 0) {
                    car.setAvailable(true);
                    carService.createCar(car);
                } else
                    carService.updateCar(car);
                
                forward.setUrl("action/car_list");
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
