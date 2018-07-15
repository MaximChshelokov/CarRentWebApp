package com.mv.schelokov.car_rent.model.db.repository;

import com.mv.schelokov.car_rent.model.db.repository.exceptions.DbException;
import com.mv.schelokov.car_rent.model.entities.Car;
import com.mv.schelokov.car_rent.model.entities.builders.CarBuilder;
import com.mv.schelokov.car_rent.model.entities.builders.ModelBuilder;
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
    private CarRepository cr;
    
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
        cr = new CarRepository(connection);
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
                .setModel(new ModelBuilder()
                        .setId(1)
                        .getModel())
                .getCar()));
    }
    
    @Test
    public void selectAllAndDeleteLast() throws DbException {
        List<Car> cl = cr.read(CarRepository.SELECT_ALL);
        Car carToDelete = new Car();
        for (Car car : cl)
            if (car.getId() > carToDelete.getId())
                carToDelete = car;
        assertTrue(cr.remove(carToDelete));        
    }
    
    @Test
    public void updateCar() throws DbException {
        Car car = cr.read(CarRepository.SELECT_ALL).get(0);
        car.setPrice(car.getPrice() + 1);
        assertTrue(cr.update(car));                
    }
    
    @Test
    public void findCarById() throws DbException {
        assertEquals(1, cr.read(new CarRepository.FindById(1)).size());
    }
}
