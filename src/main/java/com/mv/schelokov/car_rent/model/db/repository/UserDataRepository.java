package com.mv.schelokov.car_rent.model.db.repository;

import com.mv.schelokov.car_rent.model.db.repository.exceptions.CriteriaMismatchException;
import com.mv.schelokov.car_rent.model.db.repository.interfaces.AbstractSqlRepository;
import com.mv.schelokov.car_rent.model.db.repository.interfaces.Criteria;
import com.mv.schelokov.car_rent.model.db.repository.interfaces.SqlCriteria;
import com.mv.schelokov.car_rent.model.entities.User;
import com.mv.schelokov.car_rent.model.entities.UserData;
import com.mv.schelokov.car_rent.model.entities.builders.UserDataBuilder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class UserDataRepository extends AbstractSqlRepository<UserData> {
    
    public interface ReadCriteria extends SqlCriteria {}

    public interface DeleteCriteria extends SqlCriteria {}

    public static class FindByUser implements ReadCriteria {
        private static final String QUERY = "SELECT * FROM users_data "
                + "WHERE userdata_id=?";
        private final int userId;
        private static final int USER_ID_COLUMN = 1;

        public FindByUser(User user) {
            this.userId = user.getId();
        }

        public FindByUser(int userDataId) {
            this.userId = userDataId;
        }

        @Override
        public String toSqlQuery() {
            return QUERY;
        }

        @Override
        public void setStatement(PreparedStatement ps) throws SQLException {
            ps.setInt(USER_ID_COLUMN, userId);
        }
    }
    
    private static final String CREATE_QUERY = "INSERT INTO users_data ("
            + "userdata_id,name,address,phone) VALUES (?,?,?,?)";
    private static final String REMOVE_QUERY = "DELETE FROM users_data WHERE"
            + " userdata_id=?";
    private static final String UPDATE_QUERY = "UPDATE users_data SET "
            + "name=?,address=?,phone=? WHERE userdata_id=?";
    
    /**
     * The Field enum has column names for read methods and number of column for
     * the update method and the add method (in the UPDATE attribute and the 
     * INSERT attribute)
     */
    enum Fields {
        USERDATA_ID(1, 4), NAME(2, 1), ADDRESS(3, 2), PHONE(4, 3);
        
        int INSERT, UPDATE;
        
        Fields(int insert, int update) {
            this.INSERT = insert;
            this.UPDATE = update;
        }
    }
    
    public UserDataRepository(Connection connection) {
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
    protected UserData createItem(ResultSet rs) throws SQLException {
        return new UserDataBuilder()
                .setId(rs.getInt(Fields.USERDATA_ID.name()))
                .setName(rs.getString(Fields.NAME.name()))
                .setAddress(rs.getString(Fields.ADDRESS.name()))
                .setPhone(rs.getString(Fields.PHONE.name()))
                .getUserData();
    }

    @Override
    protected void setStatement(PreparedStatement ps, UserData item, 
            boolean isUpdateStatement) throws SQLException {
        if (isUpdateStatement) {
            ps.setInt(Fields.USERDATA_ID.UPDATE, item.getId());
            ps.setString(Fields.NAME.UPDATE, item.getName());
            ps.setString(Fields.ADDRESS.UPDATE, item.getAddress());
            ps.setString(Fields.PHONE.UPDATE, item.getPhone());
        } else {
            ps.setInt(Fields.USERDATA_ID.INSERT, item.getId());
            ps.setString(Fields.NAME.INSERT, item.getName());
            ps.setString(Fields.ADDRESS.INSERT, item.getAddress());
            ps.setString(Fields.PHONE.INSERT, item.getPhone());          
        }
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
