package com.mv.schelokov.car_rent.model.db.repository;

import com.mv.schelokov.car_rent.model.db.repository.exceptions.CriteriaMismatchException;
import com.mv.schelokov.car_rent.model.db.repository.interfaces.AbstractSqlRepository;
import com.mv.schelokov.car_rent.model.db.repository.interfaces.Criteria;
import com.mv.schelokov.car_rent.model.db.repository.user_data_criteria.interfaces.UserDataDeleteCriteria;
import com.mv.schelokov.car_rent.model.db.repository.user_data_criteria.interfaces.UserDataReadCriteria;
import com.mv.schelokov.car_rent.model.entities.UserData;
import com.mv.schelokov.car_rent.model.entities.builders.UserDataBuilder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class UserDataRepository extends AbstractSqlRepository<UserData> {
    
    private static final String CREATE_QUERY = "INSERT INTO users_data ("
            + "userdata_id,name,address,phone) VALUES (?,?,?,?)";
    private static final String REMOVE_QUERY = "DELETE FROM users_data WHERE"
            + " userdata_id=?";
    private static final String UPDATE_QUERY = "UPDATE users_data SET "
            + "name=?,address=?,phone=? WHERE userdata_id=?";
    
    public UserDataRepository(Connection connection) {
        super(connection);
    }

    @Override
    protected String getCreateQuery() {
        return CREATE_QUERY;
    }

    @Override
    protected String getRemoveQuery() {
        return REMOVE_QUERY;
    }

    @Override
    protected String getUpdateQuery() {
        return UPDATE_QUERY;
    }

    @Override
    protected UserData createItem(ResultSet rs) throws SQLException {
        return new UserDataBuilder()
                .setId(rs.getInt(1))
                .setName(rs.getString(2))
                .setAddress(rs.getString(3))
                .setPhone(rs.getString(4))
                .getUserData();
    }

    @Override
    protected void setStatement(PreparedStatement ps, UserData item, boolean isUpdateStatement) throws SQLException {
        int i = 1;
        if (!isUpdateStatement)
            ps.setInt(i++, item.getId());
        ps.setString(i++, item.getName());
        ps.setString(i++, item.getAddress());
        ps.setString(i++, item.getPhone());
        if (isUpdateStatement)
            ps.setInt(i, item.getId());
    }

    @Override
    protected boolean checkCriteriaInstance(Criteria criteria, boolean isDeleteCriteria) throws CriteriaMismatchException {
        if (isDeleteCriteria) {
            if (criteria instanceof UserDataDeleteCriteria)
                return true;
        } else if (criteria instanceof UserDataReadCriteria)
            return true;
        throw new CriteriaMismatchException();
    }
    
}
