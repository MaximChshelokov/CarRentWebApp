package com.mv.schelokov.car_rent.model.db.repository.criteria.model;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class SelectAllModels implements ModelReadCriteria {
    
    private static final String QUERY = "SELECT model_id,m.name,make,k.name "
            + "FROM models AS m LEFT JOIN makes AS k ON make=make_id";

    @Override
    public String toSqlQuery() {
        return QUERY;
    }

    @Override
    public void setStatement(PreparedStatement ps) throws SQLException {
    }
    
}
