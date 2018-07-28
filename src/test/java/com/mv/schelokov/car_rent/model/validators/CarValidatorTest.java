package com.mv.schelokov.car_rent.model.validators;

import com.mv.schelokov.car_rent.model.entities.Car;
import com.mv.schelokov.car_rent.model.entities.builders.CarBuilder;
import com.mv.schelokov.car_rent.model.entities.builders.CarMakeBuilder;
import com.mv.schelokov.car_rent.model.entities.builders.CarModelBuilder;
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
                .setLicensePlate("123ASM09")
                .setPrice(1500)
                .setYearOfMake(2005)
                .setModel(new CarModelBuilder()
                        .setName("Granta")
                        .setCarMake(new CarMakeBuilder().setName("Lada").getCarMake())
                        .getCarModel())
                .getCar();
        int expResult = ValidationResult.OK;
        int result = CarValidator.validate(car);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testValidateReturnInvalidePlate() {
        Car car = new CarBuilder()
                .setLicensePlate("KAZ228AK")
                .setPrice(1500)
                .setYearOfMake(2005)
                .setModel(new CarModelBuilder()
                        .setName("Granta")
                        .setCarMake(new CarMakeBuilder().setName("Lada").getCarMake())
                        .getCarModel())
                .getCar();
        int expResult = ValidationResult.INVALID_LICENSE_PLATE;
        int result = CarValidator.validate(car);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testValidateReturnInvalidPrice() {
        Car car = new CarBuilder()
                .setLicensePlate("123ASM09")
                .setPrice(300)
                .setYearOfMake(2005)
                .setModel(new CarModelBuilder()
                        .setName("Granta")
                        .setCarMake(new CarMakeBuilder().setName("Lada").getCarMake())
                        .getCarModel())
                .getCar();
        int expResult = ValidationResult.INVALID_PRICE;
        int result = CarValidator.validate(car);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testValidateReturnInvalidYear() {
        Car car = new CarBuilder()
                .setLicensePlate("123ASM09")
                .setPrice(1500)
                .setYearOfMake(2050)
                .setModel(new CarModelBuilder()
                        .setName("Granta")
                        .setCarMake(new CarMakeBuilder().setName("Lada").getCarMake())
                        .getCarModel())
                .getCar();
        int expResult = ValidationResult.INVALID_YEAR;
        int result = CarValidator.validate(car);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testValidateReturnInvalidModel() {
        Car car = new CarBuilder()
                .setLicensePlate("123ASM09")
                .setPrice(1500)
                .setYearOfMake(2005)
                .setModel(new CarModelBuilder()
                        .setName("+%$Granta")
                        .setCarMake(new CarMakeBuilder().setName("Lada").getCarMake())
                        .getCarModel())
                .getCar();
        int expResult = ValidationResult.INVALID_MODEL;
        int result = CarValidator.validate(car);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testValidateReturnInvalidMake() {
        Car car = new CarBuilder()
                .setLicensePlate("123ASM09")
                .setPrice(1500)
                .setYearOfMake(2005)
                .setModel(new CarModelBuilder()
                        .setName("Granta")
                        .setCarMake(new CarMakeBuilder().setName("@#$234").getCarMake())
                        .getCarModel())
                .getCar();
        int expResult = ValidationResult.INVALID_MAKE;
        int result = CarValidator.validate(car);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testValidateReturnEmpty() {
        Car car = new Car();
        int expResult = ValidationResult.EMPTY_FIELD;
        int result = CarValidator.validate(car);
        assertEquals(expResult, result);
    }
    
}
