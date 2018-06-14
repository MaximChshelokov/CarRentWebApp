package com.mv.schelokov.car_rent.model.db.repository;

import com.mv.schelokov.car_rent.model.db.repository.criteria.invoce_type.SelectAllTypes;
import com.mv.schelokov.car_rent.model.db.repository.exceptions.DbException;
import com.mv.schelokov.car_rent.model.entities.InvoceType;
import com.mv.schelokov.car_rent.model.entities.builders.InvoceTypeBuilder;
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
public class InvoceTypeRepositoryTest {
    
    private Connection connection;
    private InvoceTypeRepository itr;
    
    public InvoceTypeRepositoryTest() {
    }
    
    @Before
    public void setUp() throws ClassNotFoundException, 
            InstantiationException, IllegalAccessException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        connection = DriverManager.getConnection(
                "jdbc:mysql://localhost/car_rent_test?autoReconnect=true"
                        + "&useSSL=false&characterEncoding=utf-8",
                "car_rent_app", "Un3L41NoewVA");
        itr = new InvoceTypeRepository(connection);
    }
    
    @After
    public void tearDown() throws SQLException {
        connection.close();
    }

    @Test
    public void createNewInvoceType() throws DbException {
        assertTrue(itr.add(new InvoceTypeBuilder()
                .setName("rent total")
                .getInvoceType()));
    }
    
    @Test
    public void findAllAndDeleteLast() throws DbException {
        List<InvoceType> itl = itr.read(new SelectAllTypes());
        assertTrue(itr.remove(itl.get(itl.size()-1)));
    }
    
    @Test
    public void findAllAndUpdateSecond() throws DbException {
        InvoceType iType = itr.read(new SelectAllTypes()).get(1);
        iType.setName("penalty");
        
        assertTrue(itr.update(iType));
    }
}
