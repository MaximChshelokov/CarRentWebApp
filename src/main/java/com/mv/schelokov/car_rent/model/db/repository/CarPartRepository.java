package com.mv.schelokov.car_rent.model.db.repository;

import com.mv.schelokov.car_rent.model.db.repository.criteria.car_part.CarPartDeleteCriteria;
import com.mv.schelokov.car_rent.model.db.repository.criteria.car_part.CarPartReadCriteria;
import com.mv.schelokov.car_rent.model.db.repository.exceptions.CriteriaMismatchException;
import com.mv.schelokov.car_rent.model.db.repository.interfaces.AbstractSqlRepository;
import com.mv.schelokov.car_rent.model.db.repository.interfaces.Criteria;
import com.mv.schelokov.car_rent.model.entities.CarPart;
import com.mv.schelokov.car_rent.model.entities.builders.CarPartBuilder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class CarPartRepository extends AbstractSqlRepository<CarPart> {
    
    private static final String CREATE_QUERY = "INSERT INTO car_parts (name)"
            + " VALUES (?)"; 
    private static final String REMOVE_QUERY = "DELETE FROM car_parts WHERE"
            + " part_id=?";
    private static final String UPDATE_QUERY = "UPDATE car_parts SET name=? "
            + "WHERE part_id=?";
    
    /**
     * The Field enum has column names for read methods and number of column for
     * the update method and the add method (in the NUMBER attribute)
     */
    enum Fields {
        PART_ID(2), NAME(1);
        
        int NUMBER;
        
        Fields(int number) {
            this.NUMBER = number;
        }
    }
    

    public CarPartRepository(Connection connection) {
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
    protected CarPart createItem(ResultSet rs) throws SQLException {
        return new CarPartBuilder()
                .setId(rs.getInt(Fields.PART_ID.name()))
                .setName(rs.getString(Fields.NAME.name()))
                .getCarPart();
    }

    @Override
    protected void setStatement(PreparedStatement ps, CarPart item, 
            boolean isUpdateStatement) throws SQLException {
        ps.setString(Fields.NAME.NUMBER, item.getName());
        if (isUpdateStatement)
            ps.setInt(Fields.PART_ID.NUMBER, item.getId());
    }

    @Override
    protected boolean checkCriteriaInstance(Criteria criteria, 
            boolean isDeleteCriteria) throws CriteriaMismatchException {
        if (isDeleteCriteria) {
            if (criteria instanceof CarPartDeleteCriteria)
                return true;
        } else if (criteria instanceof CarPartReadCriteria)
            return true;
        throw new CriteriaMismatchException();
    }
}
