package com.mv.schelokov.car_rent.model.db.repository.interfaces;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public interface SqlCriteria extends Criteria {
    
    String toSqlQuery();
    void setStatement(PreparedStatement ps) throws SQLException;
    
}