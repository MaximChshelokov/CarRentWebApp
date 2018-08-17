package com.mv.schelokov.carent.model.validators;

import com.mv.schelokov.carent.model.entity.Car;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Pattern;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class CarValidator extends Validator {
    
    private final Car car;
    private static final int MIN_YEAR = 1970;
    private static final int MIN_PRICE = 1000;
    private static final int MAX_PRICE = 1_000_000;
    private static final Pattern VALID_LICENSE_PLATE = Pattern.compile("^[0-9]"
            + "{3}[A-Z]{3}[0-9]{2}$", Pattern.CASE_INSENSITIVE);
    private static final Pattern VALID_MODEL_NAME = Pattern.compile("^[A-Z0-9\\"
            + "s\\.-]{1,}$", Pattern.CASE_INSENSITIVE);
    private static final Pattern VALID_MAKE_NAME = Pattern.compile("^[A-Z0-9-]"
            + "{3,}$", Pattern.CASE_INSENSITIVE);
    
    public CarValidator(Car car) {
        this.car = car;
    }
    
    @Override
    public int validate() {

        if (hasEmptyFields()) {
            return ValidationResult.EMPTY_FIELD;
        }
        
        if (!isValidLicensePlate())
            return ValidationResult.INVALID_LICENSE_PLATE;

        if (!isValidModel())
            return ValidationResult.INVALID_MODEL;
        
        if (!isValidMake())
            return ValidationResult.INVALID_MAKE;
        
        if (!isValidYear())
            return ValidationResult.INVALID_YEAR;
        
        if (!isValidPrice())
            return ValidationResult.INVALID_PRICE;

        return ValidationResult.OK;
    }

    private boolean hasEmptyFields() {

        return isNullField(car.getLicensePlate(), car.getCarModel())
                || isNullField(car.getCarModel().getName(), car.getCarModel().getCarMake())
                || car.getCarModel().getCarMake().getName() == null
                || isEmptyString(car.getLicensePlate(), car.getCarModel().getName(),
                        car.getCarModel().getCarMake().getName());
    }
    
    private boolean isValidYear() {
        Date date = new Date();
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        int currentYear = cal.get(Calendar.YEAR);
        return car.getYearOfMake() >= MIN_YEAR
                && car.getYearOfMake() <= currentYear;
    }
    
    private boolean isValidPrice() {
        return car.getPrice() >= MIN_PRICE && car.getPrice() <= MAX_PRICE;
    }
    
    private boolean isValidLicensePlate() {
        return VALID_LICENSE_PLATE.matcher(car.getLicensePlate()).find();
    }
    
    private boolean isValidModel() {
        return VALID_MODEL_NAME.matcher(car.getCarModel().getName()).find();
    }
    
    private boolean isValidMake() {
        return VALID_MAKE_NAME.matcher(car.getCarModel().getCarMake()
                .getName()).find();
    }
}
