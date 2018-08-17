package com.mv.schelokov.carent.model.db.dao;

import com.mv.schelokov.carent.model.db.dao.exceptions.DbException;
import com.mv.schelokov.carent.model.entity.CarMake;
import com.mv.schelokov.carent.model.entity.builders.CarMakeBuilder;
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
public class CarMakeDaoTest {
    
    private Connection connection;
    private CarMakeDao mr;
    private final CarMake carMake = new CarMakeBuilder()
                .withName("Shkoda")
                .getCarMake();
    
    public CarMakeDaoTest() {
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
        mr = new CarMakeDao(connection);
    }
    
    @After
    public void tearDown() throws SQLException {
        connection.close();
    }
    
    @Test
    public void createNewCarMake() throws DbException {
        assertTrue(mr.add(carMake));
        mr.remove(getCarMake());
    }
    
    @Test
    public void updateCarMake() throws DbException {
        mr.add(carMake);
        CarMake mk = getCarMake();
        assertTrue(mr.update(mk));
        mr.remove(mk);
    }
    
    @Test
    public void deleteCarMake() throws DbException {
        mr.add(carMake);
        CarMake mk = getCarMake();
        assertTrue(mr.remove(mk));
    }
    
    private CarMake getCarMake() throws DbException {
        List<CarMake> cMakeList = mr.read(new CarMakeDao.FindNameCriteria(
                carMake.getName()));
        for (CarMake cMake : cMakeList)
            if (cMake.getName().equals(carMake.getName()))
                return cMake;
        return new CarMake();
    }
    
}
