package com.mv.schelokov.car_rent.model.services;

import com.mv.schelokov.car_rent.model.entities.User;
import com.mv.schelokov.car_rent.model.entities.builders.RoleBuilder;
import com.mv.schelokov.car_rent.model.entities.builders.UserBuilder;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class UserServiceTest {
    
    private UserService userService = new UserService();
    
    public UserServiceTest() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of RegisterNewUser method, of class UserService.
     */

    public void testRegisterNewUser() throws Exception {
        System.out.println("RegisterNewUser");
        User user = new UserBuilder().setLogin("admin").setPassword("admin")
                .setRole(new RoleBuilder().setId(1).getRole())
                .getUser();
        userService.registerNewUser(user);
        assertTrue(true);
    }

    /**
     * Test of getUserByCredentials method, of class UserService.
     */
    @Test
    public void testGetUserByCredentials() throws Exception {
        System.out.println("getUserByCredentials");
        String login = "admin";
        String password = "admin";
        List result = userService.getUserByCredentials(login, password);
        assertEquals(1, result.size());
    }
}
