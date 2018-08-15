package com.mv.schelokov.carent.model.entity.builders;

import com.mv.schelokov.carent.model.entity.Car;
import com.mv.schelokov.carent.model.entity.CarModel;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class CarBuilder {
    
    private final Car car;
    
    public CarBuilder() {
        this.car = new Car();
    }
    
    public CarBuilder withId(int id) {
        this.car.setId(id);
        return this;
    }
    
    public CarBuilder withModel(CarModel model) {
        this.car.setModel(model);
        return this;
    }
    
    public CarBuilder withLicensePlate(String licensePlate) {
        this.car.setLicensePlate(licensePlate);
        return this;
    }
    
    public CarBuilder inYearOfMake(int year) {
        this.car.setYearOfMake(year);
        return this;
    }
    
    public CarBuilder withPrice(int price) {
        this.car.setPrice(price);
        return this;
    }
    
    public CarBuilder withAvailability(boolean available) {
        this.car.setAvailable(available);
        return this;
    }
    
    public Car getCar() {
        return this.car;
    }
    
}
