package com.mv.schelokov.car_rent.model.db.repository.criteria.rent_order;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class SelectAllRentOrders implements RentOrderReadCriteria {
    
    private static final String QUERY = "SELECT rent_id,start_date,end_date,"
            + "car,license_plate,year_of_make,price,model,model_name,make,"
            + "make_name,user,login,approved_by,approver_login "
            + "FROM rent_orders_full";

    @Override
    public String toSqlQuery() {
        return QUERY;
    }

    @Override
    public void setStatement(PreparedStatement ps) throws SQLException {
    }
    
}
