package com.mv.schelokov.car_rent.model.db.repository;

import com.mv.schelokov.car_rent.model.db.repository.criteria.role.RoleDeleteCriteria;
import com.mv.schelokov.car_rent.model.db.repository.criteria.role.RoleReadCriteria;
import com.mv.schelokov.car_rent.model.db.repository.exceptions.CriteriaMismatchException;
import com.mv.schelokov.car_rent.model.db.repository.interfaces.AbstractSqlRepository;
import com.mv.schelokov.car_rent.model.db.repository.interfaces.Criteria;
import com.mv.schelokov.car_rent.model.entities.Role;
import com.mv.schelokov.car_rent.model.entities.builders.RoleBuilder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class RoleRepository extends AbstractSqlRepository<Role> {
    
    private static final String CREATE_QUERY = "INSERT INTO roles (role_name) "
            + "VALUES (?)";
    private static final String REMOVE_QUERY = "DELETE FROM roles WHERE"
            + " role_id=?";
    private static final String UPDATE_QUERY = "UPDATE roles SET role_name=?"
            + " WHERE role_id=?";
    
    /**
     * The Field enum has column names for read methods and number of column for
     * the update method and the add method (in the NUMBER attribute)
     */
    enum Fields {
        ROLE_ID(2), ROLE_NAME(1);
        
        int NUMBER;
        
        Fields(int number) {
            this.NUMBER = number;
        }
    }

    public RoleRepository(Connection connection) {
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
    protected Role createItem(ResultSet rs) throws SQLException {
        return new RoleBuilder()
                .setId(rs.getInt(Fields.ROLE_ID.name()))
                .setRoleName(rs.getString(Fields.ROLE_NAME.name()))
                .getRole();
    }

    @Override
    protected void setStatement(PreparedStatement ps, Role item, 
            boolean isUpdateStatement) throws SQLException {
        ps.setString(Fields.ROLE_NAME.NUMBER, item.getRoleName());
        if (isUpdateStatement)
            ps.setInt(Fields.ROLE_ID.NUMBER, item.getId());
    }

    @Override
    protected boolean checkCriteriaInstance(Criteria criteria, 
            boolean isDeleteCriteria) throws CriteriaMismatchException {
        if (isDeleteCriteria) {
            if (criteria instanceof RoleDeleteCriteria)
                return true;
        } else if (criteria instanceof RoleReadCriteria)
            return true;
        throw new CriteriaMismatchException();
    }
}
