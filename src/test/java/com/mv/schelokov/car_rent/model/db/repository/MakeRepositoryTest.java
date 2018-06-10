package com.mv.schelokov.car_rent.model.db.repository;

import com.mv.schelokov.car_rent.model.db.repository.exceptions.DbException;
import com.mv.schelokov.car_rent.model.db.repository.make_criteria.SelectAllMake;
import com.mv.schelokov.car_rent.model.entities.Make;
import com.mv.schelokov.car_rent.model.entities.builders.MakeBuilder;
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
    private MakeRepository mr;
    
    public MakeRepositoryTest() {
    }
    
    @Before
    public void setUp() throws ClassNotFoundException, 
            InstantiationException, IllegalAccessException, SQLException {
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        connection = DriverManager.getConnection(
                "jdbc:mysql://localhost/car_rent_test?autoReconnect=true"
                        + "&useSSL=false&characterEncoding=utf-8",
                "car_rent_app", "Un3L41NoewVA");
        mr = new MakeRepository(connection);
    }
    
    @After
    public void tearDown() throws SQLException {
        connection.close();
    }
    
    @Test
    public void createNewMake() throws DbException {
        assertTrue(mr.add(new MakeBuilder()
                .setName("Shkoda")
                .getMake()));
    }
    
    @Test
    public void selectAllAndUpdateMake() throws DbException {
        Make mk = mr.read(new SelectAllMake()).get(0);
        mk.setName(mk.getName() + "ta");
        assertTrue(mr.update(mk));
    }
    
    @Test
    public void deleteLastMake() throws DbException {
        List<Make> ml = mr.read(new SelectAllMake());
        assertTrue(mr.remove(ml.get(ml.size()-1)));
    }
    
}
