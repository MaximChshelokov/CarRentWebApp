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
        udr = new UserDataDao(connection);
    }

    @Test
    public void createNewUserData() throws DbException {
        assertTrue(udr.add(new UserDataBuilder()
                .withName("Эдуард Робертович Михтельштейн")
                .residentAtAddress("ул. Победы, д. 22, кв 50")
                .withPhone("+77087653492")
                .withUser(new UserBuilder()
                        .withId(19)
                        .getUser())
                .getUserData()));
    }
    
    @Test
    public void findByUserEntity() throws DbException {
        User user = new UserBuilder().withId(4).getUser();
        List<UserData> udl = udr.read(new UserDataDao.FindByUserCriteria(user));
        assertEquals(1, udl.size());
    }
    
    @Test
    public void findAndUpdate() throws DbException {
        UserData ud = udr.read(new UserDataDao.FindByUserCriteria(3)).get(0);
        ud.setPhone(Long.toString(Long.parseLong(ud.getPhone()) - 1L));
        assertTrue(udr.update(ud));        
    }
    
    @Test
    public void findAndDeleteById() throws DbException {
        List<UserData> udl = udr.read(new UserDataDao.FindByUserCriteria(19));
        assertTrue(udr.remove(udl.get(0)));
    }
    
    @After
    public void close() throws SQLException {
        connection.close();
    }
}
