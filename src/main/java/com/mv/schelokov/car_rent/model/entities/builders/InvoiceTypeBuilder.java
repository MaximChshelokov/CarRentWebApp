package com.mv.schelokov.car_rent.model.entities.builders;

import com.mv.schelokov.car_rent.model.entities.InvoiceType;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class InvoiceTypeBuilder {
    
    private final InvoiceType invoceType;
    
    public InvoiceTypeBuilder() {
        this.invoceType = new InvoiceType();
    }
    
    public InvoiceTypeBuilder setId(int id) {
        this.invoceType.setId(id);
        return this;
    }
    
    public InvoiceTypeBuilder setName(String name) {
        this.invoceType.setName(name);
        return this;
    }
    
    public InvoiceType getInvoceType() {
        return this.invoceType;
    }
}
