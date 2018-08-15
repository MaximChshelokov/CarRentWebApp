package com.mv.schelokov.carent.model.entity.builders;

import com.mv.schelokov.carent.model.entity.CarMake;
import com.mv.schelokov.carent.model.entity.CarModel;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class CarModelBuilder {
    private final CarModel model;
    
    public CarModelBuilder() {
        model = new CarModel();
    }
    
    public CarModelBuilder withId(int id) {
        this.model.setId(id);
        return this;
    }
    
    public CarModelBuilder withName(String name) {
        this.model.setName(name);
        return this;
    }
    
    public CarModelBuilder withCarMake(CarMake make) {
        this.model.setMake(make);
        return this;
    }
    
    public CarModel getCarModel() {
        return this.model;
    }
}
