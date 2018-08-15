package com.mv.schelokov.carent.model.validators;

import com.mv.schelokov.carent.model.entity.RentOrder;
import com.mv.schelokov.carent.model.entity.builders.CarBuilder;
import com.mv.schelokov.carent.model.entity.builders.RentOrderBuilder;
import com.mv.schelokov.carent.model.entity.builders.UserBuilder;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class RentOrderValidatorTest {
    private final Date todayDate = new Date();
    private final Date futureDate;
    private final Date pastDate;
    
    
    public RentOrderValidatorTest() {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(todayDate);
        int month =  gc.get(Calendar.MONTH);
        gc.set(Calendar.MONTH, month + 1);
        futureDate = gc.getTime();
        gc.set(Calendar.MONTH, month - 1);
        pastDate = gc.getTime();
    }

    /**
     * Test of validate method, of class RentOrderValidator.
     */
    
    @Test
    public void testValidateReturnOk() {
        RentOrder order = new RentOrderBuilder()
                .selectedCar(new CarBuilder().withId(1).getCar())
                .byUser(new UserBuilder().withId(1).getUser())
                .startsAtDate(todayDate)
                .endsByDate(futureDate)
                .getRentOrder();
        int expResult = ValidationResult.OK;
        int result = new RentOrderValidator(order).validate();
        assertEquals(expResult, result);
    }
    
    @Test
    public void testValidateReturnInvalidCar() {
        RentOrder order = new RentOrderBuilder()
                .selectedCar(new CarBuilder().withId(-1).getCar())
                .byUser(new UserBuilder().withId(1).getUser())
                .startsAtDate(todayDate)
                .endsByDate(futureDate)
                .getRentOrder();
        int expResult = ValidationResult.INVALID_CAR;
        int result = new RentOrderValidator(order).validate();
        assertEquals(expResult, result);
    }
    
    @Test
    public void testValidateReturnInvalidUser() {
        RentOrder order = new RentOrderBuilder()
                .selectedCar(new CarBuilder().withId(1).getCar())
                .byUser(new UserBuilder().withId(-1).getUser())
                .startsAtDate(todayDate)
                .endsByDate(futureDate)
                .getRentOrder();
        int expResult = ValidationResult.INVALID_USER;
        int result = new RentOrderValidator(order).validate();
        assertEquals(expResult, result);
    }
    
    @Test
    public void testValidateReturnInvalidDate() {
        RentOrder order = new RentOrderBuilder()
                .selectedCar(new CarBuilder().withId(1).getCar())
                .byUser(new UserBuilder().withId(1).getUser())
                .startsAtDate(pastDate)
                .endsByDate(futureDate)
                .getRentOrder();
        int expResult = ValidationResult.INVALID_DATE;
        int result = new RentOrderValidator(order).validate();
        assertEquals(expResult, result);
    }
    
    @Test
    public void testValidateReturnInvalidEndDate() {
        RentOrder order = new RentOrderBuilder()
                .selectedCar(new CarBuilder().withId(1).getCar())
                .byUser(new UserBuilder().withId(1).getUser())
                .startsAtDate(todayDate)
                .endsByDate(pastDate)
                .getRentOrder();
        int expResult = ValidationResult.INVALID_DATE;
        int result = new RentOrderValidator(order).validate();
        assertEquals(expResult, result);
    }
    
}
