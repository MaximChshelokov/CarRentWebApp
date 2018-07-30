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
    
    public InvoiceBuilder setId(int id) {
        this.invoice.setId(id);
        return this;
    }
    
    public InvoiceBuilder setDate(Date date) {
        this.invoice.setDate(date);
        return this;
    }
    
    public InvoiceBuilder setPaid(int paid) {
        this.invoice.setPaid(paid);
        return this;
    }
    
    public InvoiceBuilder setTotal(int total) {
        this.invoice.setTotal(total);
        return this;
    }
    
    public Invoice getInvoice() {
        return this.invoice;
    }
}
