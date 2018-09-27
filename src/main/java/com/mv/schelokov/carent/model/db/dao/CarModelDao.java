package com.mv.schelokov.carent.model.db.dao;

import com.mv.schelokov.carent.model.db.dao.factories.EntityFromResultSetFactory;
import com.mv.schelokov.carent.model.db.dao.interfaces.AbstractSqlDao;
import com.mv.schelokov.carent.model.db.dao.interfaces.Criteria;
import com.mv.schelokov.carent.model.db.dao.interfaces.SqlCriteria;
import com.mv.schelokov.carent.model.entity.CarModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class CarModelDao extends AbstractSqlDao<CarModel> {
 
    public interface ReadCriteria extends SqlCriteria {}

    public interface DeleteCriteria extends SqlCriteria {}

    public static class SelectAllCriteria implements ReadCriteria {
        private static final String QUERY = "SELECT model_id,model_name,"
                + "make_id,make_name FROM models_full";

        @Override
        public String toSqlQuery() {
            return QUERY;
        }

        @Override
        public void setStatement(PreparedStatement ps) throws SQLException {}
    }
    
    public static class FindModelCriteria extends SelectAllCriteria {
        private static final String QUERY = " WHERE model_name=? AND make_id=?";
        private static final int NAME_INDEX = 1;
        private static final int MAKE_INDEX = 2;
        private final String name;
        private final int make;
        
        public FindModelCriteria(CarModel model) {
            this.name = model.getName();
            this.make = model.getCarMake().getId();
        }
        
        @Override
        public String toSqlQuery() {
            return super.toSqlQuery().concat(QUERY);
        }
        
        @Override
        public void setStatement(PreparedStatement ps) throws SQLException {
            ps.setString(NAME_INDEX, this.name);
            ps.setInt(MAKE_INDEX, this.make);
        }
    }
    
    private static final String CREATE_QUERY = "INSERT INTO models (model_name,"
            + "make_id) VALUES (?,?)";
    private static final String REMOVE_QUERY = "DELETE FROM models WHERE"
            + " model_id=?";
    private static final String UPDATE_QUERY = "UPDATE models SET model_name=?,"
            + "make_id=? WHERE model_id=?";
    
    private static final int NAME = 1;
    private static final int MAKE = 2;
    private static final int MODEL_ID = 3;

    public CarModelDao(Connection connection) {
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
    protected CarModel createItem(ResultSet rs) throws SQLException {
        return new EntityFromResultSetFactory(rs).createCarModel();
    }

    @Override
    protected void setInsertStatement(PreparedStatement ps, CarModel item)
            throws SQLException {
        ps.setString(NAME, item.getName());
        ps.setInt(MAKE, item.getCarMake().getId());  
    }
    
    @Override
    protected void setUpdateStatement(PreparedStatement ps, CarModel item)
            throws SQLException {
        setInsertStatement(ps, item);
        ps.setInt(MODEL_ID, item.getId());
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
