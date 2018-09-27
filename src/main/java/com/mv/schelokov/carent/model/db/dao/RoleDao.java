package com.mv.schelokov.carent.model.db.dao;

import com.mv.schelokov.carent.model.db.dao.factories.EntityFromResultSetFactory;
import com.mv.schelokov.carent.model.db.dao.interfaces.AbstractSqlDao;
import com.mv.schelokov.carent.model.db.dao.interfaces.Criteria;
import com.mv.schelokov.carent.model.db.dao.interfaces.SqlCriteria;
import com.mv.schelokov.carent.model.entity.Role;
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
    
    private static final int ROLE_NAME = 1;
    private static final int ROLE_ID = 2;

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
        return new EntityFromResultSetFactory(rs).createRole();
    }

    @Override
    protected void setInsertStatement(PreparedStatement ps, Role item)
            throws SQLException {
        ps.setString(ROLE_NAME, item.getRoleName());
    }
    
    @Override
    protected void setUpdateStatement(PreparedStatement ps, Role item)
            throws SQLException {
        setInsertStatement(ps, item);
        ps.setInt(ROLE_ID, item.getId());
    }

    @Override
    protected boolean isReadCriteriaInstance(Criteria criteria) {
        return criteria instanceof ReadCriteria;
    }

    @Override
    protected boolean isDeleteCriteriaInstance(Criteria criteria) {
        return criteria instanceof DeleteCriteria;
    }
}
