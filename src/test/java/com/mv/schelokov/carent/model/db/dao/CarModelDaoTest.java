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
public class CarModelDaoTest {

    private Connection connection;
    private CarModelDao mr;
    private final CarModel carModel = new CarModelBuilder()
            .withName("Corola")
            .withCarMake(new CarMakeBuilder()
                    .withId(1)
                    .getCarMake())
            .getCarModel();

    public CarModelDaoTest() {
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
        mr = new CarModelDao(connection);
    }

    @After
    public void tearDown() throws SQLException {
        connection.close();
    }

    @Test
    public void createNewModel() throws DbException {
        assertTrue(mr.add(carModel));

        mr.remove(getCarModel());
    }

    @Test
    public void updateCarModel() throws DbException {
        mr.add(carModel);
        CarModel cm = getCarModel();
        assertTrue(mr.update(cm));
        mr.remove(cm);
    }

    @Test
    public void deleteCarModel() throws DbException {
        mr.add(carModel);
        CarModel cm = getCarModel();
        assertTrue(mr.remove(cm));
    }

    private CarModel getCarModel() throws DbException {
        List<CarModel> cModelList = mr.read(new CarModelDao.FindModelCriteria(
                carModel));
        for (CarModel cModel : cModelList) {
            if (cModel.getName().equals(carModel.getName())) {
                return cModel;
            }
        }
        return new CarModel();
    }

}
