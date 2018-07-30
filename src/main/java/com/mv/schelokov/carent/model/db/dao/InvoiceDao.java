package com.mv.schelokov.carent.model.db.dao;

import com.mv.schelokov.carent.model.db.dao.interfaces.AbstractSqlDao;
import com.mv.schelokov.carent.model.db.dao.interfaces.Criteria;
import com.mv.schelokov.carent.model.db.dao.interfaces.SqlCriteria;
import com.mv.schelokov.carent.model.entity.Invoice;
import com.mv.schelokov.carent.model.entity.builders.InvoiceBuilder;
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

    public static final Criteria SELECT_ALL = new SelectAll();

    public static class SelectAll implements ReadCriteria {

        private static final String QUERY = "SELECT invoice_id,date,paid,total"
                + " FROM invoices_full";

        @Override
        public String toSqlQuery() {
            return QUERY;
        }

        @Override
        public void setStatement(PreparedStatement ps) throws SQLException {}
    }
    
    public static class FindById extends SelectAll {

        private static final String QUERY = " WHERE invoice_id=?";
        private static final int INVOICE_ID_INDEX = 1;
        private final int invoiceId;

        public FindById(int invoiceId) {
            this.invoiceId = invoiceId;
        }

        @Override
        public String toSqlQuery() {
            return super.toSqlQuery() + QUERY;
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

    /**
     * The Field enum has column names for read methods and number of column for
     * the update method and the add method (in the NUMBER attribute)
     */
    enum Fields {
        INVOICE_ID(1, 3), DATE(2, 1), PAID(3, 2), TOTAL;

        int INSERT, UPDATE;

        Fields(int insert, int update) {
            this.INSERT = insert;
            this.UPDATE = update;
        }

        Fields() {
        }
    }

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
        return new InvoiceBuilder()
                .setId(rs.getInt(Fields.INVOICE_ID.name()))
                .setDate(rs.getDate(Fields.DATE.name()))
                .setPaid(rs.getInt(Fields.PAID.name()))
                .setTotal(rs.getInt(Fields.TOTAL.name()))
                .getInvoice();
    }

    @Override
    protected void setStatement(PreparedStatement ps, Invoice item,
            boolean isUpdateStatement) throws SQLException {
        if (isUpdateStatement) {
            ps.setInt(Fields.INVOICE_ID.UPDATE, item.getId());
            ps.setDate(Fields.DATE.UPDATE, new Date(item.getDate().getTime()));
            ps.setInt(Fields.PAID.UPDATE, item.getPaid());
        } else {
            ps.setInt(Fields.INVOICE_ID.INSERT, item.getId());
            ps.setDate(Fields.DATE.INSERT, new Date(item.getDate().getTime()));
            ps.setInt(Fields.PAID.INSERT, item.getPaid());
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
