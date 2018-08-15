package com.mv.schelokov.carent.model.entity.builders;

import com.mv.schelokov.carent.model.entity.CarMake;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class CarMakeBuilder {
    private final CarMake make;
    
    public CarMakeBuilder() {
        make = new CarMake();
    }
    
    public CarMakeBuilder withId(int id) {
        this.make.setId(id);
        return this;
    }
    
    public CarMakeBuilder withName(String name) {
        this.make.setName(name);
        return this;
    }
    
    public CarMake getCarMake() {
        return this.make;
    }
    
}
