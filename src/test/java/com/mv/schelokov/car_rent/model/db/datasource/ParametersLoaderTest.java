package com.mv.schelokov.car_rent.model.db.datasource;

import com.mv.schelokov.car_rent.model.db.datasource.exceptions.DataSourceException;
import java.io.InputStream;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */

public class ParametersLoaderTest {
    private final InputStream paramStream = ParametersLoaderTest.class.getResourceAsStream("db_params_test.properties");
    private final ParametersLoader loader;
    
    public ParametersLoaderTest() throws DataSourceException {
        if (paramStream == null)
            fail("File not found!");
        this.loader = new ParametersLoader(paramStream);
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getUser method, of class ParametersLoader.
     */
    @Ignore
    @Test
    public void testGetUser() {
        System.out.println("getUser");
        String expResult = "car_rent";
        String result = loader.getLogin();
        assertEquals(expResult, result);
    }

    /**
     * Test of getPassword method, of class ParametersLoader.
     */
    @Ignore
    @Test
    public void testGetPassword() {
        System.out.println("getPassword");
        String expResult = "pass";
        String result = loader.getPassword();
        assertEquals(expResult, result);
    }

    /**
     * Test of getUrl method, of class ParametersLoader.
     */
    @Ignore
    @Test
    public void testGetUrl() {
        System.out.println("getUrl");
        String expResult = "www.leningrad.ru";
        String result = loader.getUrl();
        assertEquals(expResult, result);
    }

    /**
     * Test of getDriver method, of class ParametersLoader.
     */
    @Ignore
    @Test
    public void testGetDriver() {
        System.out.println("getDriver");
        String expResult = "Droiva";
        String result = loader.getDriver();
        assertEquals(expResult, result);
    }

    /**
     * Test of getPoolSize method, of class ParametersLoader.
     */
    @Ignore
    @Test
    public void testGetPoolSize() throws Exception {
        System.out.println("getPoolSize");
        int expResult = 1337;
        int result = loader.getPoolSize();
        assertEquals(expResult, result);
    }
    
}
