package com.mv.schelokov.carent.model.validators;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class ValidationResult {
    public static final int OK = 0;
    public static final int EMPTY_FIELD = 1;
    public static final int INVALID_EMAIL = 2;
    public static final int INVALID_YEAR = 3;
    public static final int INVALID_DATE = 4;
    public static final int INVALID_PHONE = 5;
    public static final int INVALID_PASSWORD = 6;
    public static final int INVALID_NAME = 7;
    public static final int INVALID_ADDRESS = 8;
    public static final int INVALID_LICENSE_PLATE = 9;
    public static final int INVALID_MODEL = 10;
    public static final int INVALID_MAKE = 11;
    public static final int INVALID_PRICE = 12;
    public static final int INVALID_CAR = 13;
    public static final int INVALID_USER = 14;
    public static final int INVALID_ROLE = 15;
    public static final int PASSWORDS_NOT_MATCH = 16;
    public static final int SAME_LOGIN = 17;
    public static final int USER_NOT_FOUND = 18;
    public static final int INVALID_AMOUNT = 19;
    public static final int REASON_TEXT_TOO_LONG = 20;
}
