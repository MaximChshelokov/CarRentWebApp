package com.mv.schelokov.car_rent.model.entities;

import com.mv.schelokov.car_rent.model.entities.interfaces.Entity;
import java.util.Objects;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class Defect implements Entity {
    private int id;
    private int car;
    private CarPart carPart;
    private String description;
    private boolean fixed;

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
     * @return the car
     */
    public int getCar() {
        return car;
    }

    /**
     * @param car the car to set
     */
    public void setCar(int car) {
        this.car = car;
    }

    /**
     * @return the carPart
     */
    public CarPart getCarPart() {
        return carPart;
    }

    /**
     * @param carPart the carPart to set
     */
    public void setCarPart(CarPart carPart) {
        this.carPart = carPart;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the fixed
     */
    public boolean isFixed() {
        return fixed;
    }

    /**
     * @param fixed the fixed to set
     */
    public void setFixed(boolean fixed) {
        this.fixed = fixed;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + this.id;
        hash = 47 * hash + this.car;
        hash = 47 * hash + Objects.hashCode(this.carPart);
        hash = 47 * hash + Objects.hashCode(this.description);
        hash = 47 * hash + (this.fixed ? 1 : 0);
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
        final Defect other = (Defect) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.car != other.car) {
            return false;
        }
        if (this.fixed != other.fixed) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.carPart, other.carPart)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Defects{" + "id=" + id + ", car=" + car + ", carPart=" + carPart
                + ", description=" + description + ", fixed=" + fixed + '}';
    }
    
}
