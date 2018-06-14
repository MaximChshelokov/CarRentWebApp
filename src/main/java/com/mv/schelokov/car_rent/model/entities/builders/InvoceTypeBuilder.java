package com.mv.schelokov.car_rent.model.entities.builders;

import com.mv.schelokov.car_rent.model.entities.InvoceType;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class InvoceTypeBuilder {
    
    private final InvoceType invoceType;
    
    public InvoceTypeBuilder() {
        this.invoceType = new InvoceType();
    }
    
    public InvoceTypeBuilder setId(int id) {
        this.invoceType.setId(id);
        return this;
    }
    
    public InvoceTypeBuilder setName(String name) {
        this.invoceType.setName(name);
        return this;
    }
    
    public InvoceType getInvoceType() {
        return this.invoceType;
    }
}
