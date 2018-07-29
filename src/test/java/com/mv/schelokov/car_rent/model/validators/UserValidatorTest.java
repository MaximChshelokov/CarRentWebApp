package com.mv.schelokov.car_rent.model.validators;

import com.mv.schelokov.car_rent.model.entity.User;
import com.mv.schelokov.car_rent.model.entity.builders.RoleBuilder;
import com.mv.schelokov.car_rent.model.entity.builders.UserBuilder;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class UserValidatorTest {
    
    public UserValidatorTest() {
    }

    /**
     * Test of validate method, of class UserValidator.
     */
    @Test
    public void testValidateReturnOK() {
        User user = new UserBuilder()
                .setLogin("podkova@mail.ru")
                .setPassword("1337228")
                .setRole(new RoleBuilder().setId(1).getRole())
                .getUser();
        int expResult = ValidationResult.OK;
        int result = new UserValidator(user).validate();
        assertEquals(expResult, result);
    }
    
    @Test
    public void testValidateReturnInvalidEmail() {
        User user = new UserBuilder()
                .setLogin("podkovapail.ru")
                .setPassword("Sfv@341v_1")
                .setRole(new RoleBuilder().setId(1).getRole())
                .getUser();
        int expResult = ValidationResult.INVALID_EMAIL;
        int result = new UserValidator(user).validate();
        assertEquals(expResult, result);
    }
    
    @Test
    public void testValidateReturnInvalidPassword() {
        User user = new UserBuilder()
                .setLogin("podkova@mail.ru")
                .setPassword("мой пароль")
                .setRole(new RoleBuilder().setId(1).getRole())
                .getUser();
        int expResult = ValidationResult.INVALID_PASSWORD;
        int result = new UserValidator(user).validate();
        assertEquals(expResult, result);
    }
    
    @Test
    public void testValidateReturnEmpty() {
        User user = new User();
        int expResult = ValidationResult.EMPTY_FIELD;
        int result = new UserValidator(user).validate();
        assertEquals(expResult, result);
    }
}
