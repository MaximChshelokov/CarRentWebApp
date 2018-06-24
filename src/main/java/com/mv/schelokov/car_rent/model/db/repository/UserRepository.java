package com.mv.schelokov.car_rent.model.db.repository;

import com.mv.schelokov.car_rent.model.db.repository.exceptions.CriteriaMismatchException;
import com.mv.schelokov.car_rent.model.db.repository.interfaces.AbstractSqlRepository;
import com.mv.schelokov.car_rent.model.db.repository.interfaces.Criteria;
import com.mv.schelokov.car_rent.model.db.repository.interfaces.SqlCriteria;
import com.mv.schelokov.car_rent.model.entities.User;
import com.mv.schelokov.car_rent.model.entities.builders.RoleBuilder;
import com.mv.schelokov.car_rent.model.entities.builders.UserBuilder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class UserRepository extends AbstractSqlRepository<User> {
    
    public interface ReadCriteria extends SqlCriteria {}

    public interface DeleteCriteria extends SqlCriteria {}

    public static final Criteria SELECT_ALL = new SelectAll();

    public static class SelectAll implements ReadCriteria {
        private static final String QUERY = "SELECT user_id,login,password,"
                + "role,role_name FROM users_full";

        @Override
        public String toSqlQuery() {
            return QUERY;
        }

        @Override
        public void setStatement(PreparedStatement ps) throws SQLException {}
    }
    
    public static class FindLogin extends SelectAll {

        private static final String QUERY = " WHERE login=?";
        private static final int LOGIN_COLUMN = 1;
        private final String login;

        public FindLogin(String login) {
            this.login = login;
        }

        @Override
        public String toSqlQuery() {
            return super.toSqlQuery() + QUERY;
        }

        @Override
        public void setStatement(PreparedStatement ps) throws SQLException {
            ps.setString(LOGIN_COLUMN, login);
        }
    }
    
    public static class FindLoginPassword extends FindLogin {

        private static final String QUERY = " AND password=?";
        private static final int PASSWORD_COLUMN = 2;
        private final String password;

        public FindLoginPassword(String login, String password) {
            super(login);
            this.password = password;
        }
        
        public FindLoginPassword(User user) {
            this(user.getLogin(), user.getPassword());
        }

        @Override
        public String toSqlQuery() {
            return super.toSqlQuery() + QUERY;
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

    public UserRepository(Connection connection) {
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
        return new UserBuilder()
                .setId(rs.getInt(Fields.USER_ID.name()))
                .setLogin(rs.getString(Fields.LOGIN.name()))
                .setPassword(rs.getString(Fields.PASSWORD.name()))
                .setRole(new RoleBuilder()
                        .setId(rs.getInt(Fields.ROLE.name()))
                        .setRoleName(rs.getString(Fields.ROLE_NAME.name()))
                        .getRole())
                .getUser();
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
