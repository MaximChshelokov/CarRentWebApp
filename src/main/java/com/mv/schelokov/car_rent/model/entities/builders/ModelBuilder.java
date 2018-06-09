package com.mv.schelokov.car_rent.model.entities.builders;

import com.mv.schelokov.car_rent.model.entities.Make;
import com.mv.schelokov.car_rent.model.entities.Model;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class ModelBuilder {
    private final Model model;
    
    public ModelBuilder() {
        model = new Model();
    }
    
    public ModelBuilder setId(int id) {
        this.model.setId(id);
        return this;
    }
    
    public ModelBuilder setName(String name) {
        this.model.setName(name);
        return this;
    }
    
    public ModelBuilder setMake(Make make) {
        this.model.setMake(make);
        return this;
    }
    
    public Model getModel() {
        return this.model;
    }
}
