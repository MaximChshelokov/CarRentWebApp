package com.mv.schelokov.carent.model.validators;

import com.mv.schelokov.carent.model.entity.interfaces.Entity;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public abstract class Validator {
    
    public abstract int validate();
    
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
