package com.mv.schelokov.carent.model.db.dao;

import com.mv.schelokov.carent.model.db.dao.interfaces.AbstractSqlDao;
import com.mv.schelokov.carent.model.db.dao.interfaces.Criteria;
import com.mv.schelokov.carent.model.db.dao.interfaces.SqlCriteria;
import com.mv.schelokov.carent.model.entity.CarModel;
import com.mv.schelokov.carent.model.entity.builders.CarMakeBuilder;
import com.mv.schelokov.carent.model.entity.builders.CarModelBuilder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class ModelDao extends AbstractSqlDao<CarModel> {
 
    public interface ReadCriteria extends SqlCriteria {}

    public interface DeleteCriteria extends SqlCriteria {}

    public static final Criteria SELECT_ALL = new SelectAll();

    public static class SelectAll implements ReadCriteria {
        private static final String QUERY = "SELECT model_id,name,make,"
                + "make_name FROM models_full";

        @Override
        public String toSqlQuery() {
            return QUERY;
        }

        @Override
        public void setStatement(PreparedStatement ps) throws SQLException {}
    }
    
    public static class FindModel extends SelectAll {
        private static final String QUERY = " WHERE name=? AND make=?";
        private static final int NAME_INDEX = 1;
        private static final int MAKE_INDEX = 2;
        private final String name;
        private final int make;
        
        public FindModel(CarModel model) {
            this.name = model.getName();
            this.make = model.getCarMake().getId();
        }
        
        @Override
        public String toSqlQuery() {
            return super.toSqlQuery() + QUERY;
        }
        
        @Override
        public void setStatement(PreparedStatement ps) throws SQLException {
            ps.setString(NAME_INDEX, this.name);
            ps.setInt(MAKE_INDEX, this.make);
        }
    }
    
    private static final String CREATE_QUERY = "INSERT INTO models (name,"
            + "make) VALUES (?,?)";
    private static final String REMOVE_QUERY = "DELETE FROM models WHERE"
            + " model_id=?";
    private static final String UPDATE_QUERY = "UPDATE models SET name=?,"
            + "make=? WHERE model_id=?";
    
    /**
     * The Field enum has column names for read methods and number of column for
     * the update method and the add method (in the NUMBER attribute)
     */
    enum Fields {
        MODEL_ID(3), NAME(1), MAKE(2), MAKE_NAME;
        
        int NUMBER;
        
        Fields(int number) {
            this.NUMBER = number;
        }
        
        Fields() {
        }
    }

    public ModelDao(Connection connection) {
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
        return new CarModelBuilder()
                .setId(rs.getInt(Fields.MODEL_ID.name()))
                .setName(rs.getString(Fields.NAME.name()))
                .setCarMake(new CarMakeBuilder()
                        .setId(rs.getInt(Fields.MAKE.name()))
                        .setName(rs.getString(Fields.MAKE_NAME.name()))
                        .getCarMake())
                .getCarModel();
    }

    @Override
    protected void setStatement(PreparedStatement ps, CarModel item,
            boolean isUpdateStatement) throws SQLException {
        ps.setString(Fields.NAME.NUMBER, item.getName());
        ps.setInt(Fields.MAKE.NUMBER, item.getCarMake().getId());
        if (isUpdateStatement) {
            ps.setInt(Fields.MODEL_ID.NUMBER, item.getId());
        }
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
