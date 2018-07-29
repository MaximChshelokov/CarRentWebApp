package com.mv.schelokov.car_rent.model.validators;

import com.mv.schelokov.car_rent.model.entity.RentOrder;
import com.mv.schelokov.car_rent.model.entity.builders.CarBuilder;
import com.mv.schelokov.car_rent.model.entity.builders.RentOrderBuilder;
import com.mv.schelokov.car_rent.model.entity.builders.UserBuilder;
import java.util.Date;
import java.util.GregorianCalendar;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class RentOrderValidatorTest {
    
    public RentOrderValidatorTest() {
    }

    /**
     * Test of validate method, of class RentOrderValidator.
     */
    @Test
    public void testValidateReturnOk() {
        RentOrder order = new RentOrderBuilder()
                .setCar(new CarBuilder().setId(1).getCar())
                .setUser(new UserBuilder().setId(1).getUser())
                .setStartDate(new Date())
                .setEndDate(new GregorianCalendar(2018, 7, 10).getTime())
                .getRentOrder();
        int expResult = ValidationResult.OK;
        int result = new RentOrderValidator(order).validate();
        assertEquals(expResult, result);
    }
    
    @Test
    public void testValidateReturnInvalidCar() {
        RentOrder order = new RentOrderBuilder()
                .setCar(new CarBuilder().setId(-1).getCar())
                .setUser(new UserBuilder().setId(1).getUser())
                .setStartDate(new Date())
                .setEndDate(new GregorianCalendar(2018, 7, 10).getTime())
                .getRentOrder();
        int expResult = ValidationResult.INVALID_CAR;
        int result = new RentOrderValidator(order).validate();
        assertEquals(expResult, result);
    }
    
    @Test
    public void testValidateReturnInvalidUser() {
        RentOrder order = new RentOrderBuilder()
                .setCar(new CarBuilder().setId(1).getCar())
                .setUser(new UserBuilder().setId(-1).getUser())
                .setStartDate(new Date())
                .setEndDate(new GregorianCalendar(2018, 7, 10).getTime())
                .getRentOrder();
        int expResult = ValidationResult.INVALID_USER;
        int result = new RentOrderValidator(order).validate();
        assertEquals(expResult, result);
    }
    
    @Test
    public void testValidateReturnInvalidDate() {
        RentOrder order = new RentOrderBuilder()
                .setCar(new CarBuilder().setId(1).getCar())
                .setUser(new UserBuilder().setId(1).getUser())
                .setStartDate(new GregorianCalendar(2017, 6, 21).getTime())
                .setEndDate(new GregorianCalendar(2018, 7, 10).getTime())
                .getRentOrder();
        int expResult = ValidationResult.INVALID_DATE;
        int result = new RentOrderValidator(order).validate();
        assertEquals(expResult, result);
    }
    
    @Test
    public void testValidateReturnInvalidEndDate() {
        RentOrder order = new RentOrderBuilder()
                .setCar(new CarBuilder().setId(1).getCar())
                .setUser(new UserBuilder().setId(1).getUser())
                .setStartDate(new GregorianCalendar(2019, 7, 10).getTime())
                .setEndDate(new GregorianCalendar(2018, 7, 10).getTime())
                .getRentOrder();
        int expResult = ValidationResult.INVALID_DATE;
        int result = new RentOrderValidator(order).validate();
        assertEquals(expResult, result);
    }
    
}
