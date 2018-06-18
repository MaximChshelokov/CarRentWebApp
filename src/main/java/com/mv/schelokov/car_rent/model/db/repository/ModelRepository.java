package com.mv.schelokov.car_rent.model.db.repository;

import com.mv.schelokov.car_rent.model.db.repository.exceptions.CriteriaMismatchException;
import com.mv.schelokov.car_rent.model.db.repository.interfaces.AbstractSqlRepository;
import com.mv.schelokov.car_rent.model.db.repository.interfaces.Criteria;
import com.mv.schelokov.car_rent.model.db.repository.interfaces.SqlCriteria;
import com.mv.schelokov.car_rent.model.entities.Model;
import com.mv.schelokov.car_rent.model.entities.builders.MakeBuilder;
import com.mv.schelokov.car_rent.model.entities.builders.ModelBuilder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class ModelRepository extends AbstractSqlRepository<Model> {
 
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

    public ModelRepository(Connection connection) {
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
    protected Model createItem(ResultSet rs) throws SQLException {
        return new ModelBuilder()
                .setId(rs.getInt(Fields.MODEL_ID.name()))
                .setName(rs.getString(Fields.NAME.name()))
                .setMake(new MakeBuilder()
                        .setId(rs.getInt(Fields.MAKE.name()))
                        .setName(rs.getString(Fields.MAKE_NAME.name()))
                        .getMake())
                .getModel();
    }

    @Override
    protected void setStatement(PreparedStatement ps, Model item,
            boolean isUpdateStatement) throws SQLException {
        ps.setString(Fields.NAME.NUMBER, item.getName());
        ps.setInt(Fields.MAKE.NUMBER, item.getMake().getId());
        if (isUpdateStatement) {
            ps.setInt(Fields.MODEL_ID.NUMBER, item.getId());
        }
    }

    @Override
    protected boolean checkCriteriaInstance(Criteria criteria,
            boolean isDeleteCriteria) throws CriteriaMismatchException {
        if (isDeleteCriteria) {
            if (criteria instanceof DeleteCriteria) {
                return true;
            }
        } else if (criteria instanceof ReadCriteria) {
            return true;
        }
        throw new CriteriaMismatchException();
    }
    
}
