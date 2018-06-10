package com.mv.schelokov.car_rent.model.db.repository;

import com.mv.schelokov.car_rent.model.db.repository.exceptions.DbException;
import com.mv.schelokov.car_rent.model.db.repository.criteria.user.FindLogin;
import com.mv.schelokov.car_rent.model.db.repository.criteria.user.FindLoginPassword;
import com.mv.schelokov.car_rent.model.db.repository.criteria.user.SelectAllUsers;
import com.mv.schelokov.car_rent.model.entities.User;
import com.mv.schelokov.car_rent.model.entities.builders.UserBuilder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class UserRepositoryTest {
    
    private Connection connection;
    private UserRepository ur;
    
    public UserRepositoryTest() {
    }
    
    @Before
    public void setUp() throws SQLException, ClassNotFoundException, 
            InstantiationException, IllegalAccessException {
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        connection = DriverManager.getConnection(
                "jdbc:mysql://localhost/car_rent_test?autoReconnect=true&useSSL=false",
                "car_rent_app", "Un3L41NoewVA");
        ur = new UserRepository(connection);
    }

    @Test
    public void findLoginPasswordUser() throws DbException {
        
        List<User> ul = ur.read(new FindLoginPassword("admin", "admin"));
        assertEquals(ul.size(), 1);
    }
    
    @Test
    public void createNewUser() throws DbException {
        assertTrue(ur.add(new UserBuilder()
                .setLogin("Dronchik")
                .setPassword("228")
                .setRole("client").getUser()));
    }
    
    @Test
    public void findAndRemoveUser() throws DbException {
        List<User> ul = ur.read(new SelectAllUsers());
        assertTrue(ur.remove(ul.get(ul.size()-1)));
    }
    
    @Test
    public void findLoginPasswordUserNotFount() throws DbException {
        List<User> ul = ur.read(new FindLoginPassword("bogdan", "admin"));
        assertEquals(ul.size(), 0);
    }
    
    @Test
    public void findAndUpdateUser() throws DbException {
        List<User> ul = ur.read(new FindLogin("Dronchik"));
        ul.get(0).setPassword(Integer.toString(Integer.parseInt(ul.get(0).getPassword()) + 1));
        assertTrue(ur.update(ul.get(0)));
    }
    
    @After
    public void close() throws SQLException {
       connection.close();
    }
    
}
