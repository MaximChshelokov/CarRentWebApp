package com.mv.schelokov.car_rent.controller.actions.admin;

import com.mv.schelokov.car_rent.controller.actions.AbstractAction;
import com.mv.schelokov.car_rent.controller.actions.JspForward;
import com.mv.schelokov.car_rent.controller.consts.Jsps;
import com.mv.schelokov.car_rent.controller.exceptions.ActionException;
import com.mv.schelokov.car_rent.model.entities.Car;
import com.mv.schelokov.car_rent.model.entities.builders.CarBuilder;
import com.mv.schelokov.car_rent.model.services.CarService;
import com.mv.schelokov.car_rent.model.services.exceptions.ServiceException;
import com.mv.schelokov.car_rent.model.validators.CarValidator;
import com.mv.schelokov.car_rent.model.validators.ValidationResult;
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
        if (isAdmin(req)) {
            try {
                int carId = getIntParam(req, "id");
                Car car = new CarBuilder()
                        .setId(carId)
                        .setLicensePlate(req.getParameter("plate"))
                        .setPrice(getIntParam(req, "price"))
                        .setYearOfMake(getIntParam(req, "year"))
                        .setModel(CarService.getModelByNameOrCreate(
                                req.getParameter("model"),
                                req.getParameter("make")))
                        .getCar();
                int validationResult = new CarValidator(car).validate();
                if (validationResult != ValidationResult.OK) {
                    req.setAttribute("car", car);
                    req.setAttribute("errParam", validationResult);
                    return new JspForward(Jsps.ADMIN_EDIT_CAR);
                }
                if (car.getId() == 0) {
                    car.setAvailable(true);
                    CarService.createCar(car);
                } else
                    CarService.updateCar(car);
                return new JspForward("action/car_list", true);
            }
            catch (ServiceException ex) {
                LOG.error(ERROR, ex);
                throw new ActionException(ERROR, ex);
            }
        }
        sendForbidden(res);
        return null;
    }
}
