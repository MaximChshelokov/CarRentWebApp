package com.mv.schelokov.carent.model.entity.builders;

import com.mv.schelokov.carent.model.entity.Invoice;
import java.util.Date;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class InvoiceBuilder {
    
    private final Invoice invoice;
    
    public InvoiceBuilder() {
        this.invoice = new Invoice();
    }
    
    public InvoiceBuilder withId(int id) {
        this.invoice.setId(id);
        return this;
    }
    
    public InvoiceBuilder creationDate(Date date) {
        this.invoice.setDate(date);
        return this;
    }
    
    public InvoiceBuilder initialPayment(int paid) {
        this.invoice.setPaid(paid);
        return this;
    }
    
    public InvoiceBuilder estimatedTotal(int total) {
        this.invoice.setTotal(total);
        return this;
    }
    
    public Invoice getInvoice() {
        return this.invoice;
    }
}
