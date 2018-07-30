package com.mv.schelokov.carent.model.entity.builders;

import com.mv.schelokov.carent.model.entity.Car;
import com.mv.schelokov.carent.model.entity.RentOrder;
import com.mv.schelokov.carent.model.entity.User;
import java.util.Date;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class RentOrderBuilder {
    
    private final RentOrder rentOrder;
    
    public RentOrderBuilder() {
        this.rentOrder = new RentOrder();
    }
    
    public RentOrderBuilder setId(int id) {
        this.rentOrder.setId(id);
        return this;
    }
    
    public RentOrderBuilder setCar(Car car) {
        this.rentOrder.setCar(car);
        return this;
    }
    
    public RentOrderBuilder setUser(User user) {
        this.rentOrder.setUser(user);
        return this;
    }
    
    public RentOrderBuilder setStartDate(Date startDate) {
        this.rentOrder.setStartDate(startDate);
        return this;
    }
    
    public RentOrderBuilder setEndDate(Date endDate) {
        this.rentOrder.setEndDate(endDate);
        return this;
    }
    
    public RentOrderBuilder setApprovedBy(User approvedBy) {
        this.rentOrder.setApprovedBy(approvedBy);
        return this;
    }
    
    public RentOrder getRentOrder() {
        return this.rentOrder;
    }
}
