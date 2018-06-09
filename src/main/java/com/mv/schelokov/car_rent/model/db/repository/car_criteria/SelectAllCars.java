package com.mv.schelokov.car_rent.model.db.repository.car_criteria;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class SelectAllCars implements CarReadCriteria {
    
    private static final String QUERY = "SELECT c.car_id,c.license_plate,"
            + "c.year_of_make,c.price,c.model,m.name,m.make,k.name FROM cars "
            + "AS c LEFT JOIN models AS m ON model = model_id LEFT JOIN makes "
            + "AS k ON model = model_id";

    @Override
    public String toSqlQuery() {
        return QUERY;
    }

    @Override
    public void setStatement(PreparedStatement ps) throws SQLException {
    }
}
