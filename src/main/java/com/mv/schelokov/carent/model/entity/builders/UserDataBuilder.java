package com.mv.schelokov.carent.model.entity.builders;

import com.mv.schelokov.carent.model.entity.User;
import com.mv.schelokov.carent.model.entity.UserData;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class UserDataBuilder {
    
    private final UserData userData;
    
    public UserDataBuilder() {
        userData = new UserData();
    }
    
    public UserDataBuilder withId(int id) {
        this.userData.setId(id);
        return this;
    }
    
    public UserDataBuilder withName(String name) {
        this.userData.setName(name);
        return this;
    }
    
    public UserDataBuilder residentAtAddress(String address) {
        this.userData.setAddress(address);
        return this;
    }
    
    public UserDataBuilder withPhone(String phone) {
        this.userData.setPhone(phone);
        return this;
    }
    
    public UserDataBuilder withUser(User user) {
        this.userData.setUser(user);
        return this;
    }
    
    public UserData getUserData() {
        return this.userData;
    }
    
}
