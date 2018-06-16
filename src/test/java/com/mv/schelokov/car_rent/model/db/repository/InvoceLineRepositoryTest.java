package com.mv.schelokov.car_rent.model.db.repository;

import com.mv.schelokov.car_rent.model.db.repository.criteria.invoice_line.FindByInvoiceId;
import com.mv.schelokov.car_rent.model.db.repository.exceptions.DbException;
import com.mv.schelokov.car_rent.model.entities.InvoceLine;
import com.mv.schelokov.car_rent.model.entities.builders.InvoiceLineBuilder;
import com.mv.schelokov.car_rent.model.entities.builders.InvoiceTypeBuilder;
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
public class InvoceLineRepositoryTest {
    
    private Connection connection;
    private InvoiceLineRepository ilr;

    public InvoceLineRepositoryTest() {
    }
    
    @Before
    public void setUp() throws ClassNotFoundException, 
            InstantiationException, IllegalAccessException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        connection = DriverManager.getConnection(
                "jdbc:mysql://localhost/car_rent_test?autoReconnect=true"
                        + "&useSSL=false&characterEncoding=utf-8",
                "car_rent_app", "Un3L41NoewVA");
        ilr = new InvoiceLineRepository(connection);
    }
    
    @After
    public void tearDown() throws SQLException {
        connection.close();
    }
    
    @Test
    public void createNewInvoceType() throws DbException {
        assertTrue(ilr.add(new InvoiceLineBuilder()
                .setInvoiceId(1)
                .setDetails("Штраф за царапину")
                .setType(new InvoiceTypeBuilder().setId(2).getInvoceType())
                .setAmount(3000)
                .getInvoiceLine()));
    }
    
    @Test
    public void findByInvoiceIdAndDeleteLast() throws DbException {
        List<InvoceLine> ill = ilr.read(new FindByInvoiceId(1));
        assertTrue(ilr.remove(ill.get(ill.size()-1)));
    }
    
    @Test
    public void findByInvoiceIdAndUpdateFirst() throws DbException {
        InvoceLine iLine = ilr.read(new FindByInvoiceId(1)).get(0);
        iLine.setAmount(iLine.getAmount()+1);
        
        assertTrue(ilr.update(iLine));
    }
    
}
