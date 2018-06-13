package com.mv.schelokov.car_rent.model.db.repository.criteria.user;

import com.mv.schelokov.car_rent.model.db.repository.criteria.user.interfaces.UserReadCriteria;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class FindLoginPassword implements UserReadCriteria {
    
    private static final String QUERY = "SELECT user_id,login,password,role,"
            + "role_name FROM users_full WHERE login=? AND password=?";
    private static final int LOGIN_COLUMN = 1;
    private static final int PASSWORD_COLUMN = 2;
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
        ps.setString(LOGIN_COLUMN, login);
        ps.setString(PASSWORD_COLUMN, password);
    }
    
}
