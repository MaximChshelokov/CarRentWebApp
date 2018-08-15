package com.mv.schelokov.carent.model.db.dao;

import com.mv.schelokov.carent.model.db.dao.exceptions.DbException;
import com.mv.schelokov.carent.model.entity.CarModel;
import com.mv.schelokov.carent.model.entity.builders.CarMakeBuilder;
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
public class ModelDaoTest {
    
    private Connection connection;
    private ModelDao mr;
    
    public ModelDaoTest() {
    }
    
    @Before
    public void setUp() throws ClassNotFoundException, 
            InstantiationException, IllegalAccessException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        connection = DriverManager.getConnection(
                "jdbc:mysql://localhost/car_rent_test?autoReconnect=true"
                        + "&useSSL=false&characterEncoding=utf-8",
                "car_rent_app", "Un3L41NoewVA");
        mr = new ModelDao(connection);
    }
    
    @After
    public void tearDown() throws SQLException {
        connection.close();
    }

    @Test
    public void createNewModel() throws DbException {
        assertTrue(mr.add(new CarModelBuilder()
                .withName("Corola")
                .withCarMake(new CarMakeBuilder()
                        .withId(1)
                        .getCarMake())
                .getCarModel()));
    }
    
    @Test
    public void selectAllModelAndUpdateOne() throws DbException {
        CarModel model = mr.read(new ModelDao.SelectAllCriteria()).get(2);
        model.setName("Corola");
        assertTrue(mr.update(model));
    }
    
    @Test
    public void selectAllAndDeleteLast() throws DbException {
        List<CarModel> ml = mr.read(new ModelDao.SelectAllCriteria());
        CarModel modelToDelete = new CarModel();
        for (CarModel model : ml)
            if (model.getId() > modelToDelete.getId())
                modelToDelete = model;
        assertTrue(mr.remove(modelToDelete));
    }
    
}
