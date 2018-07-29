package com.mv.schelokov.car_rent.model.db.repository;

import com.mv.schelokov.car_rent.model.db.dao.MakeDao;
import com.mv.schelokov.car_rent.model.db.dao.exceptions.DbException;
import com.mv.schelokov.car_rent.model.entity.CarMake;
import com.mv.schelokov.car_rent.model.entity.builders.CarMakeBuilder;
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
public class MakeRepositoryTest {
    
    private Connection connection;
    private MakeDao mr;
    
    public MakeRepositoryTest() {
    }
    
    @Before
    public void setUp() throws ClassNotFoundException, 
            InstantiationException, IllegalAccessException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        connection = DriverManager.getConnection(
                "jdbc:mysql://localhost/car_rent_test?autoReconnect=true"
                        + "&useSSL=false&characterEncoding=utf-8",
                "car_rent_app", "Un3L41NoewVA");
        mr = new MakeDao(connection);
    }
    
    @After
    public void tearDown() throws SQLException {
        connection.close();
    }
    
    @Test
    public void createNewMake() throws DbException {
        assertTrue(mr.add(new CarMakeBuilder()
                .setName("Shkoda")
                .getCarMake()));
    }
    
    @Test
    public void selectAllAndUpdateMake() throws DbException {
        CarMake mk = mr.read(MakeDao.SELECT_ALL).get(0);
        mk.setName("Toyota");
        assertTrue(mr.update(mk));
    }
    
    @Test
    public void deleteLastMake() throws DbException {
        List<CarMake> ml = mr.read(MakeDao.SELECT_ALL);
        assertTrue(mr.remove(ml.get(ml.size()-1)));
    }
    
}
