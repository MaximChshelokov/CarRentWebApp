package com.mv.schelokov.carent.model.db.dao;

import com.mv.schelokov.carent.model.db.dao.factories.EntityFromResultSetFactory;
import com.mv.schelokov.carent.model.db.dao.interfaces.AbstractSqlDao;
import com.mv.schelokov.carent.model.db.dao.interfaces.Criteria;
import com.mv.schelokov.carent.model.db.dao.interfaces.SqlCriteria;
import com.mv.schelokov.carent.model.entity.Car;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class CarDao extends AbstractSqlDao<Car> {
    
    public interface ReadCriteria extends SqlCriteria {}
    
    public interface DeleteCriteria extends SqlCriteria {}
    
    public static final Criteria SELECT_ALL_CRITERIA = new SelectAllCriteria();
    public static final Criteria SELECT_AVAILABLE_CRITERIA = 
            new SelectAvailableCriteria();
    
    public static class SelectAllCriteria implements ReadCriteria {

        private static final String QUERY = "SELECT car_id,license_plate,"
                + "year_of_make,price,model_id,model_name,make_id,make_name,"
                + "available FROM cars_full";

        @Override
        public String toSqlQuery() {
            return QUERY;
        }

        @Override
        public void setStatement(PreparedStatement ps) throws SQLException {}
    }
    
    public static class SelectAvailableCriteria extends SelectAllCriteria {
        private static final String QUERY = " WHERE available=b'1'";
        @Override
        public String toSqlQuery() {
            return super.toSqlQuery().concat(QUERY);
        }
    }
    
    public static class FindByIdCriteria extends SelectAllCriteria {

        private static final String QUERY = " WHERE car_id=?";
        private static final int CAR_ID = 1;

        private final int id;

        public FindByIdCriteria(int id) {
            this.id = id;
        }

        @Override
        public String toSqlQuery() {
            return super.toSqlQuery().concat(QUERY);
        }

        @Override
        public void setStatement(PreparedStatement ps) throws SQLException {
            ps.setInt(CAR_ID, id);
        }
    }
    
    private static final String CREATE_QUERY = "INSERT INTO cars (model_id,"
            + "license_plate,year_of_make,price,available) VALUES (?,?,?,?,?)";
    private static final String REMOVE_QUERY = "DELETE FROM cars WHERE"
            + " car_id=?";
    private static final String UPDATE_QUERY = "UPDATE cars SET model_id=?,"
            + "license_plate=?,year_of_make=?,price=?,available=? "
            + "WHERE car_id=?";
    
    private static final int CAR_ID = 6;
    private static final int LICENSE_PLATE = 2;
    private static final int YEAR_OF_MAKE = 3;
    private static final int PRICE = 4;
    private static final int MODEL = 1;
    private static final int AVAILABLE = 5;
    
    public CarDao(Connection connection) {
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
        return new EntityFromResultSetFactory(rs).createCar();
    }
    
    @Override
    protected void setInsertStatement(PreparedStatement ps, Car item)
            throws SQLException {
        ps.setInt(MODEL, item.getCarModel().getId());
        ps.setString(LICENSE_PLATE, item.getLicensePlate());
        ps.setInt(YEAR_OF_MAKE, item.getYearOfMake());
        ps.setInt(PRICE, item.getPrice());
        ps.setBoolean(AVAILABLE, item.isAvailable()); 
    }

    @Override
    protected void setUpdateStatement(PreparedStatement ps, Car item)
            throws SQLException {
        setInsertStatement(ps, item);
        ps.setInt(CAR_ID, item.getId());
    }

    @Override
    protected boolean isReadCriteriaInstance(Criteria criteria) {
        return criteria instanceof ReadCriteria;
    }
    
    @Override
    protected boolean isDeleteCriteriaInstance(Criteria criteria) {
        return criteria instanceof DeleteCriteria;
    }
}
