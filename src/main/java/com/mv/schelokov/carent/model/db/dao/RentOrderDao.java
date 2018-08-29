package com.mv.schelokov.carent.model.db.dao;

import com.mv.schelokov.carent.model.db.dao.interfaces.AbstractSqlDao;
import com.mv.schelokov.carent.model.db.dao.interfaces.Criteria;
import com.mv.schelokov.carent.model.db.dao.interfaces.SqlCriteria;
import com.mv.schelokov.carent.model.entity.Car;
import com.mv.schelokov.carent.model.entity.RentOrder;
import com.mv.schelokov.carent.model.entity.builders.CarBuilder;
import com.mv.schelokov.carent.model.entity.builders.CarMakeBuilder;
import com.mv.schelokov.carent.model.entity.builders.CarModelBuilder;
import com.mv.schelokov.carent.model.entity.builders.RejectionReasonBuilder;
import com.mv.schelokov.carent.model.entity.builders.RentOrderBuilder;
import com.mv.schelokov.carent.model.entity.builders.UserBuilder;
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
public class RentOrderDao extends AbstractSqlDao<RentOrder> {
    
    public interface ReadCriteria extends SqlCriteria {}

    public interface DeleteCriteria extends SqlCriteria {}

    public static final Criteria SELECT_ALL_CRITERIA = new SelectAllCriteria();
    public static final Criteria ORDER_BY_APPROVED_CRITERIA =
            new OrderedByApprovedCriteria();
    public static final Criteria OPENED_ORDERS_CRITERIA =
            new OpenedOrdersCriteria();

    public static class SelectAllCriteria implements ReadCriteria {
        private static final String QUERY = "SELECT rent_id,start_date,"
                + "end_date,car,license_plate,year_of_make,price,model,"
                + "model_name,make,make_name,user,login,approved_by,"
                + "approver_login,available,reason FROM rent_orders_full";

        @Override
        public String toSqlQuery() {
            return QUERY;
        }

        @Override
        public void setStatement(PreparedStatement ps) throws SQLException {}
    }
    
    public static class OrderedByApprovedCriteria extends SelectAllCriteria {
        private static final String QUERY = " ORDER BY approved_by";
        
        @Override
        public String toSqlQuery() {
            return super.toSqlQuery().concat(QUERY);
        }
    }
    
    public static class OpenedOrdersCriteria extends SelectAllCriteria {
        private static final String QUERY = " WHERE available=b'0' AND"
                + " approved_by IS NOT NULL AND reason IS NULL";

        @Override
        public String toSqlQuery() {
            return super.toSqlQuery().concat(QUERY);
        }
    }
    
    public static class FindByIdCriteria extends SelectAllCriteria {
        private static final String QUERY = " WHERE rent_id=?";
        private static final int RENT_ID_INDEX = 1;
        private final int rentId;
        
        public FindByIdCriteria(int rentId) {
            this.rentId = rentId;
        }
        
        @Override
        public String toSqlQuery() {
            return super.toSqlQuery().concat(QUERY);
        }
        
        @Override
        public void setStatement(PreparedStatement ps) throws SQLException {
            ps.setInt(RENT_ID_INDEX, this.rentId);
        }
    }
    
    public static class FindByUserIdCriteria extends SelectAllCriteria {
        private static final String QUERY = " WHERE user=? ORDER BY start_date";
        private static final int USER_ID_INDEX = 1;
        private final int userId;

        public FindByUserIdCriteria(int userId) {
            this.userId = userId;
        }

        @Override
        public String toSqlQuery() {
            return super.toSqlQuery().concat(QUERY);
        }

        @Override
        public void setStatement(PreparedStatement ps) throws SQLException {
            ps.setInt(USER_ID_INDEX, this.userId);
        }
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
        APPROVED_BY(5), APPROVER_LOGIN, AVAILABLE, REASON;
        
        int NUMBER;
        
        Fields(int number) {
            this.NUMBER = number;
        }
        Fields() {
        }
    }

    public RentOrderDao(Connection connection) {
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
                .withId(rs.getInt(Fields.RENT_ID.name()))
                .startsAtDate(rs.getDate(Fields.START_DATE.name()))
                .endsByDate(rs.getDate(Fields.END_DATE.name()))
                .selectedCar(createCar(rs))
                .byUser(new UserBuilder()
                        .withId(rs.getInt(Fields.USER.name()))
                        .withLogin(rs.getString(Fields.LOGIN.name()))
                        .getUser())
                .approvedBy(new UserBuilder()
                        .withId(rs.getInt(Fields.APPROVED_BY.name()))
                        .withLogin(rs.getString(Fields.APPROVER_LOGIN.name()))
                        .getUser())
                .rejectedDueReason(new RejectionReasonBuilder()
                        .withId(rs.getInt(Fields.RENT_ID.name()))
                        .dueReason(rs.getString(Fields.REASON.name()))
                        .getRejectionReason())
                .getRentOrder();
    }
    
    private Car createCar(ResultSet rs) throws SQLException {
        return new CarBuilder()
                .withId(rs.getInt(Fields.CAR.name()))
                .withLicensePlate(rs.getString(Fields.LICENSE_PLATE.name()))
                .inYearOfMake(rs.getInt(Fields.YEAR_OF_MAKE.name()))
                .withPrice(rs.getInt(Fields.PRICE.name()))
                .withAvailability(rs.getBoolean(Fields.AVAILABLE.name()))
                .withModel(new CarModelBuilder()
                        .withId(rs.getInt(Fields.MODEL.name()))
                        .withName(rs.getString(Fields.MODEL_NAME.name()))
                        .withCarMake(new CarMakeBuilder()
                                .withId(rs.getInt(Fields.MAKE.name()))
                                .withName(rs.getString(Fields.MAKE_NAME.name()))
                                .getCarMake())
                        .getCarModel())
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
