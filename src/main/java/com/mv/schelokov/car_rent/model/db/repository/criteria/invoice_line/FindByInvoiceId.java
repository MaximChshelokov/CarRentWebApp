package com.mv.schelokov.car_rent.model.db.repository.criteria.invoice_line;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class FindByInvoiceId implements InvoiceLineReadCriteria {
    
    private static final String QUERY = "SELECT line_id,invoice_id,details,"
            + "type,name,amount FROM invoice_lines_full WHERE invoice_id=?";
    private static final int INVOICE_ID_COLUMN = 1;
    private final int invoiceId;
    
    public FindByInvoiceId(int invoiceId) {
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
