package com.mv.schelokov.car_rent.model.db.repository.criteria.invoce_type;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class SelectAllTypes implements InvoceTypeReadCriteria {
    
    private static final String QUERY = "SELECT type_id,name FROM invoce_types";

    @Override
    public String toSqlQuery() {
        return QUERY;
    }

    @Override
    public void setStatement(PreparedStatement ps) throws SQLException {
    }
}
