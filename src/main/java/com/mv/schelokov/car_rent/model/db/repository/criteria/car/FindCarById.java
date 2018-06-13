package com.mv.schelokov.car_rent.model.db.repository.criteria.car;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class FindCarById implements CarReadCriteria {
    
    private static final String QUERY = "SELECT car_id,license_plate,"
            + "year_of_make,price,model,name,make,make_name FROM cars_full "
            + "WHERE car_id=?";
    private static final int CAR_ID = 1;
    
    private final int id;
    
    // TODO: Add constructors for Invoice and Defect entities!
    
    public FindCarById(int id) {
        this.id = id;
    }
    

    @Override
    public String toSqlQuery() {
        return QUERY;
    }

    @Override
    public void setStatement(PreparedStatement ps) throws SQLException {
        ps.setInt(CAR_ID, id);
    }
}
