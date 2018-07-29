package com.mv.schelokov.car_rent.model.entity.builders;

import com.mv.schelokov.car_rent.model.entity.CarMake;
import com.mv.schelokov.car_rent.model.entity.CarModel;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class CarModelBuilder {
    private final CarModel model;
    
    public CarModelBuilder() {
        model = new CarModel();
    }
    
    public CarModelBuilder setId(int id) {
        this.model.setId(id);
        return this;
    }
    
    public CarModelBuilder setName(String name) {
        this.model.setName(name);
        return this;
    }
    
    public CarModelBuilder setCarMake(CarMake make) {
        this.model.setMake(make);
        return this;
    }
    
    public CarModel getCarModel() {
        return this.model;
    }
}
