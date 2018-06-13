package com.mv.schelokov.car_rent.model.db.repository;

import com.mv.schelokov.car_rent.model.db.repository.exceptions.DbException;
import com.mv.schelokov.car_rent.model.db.repository.criteria.user.FindLogin;
import com.mv.schelokov.car_rent.model.db.repository.criteria.user.FindLoginPassword;
import com.mv.schelokov.car_rent.model.db.repository.criteria.user.SelectAllUsers;
import com.mv.schelokov.car_rent.model.entities.User;
import com.mv.schelokov.car_rent.model.entities.builders.RoleBuilder;
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
        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        connection = DriverManager.getConnection(
                "jdbc:mysql://localhost/car_rent_test?autoReconnect=true"
                        + "&useSSL=false&characterEncoding=utf-8",
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
                .setRole(new RoleBuilder()
                        .setId(2)
                        .getRole()).getUser()));
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
    public void findLoginAndUpdateUser() throws DbException {
        User user = ur.read(new FindLogin("Dronchik")).get(0);
        user.setPassword(Integer.toString(Integer.parseInt(user.getPassword()) + 1));
        assertTrue(ur.update(user));
    }
    
    @After
    public void close() throws SQLException {
       connection.close();
    }
    
}
