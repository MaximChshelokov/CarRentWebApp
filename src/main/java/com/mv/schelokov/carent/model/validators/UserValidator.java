package com.mv.schelokov.carent.model.validators;

import com.mv.schelokov.carent.model.entity.User;
import java.util.regex.Pattern;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class UserValidator extends Validator {
    
    private final User user;
    private static final Pattern VALID_EMAIL_ADDRESS
            = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
                    Pattern.CASE_INSENSITIVE);
    private static final Pattern VALID_PASSWORD
            = Pattern.compile("^[a-z0-9_-]{6,18}$", Pattern.CASE_INSENSITIVE);
    
    public UserValidator(User user) {
        this.user = user;
    }
    
    @Override
    public int validate() {
        
        if (hasEmptyFields())
            return ValidationResult.EMPTY_FIELD;
        
        if (!isValidEmail())
            return ValidationResult.INVALID_EMAIL;
        
        if (!isValidPassword())
            return ValidationResult.INVALID_PASSWORD;
        
        if (!isValidEntity(user.getRole()))
            return ValidationResult.INVALID_ROLE;
        
        return ValidationResult.OK;
    }
    
    private boolean hasEmptyFields() {
        
        return isNullField(user.getLogin(), user.getPassword(), user.getRole())
                || isEmptyString(user.getLogin(), user.getPassword());
    }
    
    private boolean isValidEmail() {
        return VALID_EMAIL_ADDRESS.matcher(user.getLogin()).find();
    }
    
    private boolean isValidPassword() {
        return VALID_PASSWORD.matcher(user.getPassword()).find();
    }
}
