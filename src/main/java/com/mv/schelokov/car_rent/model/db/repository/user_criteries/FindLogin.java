package com.mv.schelokov.car_rent.model.db.repository.user_criteries;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class FindLogin implements UserReadCriteria {
    private static final String QUERY = "SELECT * FROM users WHERE login=? ";
    private final String login;
    
    public FindLogin(String login) {
        this.login = login;
    }

    @Override
    public String toSqlQuery() {
        return QUERY;
    }

    @Override
    public void setStatement(PreparedStatement ps) throws SQLException {
        ps.setString(1, login);
    }
}
