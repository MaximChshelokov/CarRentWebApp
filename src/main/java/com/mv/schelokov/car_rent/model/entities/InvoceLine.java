package com.mv.schelokov.car_rent.model.entities;

import com.mv.schelokov.car_rent.model.entities.interfaces.Entity;
import java.util.Objects;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class InvoceLine implements Entity {
    private int id;
    private int invoiceId;
    private String details;
    private InvoceType type;
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
     * @return the type
     */
    public InvoceType getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(InvoceType type) {
        this.type = type;
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
        hash = 79 * hash + Objects.hashCode(this.type);
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
        final InvoceLine other = (InvoceLine) obj;
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
        if (!Objects.equals(this.type, other.type)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "InvoceLine{" + "id=" + id + ", invoice_id=" + invoiceId + 
                ", details=" + details + ", type=" + type + ", amount=" + amount
                + '}';
    }
    
}
