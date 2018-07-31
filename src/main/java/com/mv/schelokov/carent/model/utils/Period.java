package com.mv.schelokov.carent.model.utils;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class Period {
    
    private final Date startDate;
    private final Date endDate;
    
    public Period(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }
    
    public int getDays() {
        return (int) TimeUnit.MILLISECONDS.toDays(endDate.getTime()
                - startDate.getTime());
    }
}

