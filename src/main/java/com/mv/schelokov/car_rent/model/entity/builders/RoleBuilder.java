package com.mv.schelokov.car_rent.model.entity.builders;

import com.mv.schelokov.car_rent.model.entity.Role;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class RoleBuilder {
    
    private final Role role;
    
    public RoleBuilder() {
        this.role = new Role();
    }
    
    public RoleBuilder setId(int id) {
        this.role.setId(id);
        return this;
    }
    
    public RoleBuilder setRoleName(String roleName) {
        this.role.setRoleName(roleName);
        return this;
    }
    
    public Role getRole() {
        return this.role;
    }
    
}
