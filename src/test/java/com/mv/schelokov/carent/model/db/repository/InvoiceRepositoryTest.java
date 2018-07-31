package com.mv.schelokov.carent.model.db.repository;

import com.mv.schelokov.carent.model.db.dao.InvoiceDao;
import com.mv.schelokov.carent.model.db.dao.exceptions.DbException;
import com.mv.schelokov.carent.model.entity.Invoice;
import com.mv.schelokov.carent.model.entity.builders.InvoiceBuilder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.GregorianCalendar;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class InvoiceRepositoryTest {
    
    private Connection connection;
    private InvoiceDao ir;

    
    public InvoiceRepositoryTest() {
    }
    
    @Before
    public void setUp() throws ClassNotFoundException,
            InstantiationException, IllegalAccessException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        connection = DriverManager.getConnection(
                "jdbc:mysql://localhost/car_rent_test?autoReconnect=true"
                + "&useSSL=false&characterEncoding=utf-8",
                "car_rent_app", "Un3L41NoewVA");
        ir = new InvoiceDao(connection);
    }
    
    @After
    public void tearDown() throws SQLException {
        
        connection.close();
    }

    @Test
    public void createNewInvoce() throws DbException {
        assertTrue(ir.add(new InvoiceBuilder()
                .setId(2)
                .setDate(new GregorianCalendar(2018, 5, 19).getTime())
                .setPaid(1500)
                .getInvoice()));
    }
    
    @Test
    public void findAllAndDeleteLast() throws DbException {
        List<Invoice> il = ir.read(InvoiceDao.SELECT_ALL);
        assertTrue(ir.remove(il.get(il.size() - 1)));
    }

    @Test
    public void findAllAndUpdateFirst() throws DbException {
        Invoice invoice = ir.read(InvoiceDao.SELECT_ALL).get(0);
        invoice.setPaid(invoice.getPaid() + 1);

        assertTrue(ir.update(invoice));
    }
    
}
