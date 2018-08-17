package com.mv.schelokov.carent.model.db.dao;

import com.mv.schelokov.carent.model.db.dao.exceptions.DbException;
import com.mv.schelokov.carent.model.entity.User;
import com.mv.schelokov.carent.model.entity.UserData;
import com.mv.schelokov.carent.model.entity.builders.UserBuilder;
import com.mv.schelokov.carent.model.entity.builders.UserDataBuilder;
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
public class UserDataDaoTest {
    
    private Connection connection;
    private UserDataDao udr;
    private final UserData userData = new UserDataBuilder()
            .withName("Эдуард Робертович Михтельштейн")
            .residentAtAddress("ул. Победы, д. 22, кв 50")
            .withPhone("+77087653492")
            .withUser(new UserBuilder()
                    .withId(19)
                    .getUser())
            .getUserData();
    
    public UserDataDaoTest() {
    }
    
    @Before
    public void setUp() throws ClassNotFoundException, 
            InstantiationException, IllegalAccessException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        connection = DriverManager.getConnection(
                "jdbc:mysql://localhost/car_rent_test?autoReconnect=true"
                        + "&useSSL=false&characterEncoding=utf-8",
                "car_rent_app", "Un3L41NoewVA");
        connection.setAutoCommit(false);
        udr = new UserDataDao(connection);
    }

    @Test
    public void createNewUserData() throws DbException {
        assertTrue(udr.add(userData));
        udr.remove(getUserData());
    }
    
    @Test
    public void findByUserEntity() throws DbException {
        udr.add(userData);
        User user = userData.getUser();
        List<UserData> udl = udr.read(new UserDataDao.FindByUserCriteria(user));
        assertEquals(1, udl.size());
        udr.remove(udl.get(0));
    }
    
    @Test
    public void updateUserData() throws DbException {
        udr.add(userData);
        UserData ud = getUserData();
        assertTrue(udr.update(ud));
        udr.remove(ud);
    }
    
    @Test
    public void findAndDeleteById() throws DbException {
        udr.add(userData);
        assertTrue(udr.remove(getUserData()));
    }
    
    @After
    public void close() throws SQLException {
        connection.close();
    }
    
    private UserData getUserData() throws DbException {
        List<UserData> udl = udr.read(new UserDataDao.FindByUserCriteria(
                userData.getUser().getId()));
        if (udl.isEmpty())
            return new UserData();
        return udl.get(0);
    }
}
