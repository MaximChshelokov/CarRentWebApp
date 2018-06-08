package com.mv.schelokov.car_rent.model.db.repository;

import com.mv.schelokov.car_rent.model.db.repository.exceptions.DbException;
import com.mv.schelokov.car_rent.model.db.repository.user_data_criteria.FindByUser;
import com.mv.schelokov.car_rent.model.entities.User;
import com.mv.schelokov.car_rent.model.entities.UserData;
import com.mv.schelokov.car_rent.model.entities.builders.UserBuilder;
import com.mv.schelokov.car_rent.model.entities.builders.UserDataBuilder;
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
public class UserDataRepositoryTest {
    
    private Connection connection;
    private UserDataRepository udr;
    
    public UserDataRepositoryTest() {
    }
    
    @Before
    public void setUp() throws ClassNotFoundException, 
            InstantiationException, IllegalAccessException, SQLException {
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        connection = DriverManager.getConnection(
                "jdbc:mysql://localhost/car_rent_test?autoReconnect=true"
                        + "&useSSL=false&characterEncoding=utf-8",
                "car_rent_app", "Un3L41NoewVA");
        udr = new UserDataRepository(connection);
    }

    @Test
    public void createNewUserData() throws DbException {
        assertTrue(udr.add(new UserDataBuilder()
        .setName("Эдуард Робертович Михтельштейн")
        .setAddress("ул. Победы, д. 22, кв 50")
        .setPhone("+77087653492")
        .getUserData()));
    }
    
    @Test
    public void findByUserEntity() throws DbException {
        User user = new UserBuilder().setUserData(4).getUser();
        List<UserData> udl = udr.read(new FindByUser(user));
        assertEquals(1, udl.size());
    }
    
    @Test
    public void findAndDeleteById() throws DbException {
        List<UserData> udl = udr.read(new FindByUser(6));
        assertTrue(udr.remove(udl.get(0)));
    }
    
    @After
    public void close() throws SQLException {
        connection.close();
    }
}
