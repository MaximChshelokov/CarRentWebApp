package com.mv.schelokov.car_rent.model.db.repository;

import com.mv.schelokov.car_rent.model.db.repository.exceptions.CriteriaMismatchException;
import com.mv.schelokov.car_rent.model.db.repository.interfaces.AbstractSqlRepository;
import com.mv.schelokov.car_rent.model.db.repository.interfaces.Criteria;
import com.mv.schelokov.car_rent.model.entities.User;
import com.mv.schelokov.car_rent.model.entities.builders.UserBuilder;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class UserRepository extends AbstractSqlRepository<User> {
    
    private static final String CREATE_QUERY = "INSERT INTO 'user' (user_id,"
            + "user_data,login,password,role) VALUES (?,?,?,?,?)";
    private static final String REMOVE_QUERY = "DELETE FROM 'user' WHERE"
            + " user_id=?";
    private static final String UPDATE_QUERY = "UPDATE 'user' SET user_data=?,"
            + "login=?,password=?,role=? WHERE user_id=?";
    

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
                .createUser()
                .setId(rs.getInt(1))
                .setUserData(rs.getInt(2))
                .setLogin(rs.getString(3))
                .setPassword(rs.getString(4))
                .setRole(rs.getString(5))
                .getUser();
    }

    @Override
    protected void setStatement(PreparedStatement ps, User item, 
            boolean isUpdateStatement) throws SQLException {
        int i = 1;
        if (!isUpdateStatement)
            ps.setInt(i++, item.getId());
        ps.setInt(i++, item.getUserData());
        ps.setString(i++, item.getLogin());
        ps.setString(i++, item.getPassword());
        ps.setString(i++, item.getRole());
        if (isUpdateStatement)
            ps.setInt(i, item.getId());
    }

    @Override
    protected boolean checkCriteriaInstance(Criteria criteria, 
            boolean isDeleteCriteria) throws CriteriaMismatchException {
        return true;
    }
    
}
