package com.mv.schelokov.car_rent.model.db.repository;

import com.mv.schelokov.car_rent.model.db.dao.RentOrderDao;
import com.mv.schelokov.car_rent.model.db.dao.exceptions.DbException;
import com.mv.schelokov.car_rent.model.entity.RentOrder;
import com.mv.schelokov.car_rent.model.entity.builders.CarBuilder;
import com.mv.schelokov.car_rent.model.entity.builders.RentOrderBuilder;
import com.mv.schelokov.car_rent.model.entity.builders.UserBuilder;
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
public class RentOrderRepositoryTest {
    
    private Connection connection;
    private RentOrderDao ror;

    
    public RentOrderRepositoryTest() {
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
        assertNotEquals(0,ror.read(RentOrderDao.SELECT_ALL).size());
    }
    
    @Test
    public void createNewRentOrder() throws DbException {
        assertTrue(ror.add(new RentOrderBuilder()
                .setCar(new CarBuilder().setId(6).getCar())
                .setUser(new UserBuilder().setId(4).getUser())
                .setStartDate(new GregorianCalendar(2018, 5, 15).getTime())
                .setEndDate(new GregorianCalendar(2018, 5, 30).getTime())
                .getRentOrder()));
    }
    
    @Test 
    public void findAllAndDeleteLast() throws DbException {
        List<RentOrder> rol = ror.read(RentOrderDao.SELECT_ALL);
        assertTrue(ror.remove(rol.get(rol.size()-1)));
    }
    
    @Test
    public void findAllAndUpdateFirst() throws DbException {
        RentOrder order = ror.read(RentOrderDao.SELECT_ALL).get(0);
        order.setApprovedBy(new UserBuilder().setId(1).getUser());
        assertTrue(ror.update(order));
    }
    
}
