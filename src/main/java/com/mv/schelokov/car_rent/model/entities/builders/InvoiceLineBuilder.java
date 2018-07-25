package com.mv.schelokov.car_rent.model.entities.builders;

import com.mv.schelokov.car_rent.model.entities.InvoiceLine;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class InvoiceLineBuilder {
    
    private final InvoiceLine invoceLine;
    
    public InvoiceLineBuilder() {
        this.invoceLine = new InvoiceLine();
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
    
    public InvoiceLineBuilder setAmount(int amount) {
        this.invoceLine.setAmount(amount);
        return this;
    }
    
    public InvoiceLine getInvoiceLine() {
        return this.invoceLine;
    }
    
}
