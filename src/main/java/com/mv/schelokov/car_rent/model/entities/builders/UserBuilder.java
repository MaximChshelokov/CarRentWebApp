package com.mv.schelokov.car_rent.model.entities.builders;

import com.mv.schelokov.car_rent.model.entities.Role;
import com.mv.schelokov.car_rent.model.entities.User;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class UserBuilder {
    private User user;

    
    public UserBuilder() {
        this.user = new User();
    }
        
    public UserBuilder setId(int id) {
        this.user.setId(id);
        return this;
    }
    public UserBuilder setLogin(String login) {
        this.user.setLogin(login);
        return this;
    }
    
    public UserBuilder setPassword(String password) {
        this.user.setPassword(password);
        return this;
    }
    
    public UserBuilder setRole(Role role) {
        this.user.setRole(role);
        return this;
    }
    
    public User getUser() {
        return user;
    }
    
}
