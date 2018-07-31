package com.mv.schelokov.carent.model.validators;

import com.mv.schelokov.carent.model.validators.UserDataValidator;
import com.mv.schelokov.carent.model.validators.ValidationResult;
import com.mv.schelokov.carent.model.entity.User;
import com.mv.schelokov.carent.model.entity.UserData;
import com.mv.schelokov.carent.model.entity.builders.UserBuilder;
import com.mv.schelokov.carent.model.entity.builders.UserDataBuilder;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class UserDataValidatorTest {
    
    public UserDataValidatorTest() {
    }

    /**
     * Test of validate method, of class UserDataValidator.
     */
    @Test
    public void testValidateReturnOk() {
        UserData userData = new UserDataBuilder()
                .setName("Фёдор Семёнович Кружыль")
                .setAddress("г. Караганда, ул. Кривая, д. 20 кв 18")
                .setPhone("87019131415")
                .setUser(new UserBuilder().setLogin("semenitch@rambler.kz").getUser())
                .getUserData();
        int expResult = ValidationResult.OK;
        int result = new UserDataValidator(userData).validate();
        assertEquals(expResult, result);
    }
    
    @Test
    public void testValidateReturnInvalidPhone() {
        UserData userData = new UserDataBuilder()
                .setName("Фёдор Семёнович Кружыль")
                .setAddress("г. Караганда, ул. Кривая, д. 20 кв 18")
                .setPhone("8701913")
                .setUser(new UserBuilder().setLogin("semenitch@rambler.kz").getUser())
                .getUserData();
        int expResult = ValidationResult.INVALID_PHONE;
        int result = new UserDataValidator(userData).validate();
        assertEquals(expResult, result);
    }
    
    @Test
    public void testValidateReturnEmpty() {
        UserData userData = new UserData();
        int expResult = ValidationResult.EMPTY_FIELD;
        int result = new UserDataValidator(userData).validate();
        assertEquals(expResult, result);
    }
    
    @Test
    public void testValidateReturnInvalidName() {
        UserData userData = new UserDataBuilder()
                .setName("Федя")
                .setAddress("г. Караганда, ул. Кривая, д. 20 кв 18")
                .setPhone("87019131415")
                .setUser(new UserBuilder().setLogin("semenitch@rambler.kz").getUser())
                .getUserData();
        int expResult = ValidationResult.INVALID_NAME;
        int result = new UserDataValidator(userData).validate();
        assertEquals(expResult, result);
    }
    
    @Test
    public void testValidateReturnInvalidAddress() {
        UserData userData = new UserDataBuilder()
                .setName("Фёдор Семёнович Кружыль")
                .setAddress("улица")
                .setPhone("87019131415")
                .setUser(new UserBuilder().setLogin("semenitch@rambler.kz").getUser())
                .getUserData();
        int expResult = ValidationResult.INVALID_ADDRESS;
        int result = new UserDataValidator(userData).validate();
        assertEquals(expResult, result);
    }
    
    @Test
    public void testValidateReturnInvalidEmail() {
        UserData userData = new UserDataBuilder()
                .setName("Фёдор Семёнович Кружыль")
                .setAddress("г. Караганда, ул. Кривая, д. 20 кв 18")
                .setPhone("87019131415")
                .setUser(new UserBuilder().setLogin("beysembet").getUser())
                .getUserData();
        int expResult = ValidationResult.INVALID_EMAIL;
        int result = new UserDataValidator(userData).validate();
        assertEquals(expResult, result);
    }
}