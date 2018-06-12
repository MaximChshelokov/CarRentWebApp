package com.mv.schelokov.car_rent.model.db.repository.criteria.model;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class SelectAllModels implements ModelReadCriteria {
    
    private static final String QUERY = "SELECT model_id,name,make,make_name "
            + "FROM models_full";

    @Override
    public String toSqlQuery() {
        return QUERY;
    }

    @Override
    public void setStatement(PreparedStatement ps) throws SQLException {
    }
    
}
