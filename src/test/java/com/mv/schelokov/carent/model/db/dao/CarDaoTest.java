package com.mv.schelokov.carent.model.db.dao;

import com.mv.schelokov.carent.model.db.dao.exceptions.DbException;
import com.mv.schelokov.carent.model.entity.Car;
import com.mv.schelokov.carent.model.entity.builders.CarBuilder;
import com.mv.schelokov.carent.model.entity.builders.CarModelBuilder;
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
public class CarDaoTest {
    
    private Connection connection;
    private CarDao cr;
    private static final String LICENSE_PLATE_NUMBER = "228cum09";
    private final Car car = new CarBuilder()
                .withLicensePlate(LICENSE_PLATE_NUMBER)
                .inYearOfMake(2009)
                .withPrice(6000)
                .withModel(new CarModelBuilder()
                        .withId(1)
                        .getCarModel())
                .getCar();
    
    public CarDaoTest() {
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
        cr = new CarDao(connection);
    }
    
    @After
    public void tearDown() throws SQLException {
        connection.close();
    }

    @Test
    public void createNewCar() throws DbException {
        assertTrue(cr.add(car));
        cr.remove(getCar());
    }
    
    @Test
    public void selectAllAndDeleteLast() throws DbException {
        
        cr.add(car);
        assertTrue(cr.remove(getCar()));        
    }
    
    @Test
    public void updateCar() throws DbException {
        cr.add(car);
        Car updCar = getCar();
        assertTrue(cr.update(updCar));                
        cr.remove(updCar);
    }
    
    @Test
    public void findCarById() throws DbException {
        assertEquals(1, cr.read(new CarDao.FindByIdCriteria(1)).size());
    }
    
    private Car getCar() throws DbException {
        List<Car> carList = cr.read(CarDao.SELECT_ALL_CRITERIA);
        for (Car car : carList)
            if (car.getLicensePlate().equals(LICENSE_PLATE_NUMBER))
                return car;
        return new Car();
    }
}
