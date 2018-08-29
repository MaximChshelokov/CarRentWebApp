package com.mv.schelokov.carent.model.validators;

import com.mv.schelokov.carent.model.entity.RejectionReason;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class RejectionReasonValidator extends Validator {
    
    private static final int MAX_REASON_LENGTH = 100;
    
    private final RejectionReason rejectionReason;
    
    public RejectionReasonValidator(RejectionReason rejectionReason) {
        this.rejectionReason = rejectionReason;
    }
    
    @Override
    public int validate() {

        if (hasEmptyFields()) {
            return ValidationResult.EMPTY_FIELD;
        }

        if (!isValidReasonLength()) {
            return ValidationResult.REASON_TEXT_TOO_LONG;
        }

        return ValidationResult.OK;
    }

    private boolean hasEmptyFields() {

        return isNullField(rejectionReason.getReason())
                || isEmptyString(rejectionReason.getReason());
    }

    private boolean isValidReasonLength() {
        return rejectionReason.getReason().length() <= MAX_REASON_LENGTH;
    }
}
