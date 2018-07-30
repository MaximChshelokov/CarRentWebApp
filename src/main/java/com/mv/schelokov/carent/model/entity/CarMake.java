package com.mv.schelokov.carent.model.entity;

import com.mv.schelokov.carent.model.entity.interfaces.Entity;
import java.util.Objects;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 * 
 * The CarMake entity is a child entity of CarModel entity. It keeps a brand
 * name of the car manufacturer.
 */
public class CarMake implements Entity {
    private int id;
    private String name;

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
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + this.id;
        hash = 43 * hash + Objects.hashCode(this.name);
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
        final CarMake other = (CarMake) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Makes{" + "id=" + id + ", name=" + name + '}';
    }
    
}
