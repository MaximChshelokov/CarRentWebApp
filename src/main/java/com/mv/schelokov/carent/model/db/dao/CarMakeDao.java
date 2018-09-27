package com.mv.schelokov.carent.model.db.dao;

import com.mv.schelokov.carent.model.db.dao.factories.EntityFromResultSetFactory;
import com.mv.schelokov.carent.model.db.dao.interfaces.AbstractSqlDao;
import com.mv.schelokov.carent.model.db.dao.interfaces.Criteria;
import com.mv.schelokov.carent.model.db.dao.interfaces.SqlCriteria;
import com.mv.schelokov.carent.model.entity.CarMake;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class CarMakeDao extends AbstractSqlDao<CarMake> {
    
    public interface ReadCriteria extends SqlCriteria {}

    public interface DeleteCriteria extends SqlCriteria {}

    public static class SelectAllCriteria implements ReadCriteria {
        private static final String QUERY = "SELECT make_id,make_name FROM "
                + "makes";

        @Override
        public String toSqlQuery() {
            return QUERY;
        }

        @Override
        public void setStatement(PreparedStatement ps) throws SQLException {}
    }
    
    public static class FindNameCriteria extends SelectAllCriteria {

        private static final String QUERY = " WHERE make_name=?";
        private static final int NAME_INDEX = 1;
        private final String name;

        public FindNameCriteria(String name) {
            this.name = name;
        }

        @Override
        public String toSqlQuery() {
            return super.toSqlQuery().concat(QUERY);
        }

        @Override
        public void setStatement(PreparedStatement ps) throws SQLException {
            ps.setString(NAME_INDEX, this.name);
        }
    }
    
    private static final String CREATE_QUERY = "INSERT INTO makes (make_name) "
            + "VALUES (?)";
    private static final String REMOVE_QUERY = "DELETE FROM makes WHERE"
            + " make_id=?";
    private static final String UPDATE_QUERY = "UPDATE makes SET make_name=? "
            + "WHERE make_id=?";
    
    private static final int NAME = 1;
    private static final int MAKE_ID = 2;

    public CarMakeDao(Connection connection) {
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
    protected CarMake createItem(ResultSet rs) throws SQLException {
        return new EntityFromResultSetFactory(rs).createCarMake();
    }
    
    @Override
    protected void setInsertStatement(PreparedStatement ps, CarMake item)
            throws SQLException {  
        ps.setString(NAME, item.getName());
    }

    @Override
    protected void setUpdateStatement(PreparedStatement ps, CarMake item)
            throws SQLException {
        setInsertStatement(ps, item);
        ps.setInt(MAKE_ID, item.getId());
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
