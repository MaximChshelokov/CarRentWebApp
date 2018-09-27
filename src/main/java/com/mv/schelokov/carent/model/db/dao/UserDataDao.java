package com.mv.schelokov.carent.model.db.dao;

import com.mv.schelokov.carent.model.db.dao.factories.EntityFromResultSetFactory;
import com.mv.schelokov.carent.model.db.dao.interfaces.AbstractSqlDao;
import com.mv.schelokov.carent.model.db.dao.interfaces.Criteria;
import com.mv.schelokov.carent.model.db.dao.interfaces.SqlCriteria;
import com.mv.schelokov.carent.model.entity.User;
import com.mv.schelokov.carent.model.entity.UserData;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class UserDataDao extends AbstractSqlDao<UserData> {
    
    public interface ReadCriteria extends SqlCriteria {}

    public interface DeleteCriteria extends SqlCriteria {}
    
    public static final SelectAllCriteria SELECT_ALL_CRITERIA =
            new SelectAllCriteria();
    
    public static class SelectAllCriteria implements ReadCriteria {
        private static final String QUERY = "SELECT login,password,userdata_id,"
                + "name,address,phone,role_id,role_name,user_id FROM "
                + "users_data_full";
        
        @Override
        public String toSqlQuery() {
            return QUERY;
        }

        @Override
        public void setStatement(PreparedStatement ps) throws SQLException {}
    }

    public static class FindByUserCriteria extends SelectAllCriteria {
        private static final String QUERY = " WHERE user_id=?";
        private final int userId;
        private static final int USER_ID_COLUMN = 1;

        public FindByUserCriteria(User user) {
            this.userId = user.getId();
        }

        public FindByUserCriteria(int userDataId) {
            this.userId = userDataId;
        }

        @Override
        public String toSqlQuery() {
            return super.toSqlQuery().concat(QUERY);
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

    private static final int INSERT_USERDATA_ID = 1;
    private static final int INSERT_NAME = 2;
    private static final int INSERT_ADDRESS = 3;
    private static final int INSERT_PHONE = 4;
    private static final int UPDATE_USERDATA_ID = 4;
    private static final int UPDATE_NAME = 1;
    private static final int UPDATE_ADDRESS = 2;
    private static final int UPDATE_PHONE = 3;
    
    public UserDataDao(Connection connection) {
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
        return new EntityFromResultSetFactory(rs).createUserData();
    }
    
    @Override
    protected void setInsertStatement(PreparedStatement ps, UserData item)
            throws SQLException {
        ps.setInt(INSERT_USERDATA_ID, item.getUser().getId());
        ps.setString(INSERT_NAME,item.getName());
        ps.setString(INSERT_ADDRESS, item.getAddress());
        ps.setString(INSERT_PHONE, item.getPhone());
    }

    @Override
    protected void setUpdateStatement(PreparedStatement ps, UserData item)
            throws SQLException {
        ps.setInt(UPDATE_USERDATA_ID, item.getId());
        ps.setString(UPDATE_NAME, item.getName());
        ps.setString(UPDATE_ADDRESS, item.getAddress());
        ps.setString(UPDATE_PHONE, item.getPhone());
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
