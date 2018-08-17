package com.mv.schelokov.carent.model.validators;

import com.mv.schelokov.carent.model.entity.RentOrder;
import com.mv.schelokov.carent.model.utils.OnlyDate;
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
    
    @Override
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
        Date today = new OnlyDate().getOnlyDate();
        return !order.getStartDate().before(today)
                && order.getEndDate().after(order.getStartDate());
    }
}
