package com.mv.schelokov.car_rent.model.db.repository;

import com.mv.schelokov.car_rent.model.db.repository.car_criteria.CarDeleteCriteria;
import com.mv.schelokov.car_rent.model.db.repository.car_criteria.CarReadCriteria;
import com.mv.schelokov.car_rent.model.db.repository.exceptions.CriteriaMismatchException;
import com.mv.schelokov.car_rent.model.db.repository.interfaces.AbstractSqlRepository;
import com.mv.schelokov.car_rent.model.db.repository.interfaces.Criteria;
import com.mv.schelokov.car_rent.model.entities.Car;
import com.mv.schelokov.car_rent.model.entities.builders.CarBuilder;
import com.mv.schelokov.car_rent.model.entities.builders.MakeBuilder;
import com.mv.schelokov.car_rent.model.entities.builders.ModelBuilder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class CarRepository extends AbstractSqlRepository<Car> {
    
    private static final String CREATE_QUERY = "INSERT INTO cars (model,"
            + "license_plate,year_of_make,price) VALUES (?,?,?,?)";
    private static final String REMOVE_QUERY = "DELETE FROM cars WHERE"
            + " car_id=?";
    private static final String UPDATE_QUERY = "UPDATE cars SET model=?,"
            + "license_plate=?,year_of_make=?,price=? WHERE car_id=?";
    
    public CarRepository(Connection connection) {
        super(connection);
    }

        @Override
    protected String getCreateQuery() {
        return CREATE_QUERY;
    }

    @Override
    protected String getRemoveQuery() {
        return REMOVE_QUERY;
    }

    @Override
    protected String getUpdateQuery() {
        return UPDATE_QUERY;
    }

    @Override
    protected Car createItem(ResultSet rs) throws SQLException {
        return new CarBuilder()
                .setId(rs.getInt(1))
                .setLicensePlate(rs.getString(2))
                .setYearOfMake(rs.getInt(3))
                .setPrice(rs.getInt(4))
                .setModel(new ModelBuilder()
                        .setId(rs.getInt(5))
                        .setName(rs.getString(6))
                        .setMake(new MakeBuilder()
                                .setId(rs.getInt(7))
                                .setName(rs.getString(8))
                                .getMake())
                        .getModel())
                .getCar();
    }

    @Override
    protected void setStatement(PreparedStatement ps, Car item, 
            boolean isUpdateStatement) throws SQLException {
        int i = 1;
        ps.setInt(i++, item.getModel().getId());
        ps.setString(i++, item.getLicensePlate());
        ps.setInt(i++, item.getYearOfMake());
        ps.setInt(i++, item.getPrice());
        if (isUpdateStatement)
            ps.setInt(i, item.getId());
    }

    @Override
    protected boolean checkCriteriaInstance(Criteria criteria, 
            boolean isDeleteCriteria) throws CriteriaMismatchException {
        if (isDeleteCriteria) {
            if (criteria instanceof CarDeleteCriteria)
                return true;
        } else if (criteria instanceof CarReadCriteria)
            return true;
        throw new CriteriaMismatchException();
    }
    
}
