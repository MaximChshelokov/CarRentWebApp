package com.mv.schelokov.carent.model.validators;

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
                .withName("Фёдор Семёнович Кружыль")
                .residentAtAddress("г. Караганда, ул. Кривая, д. 20 кв 18")
                .withPhone("87019131415")
                .withUser(new UserBuilder().withLogin("semenitch@rambler.kz").getUser())
                .getUserData();
        int expResult = ValidationResult.OK;
        int result = new UserDataValidator(userData).validate();
        assertEquals(expResult, result);
    }
    
    @Test
    public void testValidateReturnInvalidPhone() {
        UserData userData = new UserDataBuilder()
                .withName("Фёдор Семёнович Кружыль")
                .residentAtAddress("г. Караганда, ул. Кривая, д. 20 кв 18")
                .withPhone("8701913")
                .withUser(new UserBuilder().withLogin("semenitch@rambler.kz").getUser())
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
                .withName("Федя")
                .residentAtAddress("г. Караганда, ул. Кривая, д. 20 кв 18")
                .withPhone("87019131415")
                .withUser(new UserBuilder().withLogin("semenitch@rambler.kz").getUser())
                .getUserData();
        int expResult = ValidationResult.INVALID_NAME;
        int result = new UserDataValidator(userData).validate();
        assertEquals(expResult, result);
    }
    
    @Test
    public void testValidateReturnInvalidAddress() {
        UserData userData = new UserDataBuilder()
                .withName("Фёдор Семёнович Кружыль")
                .residentAtAddress("улица")
                .withPhone("87019131415")
                .withUser(new UserBuilder().withLogin("semenitch@rambler.kz").getUser())
                .getUserData();
        int expResult = ValidationResult.INVALID_ADDRESS;
        int result = new UserDataValidator(userData).validate();
        assertEquals(expResult, result);
    }
    
    @Test
    public void testValidateReturnInvalidEmail() {
        UserData userData = new UserDataBuilder()
                .withName("Фёдор Семёнович Кружыль")
                .residentAtAddress("г. Караганда, ул. Кривая, д. 20 кв 18")
                .withPhone("87019131415")
                .withUser(new UserBuilder().withLogin("beysembet").getUser())
                .getUserData();
        int expResult = ValidationResult.INVALID_EMAIL;
        int result = new UserDataValidator(userData).validate();
        assertEquals(expResult, result);
    }
}
