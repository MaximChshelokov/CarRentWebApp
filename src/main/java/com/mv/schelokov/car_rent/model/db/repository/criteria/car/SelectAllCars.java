package com.mv.schelokov.car_rent.model.db.repository.criteria.car;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class SelectAllCars implements CarReadCriteria {
    
    private static final String QUERY = "SELECT car_id,license_plate,"
            + "year_of_make,price,model,m.name,m.make,k.name FROM cars "
            + "LEFT JOIN models AS m ON model = model_id LEFT JOIN makes "
            + "AS k ON model = model_id";

    @Override
    public String toSqlQuery() {
        return QUERY;
    }

    @Override
    public void setStatement(PreparedStatement ps) throws SQLException {
    }
}
