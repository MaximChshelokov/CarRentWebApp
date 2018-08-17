package com.mv.schelokov.carent.model.db.dao;

import com.mv.schelokov.carent.model.db.dao.exceptions.DbException;
import com.mv.schelokov.carent.model.entity.Role;
import com.mv.schelokov.carent.model.entity.builders.RoleBuilder;
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
public class RoleDaoTest {
    
    private Connection connection;
    private RoleDao rr;
    private final Role role = new RoleBuilder()
            .withRoleName("guest")
            .getRole();
    
    public RoleDaoTest() {
    }
    
    @Before
    public void setUp() throws SQLException, ClassNotFoundException, 
            InstantiationException, IllegalAccessException {
        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        connection = DriverManager.getConnection(
                "jdbc:mysql://localhost/car_rent_test?autoReconnect=true"
                        + "&useSSL=false&characterEncoding=utf-8",
                "car_rent_app", "Un3L41NoewVA");
        connection.setAutoCommit(false); 
        rr = new RoleDao(connection);
    }
    
    @After
    public void tearDown() throws SQLException {
        connection.close();
    }

    @Test
    public void createNewRole() throws DbException {
        assertTrue(rr.add(role));
        rr.remove(getRole());
    }
    
    @Test
    public void deleteRole() throws DbException {
        rr.add(role);
        assertTrue(rr.remove(getRole()));
    }
    
    @Test
    public void updateRole() throws DbException {
        rr.add(role);
        Role roleWithId = getRole();
        assertTrue(rr.update(roleWithId));
        rr.remove(roleWithId);
    }
    
    private Role getRole() throws DbException {
        List<Role> roleList = rr.read(RoleDao.SELECT_ALL_CRITERIA);
        for (Role r : roleList)
            if (r.getRoleName().equals(role.getRoleName()))
                return r;
        return new Role();
    }
    
}
