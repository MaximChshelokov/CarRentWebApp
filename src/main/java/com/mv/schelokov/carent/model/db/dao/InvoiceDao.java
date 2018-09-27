package com.mv.schelokov.carent.model.db.dao;

import com.mv.schelokov.carent.model.db.dao.factories.EntityFromResultSetFactory;
import com.mv.schelokov.carent.model.db.dao.interfaces.AbstractSqlDao;
import com.mv.schelokov.carent.model.db.dao.interfaces.Criteria;
import com.mv.schelokov.carent.model.db.dao.interfaces.SqlCriteria;
import com.mv.schelokov.carent.model.entity.Invoice;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class InvoiceDao extends AbstractSqlDao<Invoice> {
    
    public interface ReadCriteria extends SqlCriteria {}

    public interface DeleteCriteria extends SqlCriteria {}

    public static final Criteria SELECT_ALL = new SelectAllCriteria();

    public static class SelectAllCriteria implements ReadCriteria {

        private static final String QUERY = "SELECT invoice_id,date,paid,total"
                + " FROM invoices_full";

        @Override
        public String toSqlQuery() {
            return QUERY;
        }

        @Override
        public void setStatement(PreparedStatement ps) throws SQLException {}
    }
    
    public static class FindByIdCriteria extends SelectAllCriteria {

        private static final String QUERY = " WHERE invoice_id=?";
        private static final int INVOICE_ID_INDEX = 1;
        private final int invoiceId;

        public FindByIdCriteria(int invoiceId) {
            this.invoiceId = invoiceId;
        }

        @Override
        public String toSqlQuery() {
            return super.toSqlQuery().concat(QUERY);
        }

        @Override
        public void setStatement(PreparedStatement ps) throws SQLException {
            ps.setInt(INVOICE_ID_INDEX, this.invoiceId);
        }
    }

    private static final String CREATE_QUERY = "INSERT INTO invoices ("
            + "invoice_id,date,paid) VALUES (?,?,?)";
    private static final String REMOVE_QUERY = "DELETE FROM invoices WHERE"
            + " invoice_id=?";
    private static final String UPDATE_QUERY = "UPDATE invoices SET "
            + "date=?,paid=? WHERE invoice_id=?";

    private static final int INSERT_INVOICE_ID = 1;
    private static final int INSERT_DATE = 2;
    private static final int INSERT_PAID = 3;
    private static final int UPDATE_INVOICE_ID = 3;
    private static final int UPDATE_DATE = 1;
    private static final int UPDATE_PAID = 2;

    public InvoiceDao(Connection connection) {
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
    protected Invoice createItem(ResultSet rs) throws SQLException {
        return new EntityFromResultSetFactory(rs).createInvoice();
    }
    
    @Override
    protected void setInsertStatement(PreparedStatement ps, Invoice item)
            throws SQLException {
        ps.setInt(INSERT_INVOICE_ID, item.getId());
        ps.setDate(INSERT_DATE, new Date(item.getDate().getTime()));
        ps.setInt(INSERT_PAID, item.getPaid());
    }
    
    @Override
    protected void setUpdateStatement(PreparedStatement ps, Invoice item)
            throws SQLException {
        ps.setInt(UPDATE_INVOICE_ID, item.getId());
        ps.setDate(UPDATE_DATE, new Date(item.getDate().getTime()));
        ps.setInt(UPDATE_PAID, item.getPaid());
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
