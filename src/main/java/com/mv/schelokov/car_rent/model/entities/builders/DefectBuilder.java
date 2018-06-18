package com.mv.schelokov.car_rent.model.entities.builders;

import com.mv.schelokov.car_rent.model.entities.CarPart;
import com.mv.schelokov.car_rent.model.entities.Defect;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class DefectBuilder {
    
    private final Defect defect;
    
    public DefectBuilder() {
        this.defect = new Defect();
    }
    
    public DefectBuilder setId(int id) {
        this.defect.setId(id);
        return this;
    }
    
    public DefectBuilder setCar(int car) {
        this.defect.setCar(car);
        return this;
    }
    
    public DefectBuilder setCarPart(CarPart carPart) {
        this.defect.setCarPart(carPart);
        return this;
    }
    
    public DefectBuilder setDescription(String description) {
        this.defect.setDescription(description);
        return this;
    }
    
    public DefectBuilder setFixed(boolean fixed) {
        this.defect.setFixed(fixed);
        return this;
    }
    
    public Defect getDefect() {
        return this.defect;
    }
}
