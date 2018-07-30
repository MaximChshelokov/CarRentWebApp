package com.mv.schelokov.carent.model.entity;

import com.mv.schelokov.carent.model.entity.interfaces.Entity;
import java.util.Objects;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 * 
 * The Car entity keeps information about a car, that in ownership of the
 * car rent organization.
 */
public class Car implements Entity {
    private int id;
    private CarModel model;
    private int yearOfMake;
    private int price;
    private String licensePlate;
    private boolean available;

    /**
     * @return the id
     */
    @Override
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the yearOfMake
     */
    public int getYearOfMake() {
        return yearOfMake;
    }

    /**
     * @param yearOfMake the yearOfMake to set
     */
    public void setYearOfMake(int yearOfMake) {
        this.yearOfMake = yearOfMake;
    }

    /**
     * @return the price
     */
    public int getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(int price) {
        this.price = price;
    }

    /**
     * @return the licensePlate
     */
    public String getLicensePlate() {
        return licensePlate;
    }

    /**
     * @param licensePlate the licensePlate to set
     */
    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    /**
     * @return the model
     */
    public CarModel getCarModel() {
        return model;
    }

    /**
     * @param model the model to set
     */
    public void setModel(CarModel model) {
        this.model = model;
    }

    @Override
    public String toString() {
        return "Car{" + "id=" + id + ", model=" + model + ", yearOfMake=" + yearOfMake + ", price=" + price + ", licensePlate=" + licensePlate + ", available=" + available + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + this.id;
        hash = 89 * hash + Objects.hashCode(this.model);
        hash = 89 * hash + this.yearOfMake;
        hash = 89 * hash + this.price;
        hash = 89 * hash + Objects.hashCode(this.licensePlate);
        hash = 89 * hash + (this.available ? 1 : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Car other = (Car) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.yearOfMake != other.yearOfMake) {
            return false;
        }
        if (this.price != other.price) {
            return false;
        }
        if (this.available != other.available) {
            return false;
        }
        if (!Objects.equals(this.licensePlate, other.licensePlate)) {
            return false;
        }
        if (!Objects.equals(this.model, other.model)) {
            return false;
        }
        return true;
    }

    /**
     * @return the available
     */
    public boolean isAvailable() {
        return available;
    }

    /**
     * @param available the available to set
     */
    public void setAvailable(boolean available) {
        this.available = available;
    }

}
