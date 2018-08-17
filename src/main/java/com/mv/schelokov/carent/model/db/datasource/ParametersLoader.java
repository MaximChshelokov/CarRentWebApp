package com.mv.schelokov.carent.model.db.datasource;

import com.mv.schelokov.carent.model.db.datasource.exceptions.DataSourceException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.apache.log4j.Logger;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class ParametersLoader {
    
    private static final Logger LOG = Logger.getLogger(ParametersLoader.class);
    private static final String POOL_SIZE_ERROR = "Wrong pool size number is in"
            + " the file";
    private static final String PARAMETERS_LOAD_ERROR = "Load parameters from"
            + " the file is failed";
    private static final String LOGIN = "LOGIN";
    private static final String PASSWORD = "PASSWORD";
    private static final String URL = "URL";
    private static final String DRIVER = "DRIVER";
    private static final String POOL_SIZE = "POOL_SIZE";

    private Properties properties;
    
    public ParametersLoader(InputStream stream) throws DataSourceException {
        properties = new Properties();
        try {
            properties.load(stream);
        } catch(IOException ex) {
            LOG.error(PARAMETERS_LOAD_ERROR, ex);
            throw new DataSourceException(PARAMETERS_LOAD_ERROR, ex);
        }
    }

    public String getLogin() {
        return properties.getProperty(LOGIN);
    }
    
    public String getPassword() {
        return properties.getProperty(PASSWORD);
    }
    
    public String getUrl() {
        return properties.getProperty(URL);
    }
    
    public String getDriver() {
        return properties.getProperty(DRIVER);
    }
    
    public int getPoolSize() throws DataSourceException {
        int result = 10;    // Default pool size
        String poolSize = properties.getProperty(POOL_SIZE);
        if (poolSize != null)
            try {
                result = Integer.parseInt(poolSize);
            } catch(NumberFormatException ex) {
                LOG.error(POOL_SIZE_ERROR);
                throw new DataSourceException(POOL_SIZE_ERROR, poolSize, ex);
            }
        return result;
    }
}
