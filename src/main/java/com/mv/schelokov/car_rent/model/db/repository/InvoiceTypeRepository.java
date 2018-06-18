package com.mv.schelokov.car_rent.model.db.repository;

import com.mv.schelokov.car_rent.model.db.repository.exceptions.CriteriaMismatchException;
import com.mv.schelokov.car_rent.model.db.repository.interfaces.AbstractSqlRepository;
import com.mv.schelokov.car_rent.model.db.repository.interfaces.Criteria;
import com.mv.schelokov.car_rent.model.db.repository.interfaces.SqlCriteria;
import com.mv.schelokov.car_rent.model.entities.InvoiceType;
import com.mv.schelokov.car_rent.model.entities.builders.InvoiceTypeBuilder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class InvoiceTypeRepository extends AbstractSqlRepository<InvoiceType> {
    
    public interface ReadCriteria extends SqlCriteria {}

    public interface DeleteCriteria extends SqlCriteria {}

    public static final Criteria SELECT_ALL = new SelectAll();

    public static class SelectAll implements ReadCriteria {
  
        private static final String QUERY = "SELECT type_id,name FROM "
                + "invoice_types";

        @Override
        public String toSqlQuery() {
            return QUERY;
        }

        @Override
        public void setStatement(PreparedStatement ps) throws SQLException {}
    }
    
    private static final String CREATE_QUERY = "INSERT INTO invoice_types "
            + "(name) VALUES (?)";
    private static final String REMOVE_QUERY = "DELETE FROM invoice_types WHERE"
            + " type_id=?";
    private static final String UPDATE_QUERY = "UPDATE invoice_types SET name=?"
            + " WHERE type_id=?";
    
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

    public InvoiceTypeRepository(Connection connection) {
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
    protected InvoiceType createItem(ResultSet rs) throws SQLException {
        return new InvoiceTypeBuilder()
                .setId(rs.getInt(Fields.TYPE_ID.name()))
                .setName(rs.getString(Fields.NAME.name()))
                .getInvoceType();
    }
    
    @Override
    protected void setStatement(PreparedStatement ps, InvoiceType item, 
            boolean isUpdateStatement) throws SQLException {
        ps.setString(Fields.NAME.NUMBER, item.getName());

        if (isUpdateStatement)
            ps.setInt(Fields.TYPE_ID.NUMBER, item.getId());
    }

    @Override
    protected boolean checkCriteriaInstance(Criteria criteria, 
            boolean isDeleteCriteria) throws CriteriaMismatchException {
        if (isDeleteCriteria) {
            if (criteria instanceof DeleteCriteria)
                return true;
        } else if (criteria instanceof ReadCriteria)
            return true;
        throw new CriteriaMismatchException();
    }
    
}
