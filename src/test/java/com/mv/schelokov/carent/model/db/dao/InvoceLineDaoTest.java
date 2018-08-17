package com.mv.schelokov.carent.model.db.dao;

import com.mv.schelokov.carent.model.db.dao.exceptions.DbException;
import com.mv.schelokov.carent.model.entity.InvoiceLine;
import com.mv.schelokov.carent.model.entity.builders.InvoiceLineBuilder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class InvoceLineDaoTest {
    
    private Connection connection;
    private InvoiceLineDao ilr;
    private final InvoiceLine iLine = new InvoiceLineBuilder()
                .withInvoiceId(1)
                .paymentDetails("Штраф за царапину")
                .paymentAmount(3000)
                .getInvoiceLine();

    public InvoceLineDaoTest() {
    }
    
    @Before
    public void setUp() throws ClassNotFoundException, 
            InstantiationException, IllegalAccessException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        connection = DriverManager.getConnection(
                "jdbc:mysql://localhost/car_rent_test?autoReconnect=true"
                        + "&useSSL=false&characterEncoding=utf-8",
                "car_rent_app", "Un3L41NoewVA");
        connection.setAutoCommit(false);
        ilr = new InvoiceLineDao(connection);
    }
    
    @After
    public void tearDown() throws SQLException {
        connection.close();
    }
    
    @Test
    public void createNewInvoce() throws DbException {
        assertTrue(ilr.add(iLine));
        ilr.remove(getInvoiceLine());
    }
    
    @Test
    public void findByInvoiceIdAndDeleteLast() throws DbException  {
        ilr.add(iLine);
        ilr.remove(getInvoiceLine());
    }
    
    @Test
    public void findByInvoiceIdAndUpdateFirst() throws DbException {
        ilr.add(iLine);
        InvoiceLine iLineWithId = getInvoiceLine();
        assertTrue(ilr.update(iLineWithId));
        ilr.remove(iLineWithId);
    }
    
    private InvoiceLine getInvoiceLine() throws DbException {
        List<InvoiceLine> iList = ilr.read(new InvoiceLineDao.FindByInvoiceIdCriteria(1));
        for (InvoiceLine invoiceLine : iList)
            if (invoiceLine.getDetails().equals(iLine.getDetails()))
                return invoiceLine;
        return new InvoiceLine();
    }
    
}
