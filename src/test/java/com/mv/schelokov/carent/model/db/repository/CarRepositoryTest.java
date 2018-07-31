package com.mv.schelokov.carent.model.db.repository;

import com.mv.schelokov.carent.model.db.dao.CarDao;
import com.mv.schelokov.carent.model.db.dao.exceptions.DbException;
import com.mv.schelokov.carent.model.entity.Car;
import com.mv.schelokov.carent.model.entity.builders.CarBuilder;
import com.mv.schelokov.carent.model.entity.builders.CarModelBuilder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class CarRepositoryTest {
    
    private Connection connection;
    private CarDao cr;
    
    public CarRepositoryTest() {
    }
    
    @Before
    public void setUp() throws SQLException, ClassNotFoundException, 
            InstantiationException, IllegalAccessException {
        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        connection = DriverManager.getConnection(
                "jdbc:mysql://localhost/car_rent_test?autoReconnect=true"
                        + "&useSSL=false&characterEncoding=utf-8",
                "car_rent_app", "Un3L41NoewVA");
        cr = new CarDao(connection);
    }
    
    @After
    public void tearDown() throws SQLException {
        connection.close();
    }

    @Test
    public void createNewCar() throws DbException {
        assertTrue(cr.add(new CarBuilder()
                .setLicensePlate("228cum09")
                .setYearOfMake(2009)
                .setPrice(6000)
                .setModel(new CarModelBuilder()
                        .setId(1)
                        .getCarModel())
                .getCar()));
    }
    
    @Test
    public void selectAllAndDeleteLast() throws DbException {
        List<Car> cl = cr.read(CarDao.SELECT_ALL_CRITERIA);
        Car carToDelete = new Car();
        for (Car car : cl)
            if (car.getId() > carToDelete.getId())
                carToDelete = car;
        assertTrue(cr.remove(carToDelete));        
    }
    
    @Test
    public void updateCar() throws DbException {
        Car car = cr.read(CarDao.SELECT_ALL_CRITERIA).get(0);
        car.setPrice(car.getPrice() + 1);
        assertTrue(cr.update(car));                
    }
    
    @Test
    public void findCarById() throws DbException {
        assertEquals(1, cr.read(new CarDao.FindByIdCriteria(1)).size());
    }
}
