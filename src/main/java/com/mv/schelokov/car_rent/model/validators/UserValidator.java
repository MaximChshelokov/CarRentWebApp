package com.mv.schelokov.car_rent.model.validators;

import com.mv.schelokov.car_rent.model.entities.User;
import java.util.regex.Pattern;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class UserValidator extends Validator {
    
    private static final Pattern VALID_EMAIL_ADDRESS
            = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
                    Pattern.CASE_INSENSITIVE);
    private static final Pattern VALID_PASSWORD
            = Pattern.compile("^[a-z0-9_-]{6,18}$", Pattern.CASE_INSENSITIVE);
    
    public static int validate(User user) {
        
        if (hasEmptyFields(user))
            return ValidationResult.EMPTY_FIELD;
        
        if (!isValidEmail(user.getLogin()))
            return ValidationResult.INVALID_EMAIL;
        
        if (!isValidPassword(user.getPassword()))
            return ValidationResult.INVALID_PASSWORD;
        
        if (!isValidEntity(user.getRole()))
            return ValidationResult.INVALID_ROLE;
        
        return ValidationResult.OK;
    }
    
    private static boolean hasEmptyFields(User user) {
        
        return isNullField(user.getLogin(), user.getPassword(), user.getRole())
                || isEmptyString(user.getLogin(), user.getPassword());
    }
    
    private static boolean isValidEmail(String email) {
        return VALID_EMAIL_ADDRESS.matcher(email).find();
    }
    
    private static boolean isValidPassword(String password) {
        return VALID_PASSWORD.matcher(password).find();
    }
}
