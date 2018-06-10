package com.mv.schelokov.car_rent.model.db.repository.criteria.car_part;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class SelectAllParts implements CarPartReadCriteria {
    
    private static final String QUERY = "SELECT * FROM car_parts";

    @Override
    public String toSqlQuery() {
        return QUERY;
    }

    @Override
    public void setStatement(PreparedStatement ps) throws SQLException {
    }
}
