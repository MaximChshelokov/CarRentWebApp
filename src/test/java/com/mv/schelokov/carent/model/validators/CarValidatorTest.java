package com.mv.schelokov.carent.model.validators;

import com.mv.schelokov.carent.model.entity.Car;
import com.mv.schelokov.carent.model.entity.builders.CarBuilder;
import com.mv.schelokov.carent.model.entity.builders.CarMakeBuilder;
import com.mv.schelokov.carent.model.entity.builders.CarModelBuilder;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class CarValidatorTest {
    
    public CarValidatorTest() {
    }

    /**
     * Test of validate method, of class CarValidator.
     */
    @Test
    public void testValidateReturnOk() {
        Car car = new CarBuilder()
                .withLicensePlate("123ASM09")
                .withPrice(1500)
                .inYearOfMake(2005)
                .withModel(new CarModelBuilder()
                        .withName("Granta")
                        .withCarMake(new CarMakeBuilder().withName("Lada").getCarMake())
                        .getCarModel())
                .getCar();
        int expResult = ValidationResult.OK;
        int result = new CarValidator(car).validate();
        assertEquals(expResult, result);
    }
    
    @Test
    public void testValidateReturnInvalidePlate() {
        Car car = new CarBuilder()
                .withLicensePlate("KAZ228AK")
                .withPrice(1500)
                .inYearOfMake(2005)
                .withModel(new CarModelBuilder()
                        .withName("Granta")
                        .withCarMake(new CarMakeBuilder().withName("Lada").getCarMake())
                        .getCarModel())
                .getCar();
        int expResult = ValidationResult.INVALID_LICENSE_PLATE;
        int result = new CarValidator(car).validate();
        assertEquals(expResult, result);
    }
    
    @Test
    public void testValidateReturnInvalidPrice() {
        Car car = new CarBuilder()
                .withLicensePlate("123ASM09")
                .withPrice(300)
                .inYearOfMake(2005)
                .withModel(new CarModelBuilder()
                        .withName("Granta")
                        .withCarMake(new CarMakeBuilder().withName("Lada").getCarMake())
                        .getCarModel())
                .getCar();
        int expResult = ValidationResult.INVALID_PRICE;
        int result = new CarValidator(car).validate();
        assertEquals(expResult, result);
    }
    
    @Test
    public void testValidateReturnInvalidYear() {
        Car car = new CarBuilder()
                .withLicensePlate("123ASM09")
                .withPrice(1500)
                .inYearOfMake(2050)
                .withModel(new CarModelBuilder()
                        .withName("Granta")
                        .withCarMake(new CarMakeBuilder().withName("Lada").getCarMake())
                        .getCarModel())
                .getCar();
        int expResult = ValidationResult.INVALID_YEAR;
        int result = new CarValidator(car).validate();
        assertEquals(expResult, result);
    }
    
    @Test
    public void testValidateReturnInvalidModel() {
        Car car = new CarBuilder()
                .withLicensePlate("123ASM09")
                .withPrice(1500)
                .inYearOfMake(2005)
                .withModel(new CarModelBuilder()
                        .withName("+%$Granta")
                        .withCarMake(new CarMakeBuilder().withName("Lada").getCarMake())
                        .getCarModel())
                .getCar();
        int expResult = ValidationResult.INVALID_MODEL;
        int result = new CarValidator(car).validate();
        assertEquals(expResult, result);
    }
    
    @Test
    public void testValidateReturnInvalidMake() {
        Car car = new CarBuilder()
                .withLicensePlate("123ASM09")
                .withPrice(1500)
                .inYearOfMake(2005)
                .withModel(new CarModelBuilder()
                        .withName("Granta")
                        .withCarMake(new CarMakeBuilder().withName("@#$234").getCarMake())
                        .getCarModel())
                .getCar();
        int expResult = ValidationResult.INVALID_MAKE;
        int result = new CarValidator(car).validate();
        assertEquals(expResult, result);
    }
    
    @Test
    public void testValidateReturnEmpty() {
        Car car = new Car();
        int expResult = ValidationResult.EMPTY_FIELD;
        int result = new CarValidator(car).validate();
        assertEquals(expResult, result);
    }
    
}
