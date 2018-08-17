package com.mv.schelokov.carent.model.validators;

import com.mv.schelokov.carent.model.entity.UserData;
import java.util.regex.Pattern;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class UserDataValidator extends Validator {
    
    private final UserData userData;
    private static final Pattern VALID_EMAIL_ADDRESS
            = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
                    Pattern.CASE_INSENSITIVE);
    private static final Pattern VALID_PHONE_NUMBER
            = Pattern.compile("^[0-9]{11}$");
    private static final Pattern VALID_NAME = Pattern.compile("^[А-ЯA-Z][а-яёa-z"
            + "А-ЯA-Z\\-]{0,}\\s[А-ЯA-Z][а-яёa-zА-ЯA-Z\\-]{1,}(\\s[А-ЯA-Z][а-яёa-"
            + "zА-ЯA-Z\\-]{1,})?$");
    private static final Pattern VALID_ADDRESS
            = Pattern.compile("^[а-яa-zА-ЯA-Z0-9,\\.\\s/]{10,}$");
    
    public UserDataValidator(UserData userData) {
        this.userData = userData;
    }
    
    @Override
    public int validate() {

        if (hasEmptyFields()) {
            return ValidationResult.EMPTY_FIELD;
        }

        if (!isValidPhone()) {
            return ValidationResult.INVALID_PHONE;
        }
        
        if (!isValidName()) {
            return ValidationResult.INVALID_NAME;
        }
        
        if (!isValidAddress())
            return ValidationResult.INVALID_ADDRESS;
        
        if (!isValidEmail())
            return ValidationResult.INVALID_EMAIL;

        return ValidationResult.OK;
    }

    private boolean hasEmptyFields() {

        return isNullField(userData.getName(), userData.getAddress(), 
                userData.getPhone(), userData.getUser())
                || userData.getUser().getLogin() == null
                || isEmptyString(userData.getName(), userData.getAddress(), 
                userData.getPhone(), userData.getUser().getLogin());
    }

    private boolean isValidPhone() {
        return VALID_PHONE_NUMBER.matcher(userData.getPhone()).find();
    }
    
    private boolean isValidName() {
        return VALID_NAME.matcher(userData.getName()).find();
    }
    
    private boolean isValidAddress() {
        return VALID_ADDRESS.matcher(userData.getAddress()).find();
    }
    
    private boolean isValidEmail() {
        return VALID_EMAIL_ADDRESS.matcher(
                userData.getUser().getLogin()).find();
    }
}
