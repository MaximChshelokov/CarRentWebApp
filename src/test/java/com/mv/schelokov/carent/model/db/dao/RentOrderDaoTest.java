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
    private final RentOrder rentOrder = new RentOrderBuilder()
            .selectedCar(new CarBuilder().withId(6).getCar())
            .byUser(new UserBuilder().withId(2).getUser())
            .startsAtDate(new GregorianCalendar(2018, 5, 15).getTime())
            .endsByDate(new GregorianCalendar(2018, 5, 30).getTime())
            .getRentOrder();

    
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
        connection.setAutoCommit(false);
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
        assertTrue(ror.add(rentOrder));
        ror.remove(getOrder());
    }
    
    @Test 
    public void deleteRentOrder() throws DbException {
        ror.add(rentOrder);
        assertTrue(ror.remove(getOrder()));
    }
    
    @Test
    public void updateRentOrder() throws DbException {
        ror.add(rentOrder);
        RentOrder order = getOrder();
        assertTrue(ror.update(order));
        ror.remove(order);
    }
    
    private RentOrder getOrder() throws DbException {
        List<RentOrder> roList = ror.read(RentOrderDao.SELECT_ALL_CRITERIA);
        for (RentOrder rOrder : roList)
            if (rOrder.getUser().getId() == rentOrder.getUser().getId())
                return rOrder;
        return new RentOrder();
    }
    
}
