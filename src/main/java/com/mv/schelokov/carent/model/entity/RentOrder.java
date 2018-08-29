package com.mv.schelokov.carent.model.entity;

import com.mv.schelokov.carent.model.entity.interfaces.Entity;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 * 
 * The RentOrder entity keeps information about the rent order, that was created
 * by some user to rent a car.
 */
public class RentOrder implements Entity {
    private int id;
    private Car car;
    private User user;
    private User approvedBy;
    private Date startDate;
    private Date endDate;
    private int sum;
    private RejectionReason rejectionReason;

    /**
     * @return the id
     */
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
     * @return the car
     */
    public Car getCar() {
        return car;
    }

    /**
     * @param car the car to set
     */
    public void setCar(Car car) {
        this.car = car;
    }

    /**
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * @return the approvedBy
     */
    public User getApprovedBy() {
        return approvedBy;
    }

    /**
     * @param approvedBy the approvedBy to set
     */
    public void setApprovedBy(User approvedBy) {
        this.approvedBy = approvedBy;
    }

    /**
     * @return the startDate
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * @return the endDate
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    
    /**
     * @return the sum
     */
    public int getSum() {
        return sum;
    }

    /**
     * @param sum the sum to set
     */
    public void setSum(int sum) {
        this.sum = sum;
    }

    /**
     * @return the rejectionReason
     */
    public RejectionReason getRejectionReason() {
        return rejectionReason;
    }

    /**
     * @param rejectionReason the rejectionReason to set
     */
    public void setRejectionReason(RejectionReason rejectionReason) {
        this.rejectionReason = rejectionReason;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + this.id;
        hash = 17 * hash + Objects.hashCode(this.car);
        hash = 17 * hash + Objects.hashCode(this.user);
        hash = 17 * hash + Objects.hashCode(this.approvedBy);
        hash = 17 * hash + Objects.hashCode(this.startDate);
        hash = 17 * hash + Objects.hashCode(this.endDate);
        hash = 17 * hash + this.sum;
        hash = 17 * hash + Objects.hashCode(this.rejectionReason);
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
        final RentOrder other = (RentOrder) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.sum != other.sum) {
            return false;
        }
        if (!Objects.equals(this.car, other.car)) {
            return false;
        }
        if (!Objects.equals(this.user, other.user)) {
            return false;
        }
        if (!Objects.equals(this.approvedBy, other.approvedBy)) {
            return false;
        }
        if (!Objects.equals(this.startDate, other.startDate)) {
            return false;
        }
        if (!Objects.equals(this.endDate, other.endDate)) {
            return false;
        }
        if (!Objects.equals(this.rejectionReason, other.rejectionReason)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "RentOrder{" + "id=" + id + ", car=" + car + ", user=" + user +
                ", approvedBy=" + approvedBy + ", startDate=" + startDate +
                ", endDate=" + endDate + ", sum=" + sum + ", rejectionReason="
                + rejectionReason + '}';
    }

}
