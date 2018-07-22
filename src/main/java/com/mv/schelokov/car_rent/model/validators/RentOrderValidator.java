package com.mv.schelokov.car_rent.model.validators;

import com.mv.schelokov.car_rent.model.entities.RentOrder;
import com.mv.schelokov.car_rent.model.entities.interfaces.Entity;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class RentOrderValidator extends Validator {
    
    public static int validate(RentOrder order) {

        if (hasEmptyFields(order)) {
            return ValidationResult.EMPTY_FIELD;
        }

        if (!isValidEntity(order.getCar()))
            return ValidationResult.INVALID_CAR;
        
        if (!isValidEntity(order.getUser()))
            return ValidationResult.INVALID_USER;
        
        if (!isValidOrderDate(order))
            return ValidationResult.INVALID_DATE;

        return ValidationResult.OK;
    }
    
    private static boolean hasEmptyFields(RentOrder order) {

        return isNullField(order.getCar(), order.getUser(),
                order.getStartDate(), order.getEndDate());
    }
    
    private static boolean isValidOrderDate(RentOrder order) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(new Date());
        cal.set(GregorianCalendar.HOUR_OF_DAY, 0);
        cal.set(GregorianCalendar.MINUTE, 0);
        cal.set(GregorianCalendar.SECOND, 0);
        cal.set(GregorianCalendar.MILLISECOND, 0);
        Date today = cal.getTime();
        return !order.getStartDate().before(today)
                && order.getEndDate().after(order.getStartDate());
    }
}
