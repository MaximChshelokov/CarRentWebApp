package com.mv.schelokov.car_rent.model.db.repository.criteria.role;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class SelectAllRoles implements RoleReadCriteria {
    
    private static final String QUERY = "SELECT role_id, role_name FROM roles";

    @Override
    public String toSqlQuery() {
        return QUERY;
    }

    @Override
    public void setStatement(PreparedStatement ps) throws SQLException {
    }
}
