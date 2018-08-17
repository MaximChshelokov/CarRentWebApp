package com.mv.schelokov.carent.model.db.dao;

import com.mv.schelokov.carent.model.db.dao.exceptions.DbException;
import com.mv.schelokov.carent.model.entity.Invoice;
import com.mv.schelokov.carent.model.entity.builders.InvoiceBuilder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.GregorianCalendar;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class InvoiceDaoTest {
    
    private Connection connection;
    private InvoiceDao ir;
    private final Invoice invoice = new InvoiceBuilder()
                .withId(2)
                .creationDate(new GregorianCalendar(2018, 5, 19).getTime())
                .initialPayment(1500)
                .getInvoice();

    
    public InvoiceDaoTest() {
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
        ir = new InvoiceDao(connection);
    }
    
    @After
    public void tearDown() throws SQLException {
        connection.close();
    }

    @Test
    public void createNewInvoce() throws DbException {
        assertTrue(ir.add(invoice));
        ir.remove(invoice);
    }
    
    @Test
    public void deleteInvoice() throws DbException {
        ir.add(invoice);
        assertTrue(ir.remove(invoice));
    }

    @Test
    public void updateInvoice() throws DbException {
        ir.add(invoice);

        assertTrue(ir.update(invoice));
        
        ir.remove(invoice);
    }
    
}
