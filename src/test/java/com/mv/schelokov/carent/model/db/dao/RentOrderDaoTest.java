package com.mv.schelokov.carent.model.db.dao;

import com.mv.schelokov.carent.model.db.dao.exceptions.DbException;
import com.mv.schelokov.carent.model.entity.RentOrder;
import com.mv.schelokov.carent.model.entity.builders.CarBuilder;
import com.mv.schelokov.carent.model.entity.builders.RentOrderBuilder;
import com.mv.schelokov.carent.model.entity.builders.UserBuilder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.GregorianCalendar;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class RentOrderDaoTest {
    
    private Connection connection;
    private RentOrderDao ror;

    
    public RentOrderDaoTest() {
    }
    
    @Before
    public void setUp() throws ClassNotFoundException, 
            InstantiationException, IllegalAccessException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        connection = DriverManager.getConnection(
                "jdbc:mysql://localhost/car_rent_test?autoReconnect=true"
                        + "&useSSL=false&characterEncoding=utf-8",
                "car_rent_app", "Un3L41NoewVA");
        ror = new RentOrderDao(connection);
    }
    
    @After
    public void tearDown() throws SQLException {
        connection.close();
    }

    @Test
    public void findAllRentOrders() throws DbException {
        assertNotEquals(0,ror.read(RentOrderDao.SELECT_ALL_CRITERIA).size());
    }
    
    @Test
    public void createNewRentOrder() throws DbException {
        assertTrue(ror.add(new RentOrderBuilder()
                .selectedCar(new CarBuilder().withId(6).getCar())
                .byUser(new UserBuilder().withId(4).getUser())
                .startsAtDate(new GregorianCalendar(2018, 5, 15).getTime())
                .endsByDate(new GregorianCalendar(2018, 5, 30).getTime())
                .getRentOrder()));
    }
    
    @Test 
    public void findAllAndDeleteLast() throws DbException {
        List<RentOrder> rol = ror.read(RentOrderDao.SELECT_ALL_CRITERIA);
        assertTrue(ror.remove(rol.get(rol.size()-1)));
    }
    
    @Test
    public void findAllAndUpdateFirst() throws DbException {
        RentOrder order = ror.read(RentOrderDao.SELECT_ALL_CRITERIA).get(0);
        order.setApprovedBy(new UserBuilder().withId(1).getUser());
        assertTrue(ror.update(order));
    }
    
}
