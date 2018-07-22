package com.mv.schelokov.car_rent.model.validators;

import com.mv.schelokov.car_rent.model.entities.interfaces.Entity;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class Validator {
    
    protected static boolean isNullField(Object... fields) {
        for (Object field : fields) {
            if (field == null) {
                return true;
            }
        }
        return false;
    }

    protected static boolean isEmptyString(String... fields) {
        for (String field : fields) {
            if (field.isEmpty()) {
                return true;
            }
        }
        return false;
    }
    
    protected static boolean isValidEntity(Entity entity) {
        return entity.getId() > 0;
    }
}
