package com.mv.schelokov.car_rent.model.validators;

import com.mv.schelokov.car_rent.model.entities.UserData;
import java.util.regex.Pattern;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class UserDataValidator extends Validator {
    
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
    
    public static int validate(UserData userData) {

        if (hasEmptyFields(userData)) {
            return ValidationResult.EMPTY_FIELD;
        }

        if (!isValidPhone(userData.getPhone())) {
            return ValidationResult.INVALID_PHONE;
        }
        
        if (!isValidName(userData.getName())) {
            return ValidationResult.INVALID_NAME;
        }
        
        if (!isValidAddress(userData.getAddress()))
            return ValidationResult.INVALID_ADDRESS;
        
        if (!isValidEmail(userData.getUser().getLogin()))
            return ValidationResult.INVALID_EMAIL;

        return ValidationResult.OK;
    }

    private static boolean hasEmptyFields(UserData userData) {

        return isNullField(userData.getName(), userData.getAddress(), 
                userData.getPhone(), userData.getUser())
                || userData.getUser().getLogin() == null
                || isEmptyString(userData.getName(), userData.getAddress(), 
                userData.getPhone(), userData.getUser().getLogin());
    }

    private static boolean isValidPhone(String phone) {
        return VALID_PHONE_NUMBER.matcher(phone).find();
    }
    
    private static boolean isValidName(String name) {
        return VALID_NAME.matcher(name).find();
    }
    
    private static boolean isValidAddress(String address) {
        return VALID_ADDRESS.matcher(address).find();
    }
    
    private static boolean isValidEmail(String email) {
        return VALID_EMAIL_ADDRESS.matcher(email).find();
    }
}
