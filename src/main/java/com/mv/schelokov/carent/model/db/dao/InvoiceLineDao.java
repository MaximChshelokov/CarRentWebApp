package com.mv.schelokov.carent.model.db.dao;

import com.mv.schelokov.carent.model.db.dao.factories.EntityFromResultSetFactory;
import com.mv.schelokov.carent.model.db.dao.interfaces.AbstractSqlDao;
import com.mv.schelokov.carent.model.db.dao.interfaces.Criteria;
import com.mv.schelokov.carent.model.db.dao.interfaces.SqlCriteria;
import com.mv.schelokov.carent.model.entity.InvoiceLine;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class InvoiceLineDao extends AbstractSqlDao<InvoiceLine> {
    
    public interface ReadCriteria extends SqlCriteria {}

    public interface DeleteCriteria extends SqlCriteria {}

    public static class FindByInvoiceIdCriteria implements ReadCriteria {
        private static final String QUERY = "SELECT line_id,invoice_id,details,"
                + "amount FROM invoice_lines WHERE invoice_id=?";
        private static final int INVOICE_ID_COLUMN = 1;
        private final int invoiceId;

        public FindByInvoiceIdCriteria(int invoiceId) {
            this.invoiceId = invoiceId;
        }

        @Override
        public String toSqlQuery() {
            return QUERY;
        }

        @Override
        public void setStatement(PreparedStatement ps) throws SQLException {
            ps.setInt(INVOICE_ID_COLUMN, invoiceId);
        }
    }
    
    private static final String CREATE_QUERY = "INSERT INTO invoice_lines ("
            + "invoice_id,details,amount) VALUES (?,?,?)";
    private static final String REMOVE_QUERY = "DELETE FROM invoice_lines WHERE"
            + " line_id=?";
    private static final String UPDATE_QUERY = "UPDATE invoice_lines SET "
            + "invoice_id=?,details=?,amount=? WHERE line_id=?";

    private static final int INVOICE_ID = 1;
    private static final int DETAILS = 2;
    private static final int AMOUNT = 3;
    private static final int LINE_ID = 4;

    public InvoiceLineDao(Connection connection) {
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
    protected InvoiceLine createItem(ResultSet rs) throws SQLException {
        return new EntityFromResultSetFactory(rs).createInvoiceLine();
    }
    
    @Override
    protected void setInsertStatement(PreparedStatement ps, InvoiceLine item)
            throws SQLException {
        ps.setInt(INVOICE_ID, item.getInvoiceId());
        ps.setString(DETAILS, item.getDetails());
        ps.setInt(AMOUNT, item.getAmount());     
    }
    
    @Override
    protected void setUpdateStatement(PreparedStatement ps, InvoiceLine item)
            throws SQLException {
        setInsertStatement(ps, item);
        ps.setInt(LINE_ID, item.getId());
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
