package com.mv.schelokov.carent.model.db.dao.interfaces;

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