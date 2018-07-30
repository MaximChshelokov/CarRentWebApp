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
    
    public CarBuilder setId(int id) {
        this.car.setId(id);
        return this;
    }
    
    public CarBuilder setModel(CarModel model) {
        this.car.setModel(model);
        return this;
    }
    
    public CarBuilder setLicensePlate(String licensePlate) {
        this.car.setLicensePlate(licensePlate);
        return this;
    }
    
    public CarBuilder setYearOfMake(int year) {
        this.car.setYearOfMake(year);
        return this;
    }
    
    public CarBuilder setPrice(int price) {
        this.car.setPrice(price);
        return this;
    }
    
    public CarBuilder setAvailable(boolean available) {
        this.car.setAvailable(available);
        return this;
    }
    
    public Car getCar() {
        return this.car;
    }
    
}
