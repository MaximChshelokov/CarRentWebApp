package com.mv.schelokov.car_rent.model.db.repository;

import com.mv.schelokov.car_rent.model.db.repository.criteria.car_part.SelectAllParts;
import com.mv.schelokov.car_rent.model.db.repository.exceptions.DbException;
import com.mv.schelokov.car_rent.model.entities.CarPart;
import com.mv.schelokov.car_rent.model.entities.builders.CarPartBuilder;
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
public class CarPartRepositoryTest {
    
    private Connection connection;
    private CarPartRepository cpr;

    public CarPartRepositoryTest() {
    }
    
    @Before
    public void setUp() throws ClassNotFoundException, 
            InstantiationException, IllegalAccessException, SQLException {
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        connection = DriverManager.getConnection(
                "jdbc:mysql://localhost/car_rent_test?autoReconnect=true"
                        + "&useSSL=false&characterEncoding=utf-8",
                "car_rent_app", "Un3L41NoewVA");
        cpr = new CarPartRepository(connection);
    }
    
    @After
    public void tearDown() throws SQLException {
        connection.close();
    }

    @Test
    public void createNewCarPart() throws DbException {
        assertTrue(cpr.add(new CarPartBuilder()
                .setName("Переднее правое крыло")
                .getCarPart()));
    }
    
    @Test
    public void selectAllAndUpdateFirst() throws DbException {
        CarPart cp = cpr.read(new SelectAllParts()).get(0);
        cp.setName("Заднее левое крылышко");
        assertTrue(cpr.update(cp));
    }
    
    @Test
    public void selectAllAndDeleteLast() throws DbException {
        List<CarPart> cpl = cpr.read(new SelectAllParts());
        assertTrue(cpr.remove(cpl.get(cpl.size()-1)));
    }
    
}
