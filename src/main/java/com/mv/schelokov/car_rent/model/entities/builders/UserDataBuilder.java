package com.mv.schelokov.car_rent.model.entities.builders;

import com.mv.schelokov.car_rent.model.entities.UserData;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class UserDataBuilder {
    
    private UserData userData;
    
    public UserDataBuilder() {
        userData = new UserData();
    }
    
    public UserDataBuilder setId(int id) {
        this.userData.setId(id);
        return this;
    }
    
    public UserDataBuilder setName(String name) {
        this.userData.setName(name);
        return this;
    }
    
    public UserDataBuilder setAddress(String address) {
        this.userData.setAddress(address);
        return this;
    }
    
    public UserDataBuilder setPhone(String phone) {
        this.userData.setPhone(phone);
        return this;
    }
    
    public UserData getUserData() {
        return this.userData;
    }
    
}
