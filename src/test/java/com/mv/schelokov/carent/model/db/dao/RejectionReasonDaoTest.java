package com.mv.schelokov.carent.model.db.dao;

import com.mv.schelokov.carent.model.db.dao.exceptions.DbException;
import com.mv.schelokov.carent.model.entity.RejectionReason;
import com.mv.schelokov.carent.model.entity.builders.RejectionReasonBuilder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class RejectionReasonDaoTest {
    
    private Connection connection;
    private RejectionReasonDao rrd;
    private final RejectionReason reason = new RejectionReasonBuilder()
            .withId(2)
            .dueReason("Задержка оплаты")
            .getRejectionReason();
    
    public RejectionReasonDaoTest() {
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
        rrd = new RejectionReasonDao(connection);
    }
    
    @After
    public void tearDown() throws SQLException {
        connection.close();
    }

    @Test
    public void createRejectionReason() throws DbException {
        assertTrue(rrd.add(reason));
        rrd.remove(reason);
    }
    
    @Test
    public void updateRejectionReason() throws DbException {
        rrd.add(reason);
        assertTrue(rrd.update(reason));
        rrd.remove(reason);
    }
    
    @Test
    public void deleteRejectionReason() throws DbException {
        rrd.add(reason);
        assertTrue(rrd.remove(reason));
    }
    
    @Test
    public void findAllRejectionReasons() throws DbException {
        rrd.add(reason);
        assertFalse(rrd.read(RejectionReasonDao.SELECT_ALL).isEmpty());
        rrd.remove(reason);
    }
    
    @Test
    public void findRejectionReasonById() throws DbException {
        rrd.add(reason);
        assertEquals(reason, rrd.read(new RejectionReasonDao
                        .FindByIdCriteria(reason.getId())).get(0));
        rrd.remove(reason);       
    }    
}
