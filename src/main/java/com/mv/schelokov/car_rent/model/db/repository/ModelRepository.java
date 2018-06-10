package com.mv.schelokov.car_rent.model.db.repository;

import com.mv.schelokov.car_rent.model.db.repository.criteria.model.ModelDeleteCriteria;
import com.mv.schelokov.car_rent.model.db.repository.criteria.model.ModelReadCriteria;
import com.mv.schelokov.car_rent.model.db.repository.exceptions.CriteriaMismatchException;
import com.mv.schelokov.car_rent.model.db.repository.interfaces.AbstractSqlRepository;
import com.mv.schelokov.car_rent.model.db.repository.interfaces.Criteria;
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
    
    private static final String CREATE_QUERY = "INSERT INTO models (name,"
            + "make) VALUES (?,?)";
    private static final String REMOVE_QUERY = "DELETE FROM models WHERE"
            + " model_id=?";
    private static final String UPDATE_QUERY = "UPDATE models SET name=?,"
            + "make=? WHERE model_id=?";

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
                .setId(rs.getInt(1))
                .setName(rs.getString(2))
                .setMake(new MakeBuilder()
                        .setId(rs.getInt(3))
                        .setName(rs.getString(4))
                        .getMake())
                .getModel();
    }

    @Override
    protected void setStatement(PreparedStatement ps, Model item,
            boolean isUpdateStatement) throws SQLException {
        ps.setString(1, item.getName());
        ps.setInt(2, item.getMake().getId());
        if (isUpdateStatement) {
            ps.setInt(3, item.getId());
        }
    }

    @Override
    protected boolean checkCriteriaInstance(Criteria criteria,
            boolean isDeleteCriteria) throws CriteriaMismatchException {
        if (isDeleteCriteria) {
            if (criteria instanceof ModelDeleteCriteria) {
                return true;
            }
        } else if (criteria instanceof ModelReadCriteria) {
            return true;
        }
        throw new CriteriaMismatchException();
    }
    
}
