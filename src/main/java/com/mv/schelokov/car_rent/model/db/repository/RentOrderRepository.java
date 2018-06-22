package com.mv.schelokov.car_rent.model.db.repository;

import com.mv.schelokov.car_rent.model.db.repository.exceptions.CriteriaMismatchException;
import com.mv.schelokov.car_rent.model.db.repository.interfaces.AbstractSqlRepository;
import com.mv.schelokov.car_rent.model.db.repository.interfaces.Criteria;
import com.mv.schelokov.car_rent.model.db.repository.interfaces.SqlCriteria;
import com.mv.schelokov.car_rent.model.entities.Car;
import com.mv.schelokov.car_rent.model.entities.RentOrder;
import com.mv.schelokov.car_rent.model.entities.builders.CarBuilder;
import com.mv.schelokov.car_rent.model.entities.builders.MakeBuilder;
import com.mv.schelokov.car_rent.model.entities.builders.ModelBuilder;
import com.mv.schelokov.car_rent.model.entities.builders.RentOrderBuilder;
import com.mv.schelokov.car_rent.model.entities.builders.UserBuilder;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class RentOrderRepository extends AbstractSqlRepository<RentOrder> {
    
    public interface ReadCriteria extends SqlCriteria {}

    public interface DeleteCriteria extends SqlCriteria {}

    public static final Criteria SELECT_ALL = new SelectAll();

    public static class SelectAll implements ReadCriteria {
        private static final String QUERY = "SELECT rent_id,start_date,"
                + "end_date,car,license_plate,year_of_make,price,model,"
                + "model_name,make,make_name,user,login,approved_by,"
                + "approver_login FROM rent_orders_full";

        @Override
        public String toSqlQuery() {
            return QUERY;
        }

        @Override
        public void setStatement(PreparedStatement ps) throws SQLException {}
    }
    
    private static final String CREATE_QUERY = "INSERT INTO rent_orders (car,"
            + "user,start_date, end_date, approved_by) VALUES (?,?,?,?,?)";
    private static final String REMOVE_QUERY = "DELETE FROM rent_orders WHERE"
            + " rent_id=?";
    private static final String UPDATE_QUERY = "UPDATE rent_orders SET car=?,"
            + "user=?,start_date=?,end_date=?,approved_by=? WHERE rent_id=?";
    
    /**
     * The Field enum has column names for read methods and number of column for
     * the update method and the add method (in the NUMBER attribute)
     */
    enum Fields {
        RENT_ID(6), START_DATE(3), END_DATE(4), CAR(1), LICENSE_PLATE, 
        YEAR_OF_MAKE, PRICE, MODEL, MODEL_NAME, MAKE, MAKE_NAME, USER(2), LOGIN, 
        APPROVED_BY(5), APPROVER_LOGIN;
        
        int NUMBER;
        
        Fields(int number) {
            this.NUMBER = number;
        }
        Fields() {
        }
    }

    public RentOrderRepository(Connection connection) {
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
    protected RentOrder createItem(ResultSet rs) throws SQLException {
        return new RentOrderBuilder()
                .setId(rs.getInt(Fields.RENT_ID.name()))
                .setStartDate(rs.getDate(Fields.START_DATE.name()))
                .setEndDate(rs.getDate(Fields.END_DATE.name()))
                .setCar(createCar(rs))
                .setUser(new UserBuilder()
                        .setId(rs.getInt(Fields.USER.name()))
                        .setLogin(rs.getString(Fields.LOGIN.name()))
                        .getUser())
                .setApprovedBy(new UserBuilder()
                        .setId(rs.getInt(Fields.APPROVED_BY.name()))
                        .setLogin(rs.getString(Fields.APPROVER_LOGIN.name()))
                        .getUser())
                .getRentOrder();
    }
    
    private Car createCar(ResultSet rs) throws SQLException {
        return new CarBuilder()
                .setId(rs.getInt(Fields.CAR.name()))
                .setLicensePlate(rs.getString(Fields.LICENSE_PLATE.name()))
                .setYearOfMake(rs.getInt(Fields.YEAR_OF_MAKE.name()))
                .setPrice(rs.getInt(Fields.PRICE.name()))
                .setModel(new ModelBuilder()
                        .setId(rs.getInt(Fields.MODEL.name()))
                        .setName(rs.getString(Fields.MODEL_NAME.name()))
                        .setMake(new MakeBuilder()
                                .setId(rs.getInt(Fields.MAKE.name()))
                                .setName(rs.getString(Fields.MAKE_NAME.name()))
                                .getMake())
                        .getModel())
                .getCar();
    }
    
    @Override
    protected void setStatement(PreparedStatement ps, RentOrder item, 
            boolean isUpdateStatement) throws SQLException {
        ps.setInt(Fields.CAR.NUMBER, item.getCar().getId());
        ps.setInt(Fields.USER.NUMBER, item.getUser().getId());
        ps.setDate(Fields.START_DATE.NUMBER, new Date(item.getStartDate().getTime()));
        ps.setDate(Fields.END_DATE.NUMBER, new Date(item.getEndDate().getTime()));
        if (item.getApprovedBy() == null || item.getApprovedBy().getId() == 0)
            ps.setNull(Fields.APPROVED_BY.NUMBER, Types.INTEGER);
        else
            ps.setInt(Fields.APPROVED_BY.NUMBER, item.getApprovedBy().getId());
        if (isUpdateStatement)
            ps.setInt(Fields.RENT_ID.NUMBER, item.getId());
    }

    @Override
    protected boolean checkCriteriaInstance(Criteria criteria, 
            boolean isDeleteCriteria) {
        if (isDeleteCriteria) {
            if (criteria instanceof DeleteCriteria)
                return true;
        } else if (criteria instanceof ReadCriteria)
            return true;
        return false;
    }
}
