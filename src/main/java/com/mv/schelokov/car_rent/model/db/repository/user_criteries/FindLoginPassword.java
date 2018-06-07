package com.mv.schelokov.car_rent.model.db.repository.user_criteries;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class FindLoginPassword implements UserReadCriteria {
    
    private static final String QUERY = "SELECT * FROM users WHERE login=? "
            + "AND password=?";
    private final String login;
    private final String password;
    
    public FindLoginPassword(String login, String password) {
        this.login = login;
        this.password = password;
    }

    @Override
    public String toSqlQuery() {
        return QUERY;
    }

    @Override
    public void setStatement(PreparedStatement ps) throws SQLException {
        ps.setString(1, login);
        ps.setString(2, password);
    }
    
}
