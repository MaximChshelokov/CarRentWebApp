package com.mv.schelokov.carent.model.db.dao;

import com.mv.schelokov.carent.model.db.dao.interfaces.AbstractSqlDao;
import com.mv.schelokov.carent.model.db.dao.interfaces.Criteria;
import com.mv.schelokov.carent.model.db.dao.interfaces.SqlCriteria;
import com.mv.schelokov.carent.model.entity.CarMake;
import com.mv.schelokov.carent.model.entity.builders.CarMakeBuilder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class MakeDao extends AbstractSqlDao<CarMake> {
    
    public interface ReadCriteria extends SqlCriteria {}

    public interface DeleteCriteria extends SqlCriteria {}

    public static class SelectAllCriteria implements ReadCriteria {
        private static final String QUERY = "SELECT make_id,name FROM makes";

        @Override
        public String toSqlQuery() {
            return QUERY;
        }

        @Override
        public void setStatement(PreparedStatement ps) throws SQLException {}
    }
    
    public static class FindNameCriteria extends SelectAllCriteria {

        private static final String QUERY = " WHERE name=?";
        private static final int NAME_INDEX = 1;
        private final String name;

        public FindNameCriteria(String name) {
            this.name = name;
        }

        @Override
        public String toSqlQuery() {
            return super.toSqlQuery() + QUERY;
        }

        @Override
        public void setStatement(PreparedStatement ps) throws SQLException {
            ps.setString(NAME_INDEX, this.name);
        }
    }
    
    private static final String CREATE_QUERY = "INSERT INTO makes (name) "
            + "VALUES (?)";
    private static final String REMOVE_QUERY = "DELETE FROM makes WHERE"
            + " make_id=?";
    private static final String UPDATE_QUERY = "UPDATE makes SET name=? "
            + "WHERE make_id=?";
    
    /**
     * The Field enum has column names for read methods and number of column for
     * the update method and the add method (in the NUMBER attribute)
     */
    enum Fields {
        MAKE_ID(2), NAME(1);
        
        int NUMBER;
        
        Fields(int number) {
            this.NUMBER = number;
        }
    }

    public MakeDao(Connection connection) {
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
        return new CarMakeBuilder()
                .withId(rs.getInt(Fields.MAKE_ID.name()))
                .withName(rs.getString(Fields.NAME.name()))
                .getCarMake();
    }

    @Override
    protected void setStatement(PreparedStatement ps, CarMake item, 
            boolean isUpdateStatement) throws SQLException {
        ps.setString(Fields.NAME.NUMBER, item.getName());
        if (isUpdateStatement)
            ps.setInt(Fields.MAKE_ID.NUMBER, item.getId());
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
