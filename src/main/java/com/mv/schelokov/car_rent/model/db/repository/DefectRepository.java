package com.mv.schelokov.car_rent.model.db.repository;

import com.mv.schelokov.car_rent.model.db.repository.interfaces.AbstractSqlRepository;
import com.mv.schelokov.car_rent.model.db.repository.interfaces.Criteria;
import com.mv.schelokov.car_rent.model.db.repository.interfaces.SqlCriteria;
import com.mv.schelokov.car_rent.model.entities.Defect;
import com.mv.schelokov.car_rent.model.entities.builders.CarPartBuilder;
import com.mv.schelokov.car_rent.model.entities.builders.DefectBuilder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class DefectRepository extends AbstractSqlRepository<Defect> {
    
    public interface ReadCriteria extends SqlCriteria {}
    
    public interface DeleteCriteria extends SqlCriteria {}
    
    public static final Criteria SELECT_ALL = new SelectAll();
    
    public static class SelectAll implements ReadCriteria {
        
        private static final String QUERY = "SELECT defect_id,car,description,"
                + "fixed,car_part,name FROM defects_full";

        @Override
        public String toSqlQuery() {
            return QUERY;
        }

        @Override
        public void setStatement(PreparedStatement ps) throws SQLException {}
    }
        
    private static final String CREATE_QUERY = "INSERT INTO defects ("
            + "car,car_part,description,fixed) VALUES (?,?,?,?)";
    private static final String REMOVE_QUERY = "DELETE FROM defects WHERE"
            + " defect_id=?";
    private static final String UPDATE_QUERY = "UPDATE defects SET "
            + "car=?,car_part=?,description=?,fixed=? WHERE defect_id=?";
    
    /**
     * The Field enum has column names for read methods and number of column for
     * the update method and the add method (in the NUMBER attribute)
     */
    enum Fields {
        DEFECT_ID(5), CAR(1), DESCRIPTION(3), FIXED(4), CAR_PART(2), NAME;
        
        int NUMBER;
        
        Fields(int number) {
            this.NUMBER = number;
        }
        
        Fields() {
        }
    }

    public DefectRepository(Connection connection) {
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
    protected Defect createItem(ResultSet rs) throws SQLException {
        return new DefectBuilder()
                .setId(rs.getInt(Fields.DEFECT_ID.name()))
                .setCar(rs.getInt(Fields.CAR.name()))
                .setDescription(rs.getString(Fields.DESCRIPTION.name()))
                .setFixed(rs.getBoolean(Fields.FIXED.name()))
                .setCarPart(new CarPartBuilder()
                        .setId(rs.getInt(Fields.CAR_PART.name()))
                        .setName(rs.getString(Fields.NAME.name()))
                        .getCarPart())
                .getDefect();
    }
    
    @Override
    protected void setStatement(PreparedStatement ps, Defect item, 
            boolean isUpdateStatement) throws SQLException {
        ps.setInt(Fields.CAR.NUMBER, item.getCar());
        ps.setInt(Fields.CAR_PART.NUMBER, item.getCarPart().getId());
        ps.setString(Fields.DESCRIPTION.NUMBER, item.getDescription());
        ps.setBoolean(Fields.FIXED.NUMBER, item.isFixed());

        if (isUpdateStatement)
            ps.setInt(Fields.DEFECT_ID.NUMBER, item.getId());
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
