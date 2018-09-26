package com.mv.schelokov.carent.model.db.dao;

import com.mv.schelokov.carent.model.db.dao.factories.EntityFromResultSetFactory;
import com.mv.schelokov.carent.model.db.dao.interfaces.AbstractSqlDao;
import com.mv.schelokov.carent.model.db.dao.interfaces.Criteria;
import com.mv.schelokov.carent.model.db.dao.interfaces.SqlCriteria;
import com.mv.schelokov.carent.model.entity.RentOrder;
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
                + "end_date,car_id,license_plate,year_of_make,price,model_id,"
                + "model_name,make_id,make_name,user_id,login,approved_by,"
                + "approver_login,available,reason_id, reason FROM "
                + "rent_orders_full";

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
        return new EntityFromResultSetFactory(rs).createRentOrder();
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
    protected boolean isReadCriteriaInstance(Criteria criteria) {
        return criteria instanceof ReadCriteria;
    }

    @Override
    protected boolean isDeleteCriteriaInstance(Criteria criteria) {
        return criteria instanceof DeleteCriteria;
    }
}
