package com.mv.schelokov.car_rent.model.validators;

import com.mv.schelokov.car_rent.model.entities.Car;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Pattern;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class CarValidator extends Validator {
    
    private static final int MIN_YEAR = 1970;
    private static final int MIN_PRICE = 1000;
    private static final int MAX_PRICE = 1_000_000;
    private static final Pattern VALID_LICENSE_PLATE = Pattern.compile("^[0-9]"
            + "{3}[A-Z]{3}[0-9]{2}$", Pattern.CASE_INSENSITIVE);
    private static final Pattern VALID_MODEL_NAME = Pattern.compile("^[A-Z0-9\\"
            + "s\\.-]{1,}$", Pattern.CASE_INSENSITIVE);
    private static final Pattern VALID_MAKE_NAME = Pattern.compile("^[A-Z0-9-]"
            + "{3,}$", Pattern.CASE_INSENSITIVE);
    
    public static int validate(Car car) {

        if (hasEmptyFields(car)) {
            return ValidationResult.EMPTY_FIELD;
        }
        
        if (!isValidLicensePlate(car.getLicensePlate()))
            return ValidationResult.INVALID_LICENSE_PLATE;

        if (!isValidModel(car.getModel().getName()))
            return ValidationResult.INVALID_MODEL;
        
        if (!isValidMake(car.getModel().getMake().getName()))
            return ValidationResult.INVALID_MAKE;
        
        if (!isValidYear(car.getYearOfMake()))
            return ValidationResult.INVALID_YEAR;
        
        if (!isValidPrice(car.getPrice()))
            return ValidationResult.INVALID_PRICE;

        return ValidationResult.OK;
    }

    private static boolean hasEmptyFields(Car car) {

        return isNullField(car.getLicensePlate(), car.getModel())
                || isNullField(car.getModel().getName(), car.getModel().getMake())
                || car.getModel().getMake().getName() == null
                || isEmptyString(car.getLicensePlate(), car.getModel().getName(),
                        car.getModel().getMake().getName());
    }
    
    private static boolean isValidYear(int year) {
        Date date = new Date();
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        int currentYear = cal.get(Calendar.YEAR);
        return year >= MIN_YEAR && year <= currentYear;
    }
    
    private static boolean isValidPrice(int price) {
        return price >= MIN_PRICE && price <= MAX_PRICE;
    }
    
    private static boolean isValidLicensePlate(String licensePlate) {
        return VALID_LICENSE_PLATE.matcher(licensePlate).find();
    }
    
    private static boolean isValidModel(String model) {
        return VALID_MODEL_NAME.matcher(model).find();
    }
    
    private static boolean isValidMake(String makes) {
        return VALID_MAKE_NAME.matcher(makes).find();
    }
}
