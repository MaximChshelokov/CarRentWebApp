package com.mv.schelokov.carent.model.entity.builders;

import com.mv.schelokov.carent.model.entity.Role;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class RoleBuilder {
    
    private final Role role;
    
    public RoleBuilder() {
        this.role = new Role();
    }
    
    public RoleBuilder withId(int id) {
        this.role.setId(id);
        return this;
    }
    
    public RoleBuilder withRoleName(String roleName) {
        this.role.setRoleName(roleName);
        return this;
    }
    
    public Role getRole() {
        return this.role;
    }
    
}
