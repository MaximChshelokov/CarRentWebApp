package com.mv.schelokov.car_rent.model.db.repository.user_criteries;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class SelectAllUsers implements UserReadCriteria {
    
    private static final String QUERY = "SELECT user_id,user_data,login,"
            + "password,role_name FROM users LEFT JOIN roles ON role=role_id";

    @Override
    public String toSqlQuery() {
        return QUERY;
    }

    @Override
    public void setStatement(PreparedStatement ps) throws SQLException {
    }
    
}
