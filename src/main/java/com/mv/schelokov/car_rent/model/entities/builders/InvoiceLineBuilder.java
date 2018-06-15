package com.mv.schelokov.car_rent.model.entities.builders;

import com.mv.schelokov.car_rent.model.entities.InvoceLine;
import com.mv.schelokov.car_rent.model.entities.InvoceType;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class InvoiceLineBuilder {
    
    private final InvoceLine invoceLine;
    
    public InvoiceLineBuilder() {
        this.invoceLine = new InvoceLine();
    }
    
    public InvoiceLineBuilder setId(int id) {
        this.invoceLine.setId(id);
        return this;
    }
    
    public InvoiceLineBuilder setInvoiceId(int invoiceId) {
        this.invoceLine.setInvoiceId(invoiceId);
        return this;
    }
    
    public InvoiceLineBuilder setDetails(String details) {
        this.invoceLine.setDetails(details);
        return this;
    }
    
    public InvoiceLineBuilder setType(InvoceType type) {
        this.invoceLine.setType(type);
        return this;
    }
    
    public InvoiceLineBuilder setAmount(int amount) {
        this.invoceLine.setAmount(amount);
        return this;
    }
    
    public InvoceLine getInvoceLine() {
        return this.invoceLine;
    }
    
}
