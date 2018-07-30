package com.mv.schelokov.carent.model.entity;

import com.mv.schelokov.carent.model.entity.interfaces.Entity;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 * 
 * The Invoice entity keeps information about the amount of money to be paid by
 * the client for renting a car. The Invoice entity is connected to the RentOrder
 * entity.
 */
public class Invoice implements Entity {
    
    private int id;
    private int paid;
    private int total;
    private Date date;

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
     * @return the paid
     */
    public int getPaid() {
        return paid;
    }

    /**
     * @param paid the paid to set
     */
    public void setPaid(int paid) {
        this.paid = paid;
    }

    /**
     * @return the total
     */
    public int getTotal() {
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(int total) {
        this.total = total;
    }

    /**
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + this.id;
        hash = 41 * hash + this.paid;
        hash = 41 * hash + this.total;
        hash = 41 * hash + Objects.hashCode(this.date);
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
        final Invoice other = (Invoice) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.paid != other.paid) {
            return false;
        }
        if (this.total != other.total) {
            return false;
        }
        if (!Objects.equals(this.date, other.date)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Invoice{" + "id=" + id + ", paid=" + paid + ", total=" + total + ", date=" + date + '}';
    }
}
