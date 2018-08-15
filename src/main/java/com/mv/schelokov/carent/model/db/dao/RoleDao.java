package com.mv.schelokov.carent.model.db.dao;

import com.mv.schelokov.carent.model.db.dao.interfaces.AbstractSqlDao;
import com.mv.schelokov.carent.model.db.dao.interfaces.Criteria;
import com.mv.schelokov.carent.model.db.dao.interfaces.SqlCriteria;
import com.mv.schelokov.carent.model.entity.Role;
import com.mv.schelokov.carent.model.entity.builders.RoleBuilder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class RoleDao extends AbstractSqlDao<Role> {
    
    public interface ReadCriteria extends SqlCriteria {}

    public interface DeleteCriteria extends SqlCriteria {}

    public static final Criteria SELECT_ALL_CRITERIA = new SelectAllCriteria();

    public static class SelectAllCriteria implements ReadCriteria {
        private static final String QUERY = "SELECT role_id, role_name FROM "
                + "roles";

        @Override
        public String toSqlQuery() {
            return QUERY;
        }

        @Override
        public void setStatement(PreparedStatement ps) throws SQLException {}  
    }
    
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

    public RoleDao(Connection connection) {
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
                .withId(rs.getInt(Fields.ROLE_ID.name()))
                .withRoleName(rs.getString(Fields.ROLE_NAME.name()))
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
            boolean isDeleteCriteria) {
        if (isDeleteCriteria) {
            if (criteria instanceof DeleteCriteria)
                return true;
        } else if (criteria instanceof ReadCriteria)
            return true;
        return false;
    }
}
