package com.mv.schelokov.car_rent.controller.actions.admin;

import com.mv.schelokov.car_rent.controller.actions.AbstractAction;
import com.mv.schelokov.car_rent.controller.actions.JspForward;
import com.mv.schelokov.car_rent.controller.consts.Jsps;
import com.mv.schelokov.car_rent.controller.consts.SessionAttr;
import com.mv.schelokov.car_rent.controller.exceptions.ActionException;
import com.mv.schelokov.car_rent.model.entities.Car;
import com.mv.schelokov.car_rent.model.entities.Make;
import com.mv.schelokov.car_rent.model.entities.Model;
import com.mv.schelokov.car_rent.model.entities.builders.MakeBuilder;
import com.mv.schelokov.car_rent.model.entities.builders.ModelBuilder;
import com.mv.schelokov.car_rent.model.services.CarService;
import com.mv.schelokov.car_rent.model.services.exceptions.ServiceException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
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
    private static final CarService CAR_SERVICE = new CarService();
    private static final int OK = 0;
    private static final int WRONG_YEAR = 1;
    private static final int EMPTY_FIELD = 2;
    private static final int WRONG_PRICE = 3;

    @Override
    public JspForward execute(HttpServletRequest req, HttpServletResponse res)
            throws ActionException {
        Car car = (Car) pickSessionAttribute(req, SessionAttr.CAR);
        if (isAdmin(req) && car != null) {
            try {
                car.setModel(getModel(req.getParameter("model"), 
                        req.getParameter("make")));
                car.setLicensePlate(req.getParameter("plate"));
                car.setYearOfMake(getIntParam(req, "year"));
                car.setPrice(getIntParam(req, "price"));
                int validationResult = validate(car);
                if (validationResult != OK) {
                    req.getSession().setAttribute(SessionAttr.CAR, car);
                    req.setAttribute("errParam", validationResult);
                    return new JspForward(Jsps.ADMIN_EDIT_CAR);
                }
                if (car.getId() == 0) {
                    car.setAvailable(true);
                    CAR_SERVICE.createCar(car);
                } else
                    CAR_SERVICE.updateCar(car);
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
    
    private Model getModel(String modelName, String makeName)
            throws ServiceException {
        if (modelName == null || modelName.isEmpty())
            return new ModelBuilder().setId(0).getModel();
        Model model = new ModelBuilder()
                .setName(modelName)
                .setMake(getMake(makeName))
                .getModel();
        LOG.debug(model.getMake());
        List modelList = CAR_SERVICE.getModel(model);
        if (modelList.isEmpty())
            modelList = CAR_SERVICE.createModel(model);
        return (Model) modelList.get(0);
    }
    
    private Make getMake(String makeName) throws ServiceException {
        if (makeName == null || makeName.isEmpty())
            return new MakeBuilder().setId(0).getMake();
        List makeList = CAR_SERVICE.getMakeByName(makeName);
        if (makeList.isEmpty())
            makeList = CAR_SERVICE.createMake(new MakeBuilder()
                    .setName(makeName)
                    .getMake());
        return (Make) makeList.get(0);
        
    }
    
    private int validate(Car car) {
        Date date = new Date();
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        if (car.getLicensePlate().isEmpty() || car.getModel().getId() == 0
                || car.getModel().getMake().getId() == 0 || 
                car.getYearOfMake() == -1)
            return EMPTY_FIELD;
        if (1970 > car.getYearOfMake() || car.getYearOfMake() > year)
            return WRONG_YEAR;
        if (car.getPrice() <= 0)
            return WRONG_PRICE;
        return OK;        
    }
}
