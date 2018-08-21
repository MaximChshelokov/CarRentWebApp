package com.mv.schelokov.carent.model.validators;

import com.mv.schelokov.carent.model.entity.InvoiceLine;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class InvoiceLineValidator extends Validator {
    
    private final InvoiceLine invoiceLine;
    
    private static final int MIN_AMOUNT = 100;
    private static final int MAX_AMOUNT = 200_000;
    
    public InvoiceLineValidator(InvoiceLine invoiceLine) {
        this.invoiceLine = invoiceLine;
    }

    @Override
    public int validate() {
        
        if (hasEmptyFields()) {
            return ValidationResult.EMPTY_FIELD;
        }
        
        if (!isValidAmount()) {
            return ValidationResult.INVALID_AMOUNT;
        }
        
        return ValidationResult.OK;
    }
    
    private boolean hasEmptyFields() {
        
        return isNullField(invoiceLine.getDetails())
                || isEmptyString(invoiceLine.getDetails());
    }
    
    private boolean isValidAmount() {
        return invoiceLine.getAmount() >= MIN_AMOUNT
                && invoiceLine.getAmount() <= MAX_AMOUNT;
    }
}
