package com.mv.schelokov.carent.model.entity;

import com.mv.schelokov.carent.model.entity.interfaces.Entity;
import java.util.Objects;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 * 
 * The InvoiceLine entity is the child entity of the Invoice entity. It keeps
 * a line of some service or fine sum.
 */
public class InvoiceLine implements Entity {
    private int id;
    private int invoiceId;
    private String details;
    private int amount;

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
     * @return the details
     */
    public String getDetails() {
        return details;
    }

    /**
     * @param details the details to set
     */
    public void setDetails(String details) {
        this.details = details;
    }


    /**
     * @return the amount
     */
    public int getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }

    /**
     * @return the invoice_id
     */
    public int getInvoiceId() {
        return invoiceId;
    }

    /**
     * @param invoice_id the invoice_id to set
     */
    public void setInvoiceId(int invoice_id) {
        this.invoiceId = invoice_id;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + this.id;
        hash = 79 * hash + this.invoiceId;
        hash = 79 * hash + Objects.hashCode(this.details);
        hash = 79 * hash + this.amount;
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
        final InvoiceLine other = (InvoiceLine) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.invoiceId != other.invoiceId) {
            return false;
        }
        if (this.amount != other.amount) {
            return false;
        }
        if (!Objects.equals(this.details, other.details)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "InvoceLine{" + "id=" + id + ", invoice_id=" + invoiceId + 
                ", details=" + details + ", amount=" + amount
                + '}';
    }
    
}
