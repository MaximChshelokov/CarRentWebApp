package com.mv.schelokov.car_rent.model.db.repository.user_data_criteria;

import com.mv.schelokov.car_rent.model.db.repository.user_data_criteria.interfaces.UserDataReadCriteria;
import com.mv.schelokov.car_rent.model.entities.User;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class FindByUser implements UserDataReadCriteria {
    
    private static final String QUERY = "SELECT * FROM users_data "
            + "WHERE userdata_id=?";
    private final int userDataId;
    
    public FindByUser(User user) {
        this.userDataId = user.getUserData();
    }
    
    public FindByUser(int userDataId) {
        this.userDataId = userDataId;
    }

    @Override
    public String toSqlQuery() {
        return QUERY;
    }

    @Override
    public void setStatement(PreparedStatement ps) throws SQLException {
        ps.setInt(1, userDataId);
    }
    
    
}
