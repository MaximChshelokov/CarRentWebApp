package com.mv.schelokov.car_rent.model.db.datasource;

import com.mv.schelokov.car_rent.model.db.datasource.exceptions.DataSourceException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class ParametersLoader {
    
    private Properties properties;
    
    public ParametersLoader(InputStream stream) throws DataSourceException {
        properties = new Properties();
        try {
            properties.load(stream);
        } catch(IOException ex) {
            throw new DataSourceException("Load parameters from the file is "
                    + "failed", ex);
        }
    }

    public String getLogin() {
        return properties.getProperty("LOGIN", "car_rent_app");
    }
    
    public String getPassword() {
        return properties.getProperty("PASSWORD", "Un3L41NoewVA");
    }
    
    public String getUrl() {
        return properties.getProperty("URL", "jdbc:mysql://localhost/"
                + "car_rent_test?autoReconnect=true&useSSL=false"
                + "&characterEncoding=utf-8");
    }
    
    public String getDriver() {
        return properties.getProperty("DRIVER", "com.mysql.cj.jdbc.Driver");
    }
    
    public int getPoolSize() throws DataSourceException {
        int result = 10;    // Default pool size
        String poolSize = properties.getProperty("POOL_SIZE");
        if (poolSize != null)
            try {
                result = Integer.parseInt(poolSize);
            } catch(NumberFormatException ex) {
                throw new DataSourceException("Wrong pool size number is in the"
                        + " file", poolSize, ex);
            }
        return result;
    }
}
