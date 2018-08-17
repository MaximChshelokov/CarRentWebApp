package com.mv.schelokov.carent.model.db.dao;

import com.mv.schelokov.carent.model.db.dao.exceptions.DbException;
import com.mv.schelokov.carent.model.entity.User;
import com.mv.schelokov.carent.model.entity.builders.RoleBuilder;
import com.mv.schelokov.carent.model.entity.builders.UserBuilder;
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
public class UserDaoTest {
    
    private Connection connection;
    private UserDao ur;
    private final User user = new UserBuilder()
            .withLogin("Dronchik")
            .withPassword("228")
            .withRole(new RoleBuilder()
                    .withId(2)
                    .getRole()).getUser();
    
    public UserDaoTest() {
    }
    
    @Before
    public void setUp() throws SQLException, ClassNotFoundException, 
            InstantiationException, IllegalAccessException {
        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        connection = DriverManager.getConnection(
                "jdbc:mysql://localhost/car_rent_test?autoReconnect=true"
                        + "&useSSL=false&characterEncoding=utf-8",
                "car_rent_app", "Un3L41NoewVA");
        connection.setAutoCommit(false);
        ur = new UserDao(connection);
    }

    @Test
    public void findLoginPasswordUser() throws DbException {
        ur.add(user);
        List<User> ul = ur.read(new UserDao.FindLoginPasswordCriteria(user));
        assertEquals(1, ul.size());
        ur.remove(getUser());
    }
    
    @Test
    public void createNewUser() throws DbException {
        assertTrue(ur.add(user));
        ur.remove(getUser());
    }
    
    @Test
    public void removeUser() throws DbException {
        ur.add(user);
        assertTrue(ur.remove(getUser()));
    }
    
    @Test
    public void findLoginPasswordUserNotFount() throws DbException {
        List<User> ul = ur.read(new UserDao.FindLoginPasswordCriteria("bogdan", 
                "admin"));
        assertEquals(ul.size(), 0);
    }
    
    @Test
    public void updateUser() throws DbException {
        ur.add(user);
        User userWithId = getUser();
        assertTrue(ur.update(userWithId));
        ur.remove(userWithId);
    }
    
    @After
    public void close() throws SQLException {
       connection.close();
    }
    
    private User getUser() throws DbException {
        List<User> ul = ur.read(new UserDao.FindLoginPasswordCriteria(user));
        if (ul.isEmpty())
            return new User();
        return ul.get(0);
    }
    
}
