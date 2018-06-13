package com.mv.schelokov.car_rent.model.db.repository.criteria.user;

import com.mv.schelokov.car_rent.model.db.repository.criteria.user.interfaces.UserReadCriteria;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class SelectAllUsers implements UserReadCriteria {
    
    private static final String QUERY = "SELECT user_id,login,password,role,"
            + "role_name FROM users_full";

    @Override
    public String toSqlQuery() {
        return QUERY;
    }

    @Override
    public void setStatement(PreparedStatement ps) throws SQLException {
    }
    
}
