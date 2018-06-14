package com.mv.schelokov.car_rent.model.db.repository;

import com.mv.schelokov.car_rent.model.db.repository.criteria.invoce_type.InvoceTypeDeleteCriteria;
import com.mv.schelokov.car_rent.model.db.repository.criteria.invoce_type.InvoceTypeReadCriteria;
import com.mv.schelokov.car_rent.model.db.repository.exceptions.CriteriaMismatchException;
import com.mv.schelokov.car_rent.model.db.repository.interfaces.AbstractSqlRepository;
import com.mv.schelokov.car_rent.model.db.repository.interfaces.Criteria;
import com.mv.schelokov.car_rent.model.entities.InvoceType;
import com.mv.schelokov.car_rent.model.entities.builders.InvoceTypeBuilder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class InvoceTypeRepository extends AbstractSqlRepository<InvoceType> {
    
    private static final String CREATE_QUERY = "INSERT INTO invoce_types (name)"
            + " VALUES (?)";
    private static final String REMOVE_QUERY = "DELETE FROM invoce_types WHERE"
            + " type_id=?";
    private static final String UPDATE_QUERY = "UPDATE invoce_types SET name=? "
            + "WHERE type_id=?";
    
    /**
     * The Field enum has column names for read methods and number of column for
     * the update method and the add method (in the NUMBER attribute)
     */
    enum Fields {
        TYPE_ID(2), NAME(1);
        
        int NUMBER;
        
        Fields(int number) {
            this.NUMBER = number;
        }
    }

    public InvoceTypeRepository(Connection connection) {
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
    protected InvoceType createItem(ResultSet rs) throws SQLException {
        return new InvoceTypeBuilder()
                .setId(rs.getInt(Fields.TYPE_ID.name()))
                .setName(rs.getString(Fields.NAME.name()))
                .getInvoceType();
    }
    
    @Override
    protected void setStatement(PreparedStatement ps, InvoceType item, 
            boolean isUpdateStatement) throws SQLException {
        ps.setString(Fields.NAME.NUMBER, item.getName());

        if (isUpdateStatement)
            ps.setInt(Fields.TYPE_ID.NUMBER, item.getId());
    }

    @Override
    protected boolean checkCriteriaInstance(Criteria criteria, 
            boolean isDeleteCriteria) throws CriteriaMismatchException {
        if (isDeleteCriteria) {
            if (criteria instanceof InvoceTypeDeleteCriteria)
                return true;
        } else if (criteria instanceof InvoceTypeReadCriteria)
            return true;
        throw new CriteriaMismatchException();
    }
    
}
