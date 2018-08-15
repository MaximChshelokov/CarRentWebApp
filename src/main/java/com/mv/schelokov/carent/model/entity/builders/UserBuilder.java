package com.mv.schelokov.carent.model.entity.builders;

import com.mv.schelokov.carent.model.entity.Role;
import com.mv.schelokov.carent.model.entity.User;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class UserBuilder {
    private final User user;

    
    public UserBuilder() {
        this.user = new User();
    }
        
    public UserBuilder withId(int id) {
        this.user.setId(id);
        return this;
    }
    public UserBuilder withLogin(String login) {
        this.user.setLogin(login);
        return this;
    }
    
    public UserBuilder withPassword(String password) {
        this.user.setPassword(password);
        return this;
    }
    
    public UserBuilder withRole(Role role) {
        this.user.setRole(role);
        return this;
    }
    
    public User getUser() {
        return user;
    }
    
}
