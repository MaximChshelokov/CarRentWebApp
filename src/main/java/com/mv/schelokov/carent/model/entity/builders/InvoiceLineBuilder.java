package com.mv.schelokov.carent.model.entity.builders;

import com.mv.schelokov.carent.model.entity.InvoiceLine;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class InvoiceLineBuilder {
    
    private final InvoiceLine invoceLine;
    
    public InvoiceLineBuilder() {
        this.invoceLine = new InvoiceLine();
    }
    
    public InvoiceLineBuilder withId(int id) {
        this.invoceLine.setId(id);
        return this;
    }
    
    public InvoiceLineBuilder withInvoiceId(int invoiceId) {
        this.invoceLine.setInvoiceId(invoiceId);
        return this;
    }
    
    public InvoiceLineBuilder paymentDetails(String details) {
        this.invoceLine.setDetails(details);
        return this;
    }
    
    public InvoiceLineBuilder paymentAmount(int amount) {
        this.invoceLine.setAmount(amount);
        return this;
    }
    
    public InvoiceLine getInvoiceLine() {
        return this.invoceLine;
    }
    
}
