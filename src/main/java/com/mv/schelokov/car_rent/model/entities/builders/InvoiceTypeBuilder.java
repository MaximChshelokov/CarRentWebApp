package com.mv.schelokov.car_rent.model.entities.builders;

import com.mv.schelokov.car_rent.model.entities.InvoceType;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class InvoiceTypeBuilder {
    
    private final InvoceType invoceType;
    
    public InvoiceTypeBuilder() {
        this.invoceType = new InvoceType();
    }
    
    public InvoiceTypeBuilder setId(int id) {
        this.invoceType.setId(id);
        return this;
    }
    
    public InvoiceTypeBuilder setName(String name) {
        this.invoceType.setName(name);
        return this;
    }
    
    public InvoceType getInvoceType() {
        return this.invoceType;
    }
}
