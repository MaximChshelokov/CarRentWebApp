package com.mv.schelokov.car_rent.model.entities.builders;

import com.mv.schelokov.car_rent.model.entities.CarMake;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class CarMakeBuilder {
    private final CarMake make;
    
    public CarMakeBuilder() {
        make = new CarMake();
    }
    
    public CarMakeBuilder setId(int id) {
        this.make.setId(id);
        return this;
    }
    
    public CarMakeBuilder setName(String name) {
        this.make.setName(name);
        return this;
    }
    
    public CarMake getCarMake() {
        return this.make;
    }
    
}
