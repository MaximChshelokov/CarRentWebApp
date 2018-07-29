package com.mv.schelokov.car_rent.model.validators;

import com.mv.schelokov.car_rent.model.entity.RentOrder;
import com.mv.schelokov.car_rent.model.utils.DateUtils;
import java.util.Date;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class RentOrderValidator extends Validator {
    
    private final RentOrder order;
    
    public RentOrderValidator(RentOrder order) {
        this.order = order;
    }
    
    public int validate() {

        if (hasEmptyFields()) {
            return ValidationResult.EMPTY_FIELD;
        }

        if (!isValidEntity(order.getCar()))
            return ValidationResult.INVALID_CAR;
        
        if (!isValidEntity(order.getUser()))
            return ValidationResult.INVALID_USER;
        
        if (!isValidOrderDate())
            return ValidationResult.INVALID_DATE;

        return ValidationResult.OK;
    }
    
    private boolean hasEmptyFields() {

        return isNullField(order.getCar(), order.getUser(),
                order.getStartDate(), order.getEndDate());
    }
    
    private boolean isValidOrderDate() {
        Date today = DateUtils.onlyDate(new Date());
        return !order.getStartDate().before(today)
                && order.getEndDate().after(order.getStartDate());
    }
}
