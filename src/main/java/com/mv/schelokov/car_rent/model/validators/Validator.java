package com.mv.schelokov.car_rent.model.validators;

import com.mv.schelokov.car_rent.model.entity.interfaces.Entity;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class Validator {
    
    protected boolean isNullField(Object... fields) {
        for (Object field : fields) {
            if (field == null) {
                return true;
            }
        }
        return false;
    }

    protected boolean isEmptyString(String... fields) {
        for (String field : fields) {
            if (field.isEmpty()) {
                return true;
            }
        }
        return false;
    }
    
    protected boolean isValidEntity(Entity entity) {
        return entity.getId() > 0;
    }
}
