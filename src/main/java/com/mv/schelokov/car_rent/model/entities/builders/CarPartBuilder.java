package com.mv.schelokov.car_rent.model.entities.builders;

import com.mv.schelokov.car_rent.model.entities.CarPart;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class CarPartBuilder {
    
    private final CarPart carPart;
    
    public CarPartBuilder() {
        this.carPart = new CarPart();
    }
    
    public CarPartBuilder setId(int id) {
        this.carPart.setId(id);
        return this;
    }
    
    public CarPartBuilder setName(String name) {
        this.carPart.setName(name);
        return this;
    }
    
    public CarPart getCarPart() {
        return this.carPart;
    }
}
