package com.mv.schelokov.carent.model.entity.builders;

import com.mv.schelokov.carent.model.entity.RejectionReason;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class RejectionReasonBuilder {
    
    private final RejectionReason rejectionReason;
    
    public RejectionReasonBuilder() {
        rejectionReason = new RejectionReason();
    }
    
    public RejectionReasonBuilder withId(int id) {
        this.rejectionReason.setId(id);
        return this;
    }
    
    public RejectionReasonBuilder dueReason(String reason) {
        this.rejectionReason.setReason(reason);
        return this;
    }
    
    public RejectionReason getRejectionReason() {
        return this.rejectionReason;
    }
}
