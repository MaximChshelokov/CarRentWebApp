package com.mv.schelokov.carent.model.db.dao;

import com.mv.schelokov.carent.model.db.dao.factories.EntityFromResultSetFactory;
import com.mv.schelokov.carent.model.db.dao.interfaces.AbstractSqlDao;
import com.mv.schelokov.carent.model.db.dao.interfaces.Criteria;
import com.mv.schelokov.carent.model.db.dao.interfaces.SqlCriteria;
import com.mv.schelokov.carent.model.entity.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class UserDao extends AbstractSqlDao<User> {
    
    public interface ReadCriteria extends SqlCriteria {}

    public interface DeleteCriteria extends SqlCriteria {}

    public static final Criteria SELECT_ALL_CRITERIA = new SelectAllCriteria();

    public static class SelectAllCriteria implements ReadCriteria {
        private static final String QUERY = "SELECT user_id,login,password,"
                + "role_id,role_name FROM users_full";

        @Override
        public String toSqlQuery() {
            return QUERY;
        }

        @Override
        public void setStatement(PreparedStatement ps) throws SQLException {}
    }
    
    public static class FindByIdCriteria extends SelectAllCriteria {
        private static final String QUERY = " WHERE user_id=?";
        private static final int ID_COLUMN = 1;
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
            ps.setInt(ID_COLUMN, this.id);
        } 
    }
    
    public static class FindLoginCriteria extends SelectAllCriteria {

        private static final String QUERY = " WHERE login=?";
        private static final int LOGIN_COLUMN = 1;
        private final String login;

        public FindLoginCriteria(String login) {
            this.login = login;
        }

        @Override
        public String toSqlQuery() {
            return super.toSqlQuery().concat(QUERY);
        }

        @Override
        public void setStatement(PreparedStatement ps) throws SQLException {
            ps.setString(LOGIN_COLUMN, login);
        }
    }
    
    public static class FindLoginPasswordCriteria extends FindLoginCriteria {

        private static final String QUERY = " AND password=?";
        private static final int PASSWORD_COLUMN = 2;
        private final String password;

        public FindLoginPasswordCriteria(String login, String password) {
            super(login);
            this.password = password;
        }
        
        public FindLoginPasswordCriteria(User user) {
            this(user.getLogin(), user.getPassword());
        }

        @Override
        public String toSqlQuery() {
            return super.toSqlQuery().concat(QUERY);
        }

        @Override
        public void setStatement(PreparedStatement ps) throws SQLException {
            super.setStatement(ps);
            ps.setString(PASSWORD_COLUMN, password);
        }
    }
    
    private static final String CREATE_QUERY = "INSERT INTO users (login,"
            + "password,role) VALUES (?,?,?)";
    private static final String REMOVE_QUERY = "DELETE FROM users WHERE"
            + " user_id=?";
    private static final String UPDATE_QUERY = "UPDATE users SET login=?,"
            + "password=?,role=? WHERE user_id=?";
    
    /**
     * The Field enum has column names for read methods and number of column for
     * the update method and the add method (in the NUMBER attribute)
     */
    enum Fields {
        USER_ID(4), LOGIN(1), PASSWORD(2), ROLE(3), ROLE_NAME;
        
        int NUMBER;
        
        Fields(int number) {
            this.NUMBER = number;
        }
        Fields() {
        }
    }

    public UserDao(Connection connection) {
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
    protected User createItem(ResultSet rs) throws SQLException {
        return new EntityFromResultSetFactory(rs).createUser();
    }

    @Override
    protected void setStatement(PreparedStatement ps, User item, 
            boolean isUpdateStatement) throws SQLException {
        ps.setString(Fields.LOGIN.NUMBER, item.getLogin());
        ps.setString(Fields.PASSWORD.NUMBER, item.getPassword());
        ps.setInt(Fields.ROLE.NUMBER, item.getRole().getId());
        if (isUpdateStatement)
            ps.setInt(Fields.USER_ID.NUMBER, item.getId());
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
