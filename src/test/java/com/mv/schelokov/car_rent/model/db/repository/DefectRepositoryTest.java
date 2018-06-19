package com.mv.schelokov.car_rent.model.db.repository;

import com.mv.schelokov.car_rent.model.db.repository.exceptions.DbException;
import com.mv.schelokov.car_rent.model.entities.Defect;
import com.mv.schelokov.car_rent.model.entities.builders.CarPartBuilder;
import com.mv.schelokov.car_rent.model.entities.builders.DefectBuilder;
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
public class DefectRepositoryTest {
    
    private Connection connection;
    private DefectRepository dr;

    public DefectRepositoryTest() {
    }
    
    @Before
    public void setUp() throws ClassNotFoundException, 
            InstantiationException, IllegalAccessException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        connection = DriverManager.getConnection(
                "jdbc:mysql://localhost/car_rent_test?autoReconnect=true"
                        + "&useSSL=false&characterEncoding=utf-8",
                "car_rent_app", "Un3L41NoewVA");
        dr = new DefectRepository(connection);
    }
    
    @After
    public void tearDown() throws SQLException {
        connection.close();
    }

    @Test
    public void createNewDefect() throws DbException {
        assertTrue(dr.add(new DefectBuilder()
                .setCar(6)
                .setCarPart(new CarPartBuilder().setId(1).getCarPart())
                .setDescription("Скол лакокрасочного покрытия 5 см^2")
                .setFixed(false)
                .getDefect()));
    }
    
    @Test
    public void findAllAndDeleteLast() throws DbException {
        List<Defect> dl = dr.read(DefectRepository.SELECT_ALL);
        assertTrue(dr.remove(dl.get(dl.size()-1)));
    }
    
    @Test
    public void findAllAndUpdateFirst() throws DbException {
        Defect defect = dr.read(DefectRepository.SELECT_ALL).get(0);
        defect.setDescription("Царапина 11 см");
        
        assertTrue(dr.update(defect));
    }    
}
