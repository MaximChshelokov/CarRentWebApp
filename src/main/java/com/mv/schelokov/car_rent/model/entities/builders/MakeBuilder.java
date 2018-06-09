package com.mv.schelokov.car_rent.model.entities.builders;

import com.mv.schelokov.car_rent.model.entities.Make;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class MakeBuilder {
    private Make make;
    
    public MakeBuilder() {
        make = new Make();
    }
    
    public MakeBuilder setId(int id) {
        this.make.setId(id);
        return this;
    }
    
    public MakeBuilder setName(String name) {
        this.make.setName(name);
        return this;
    }
    
    public Make getMake() {
        return this.make;
    }
    
}
