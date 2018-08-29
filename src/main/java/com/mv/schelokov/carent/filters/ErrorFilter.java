package com.mv.schelokov.carent.filters;

import com.mv.schelokov.carent.model.validators.ValidationResult;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class ErrorFilter implements Filter {
    
    private static final Map<Integer, String> ERROR_MESSAGE = new HashMap<>();
    private static final String UNKNOW_ERROR = "editor.error-unknow";
    private static final String ERROR_MESSAGE_PARAM = "error_message";
    private static final String ERROR_NUMBER = "errParam";
    
    static {
        ERROR_MESSAGE.put(ValidationResult.EMPTY_FIELD, "editor.error-empty");
        ERROR_MESSAGE.put(ValidationResult.INVALID_EMAIL, "editor.error-email");
        ERROR_MESSAGE.put(ValidationResult.INVALID_YEAR, "editor.error-year");
        ERROR_MESSAGE.put(ValidationResult.INVALID_DATE, "editor.error-date");
        ERROR_MESSAGE.put(ValidationResult.INVALID_PHONE, "editor.error-phone");
        ERROR_MESSAGE.put(ValidationResult.INVALID_PASSWORD, "editor.error-password");
        ERROR_MESSAGE.put(ValidationResult.INVALID_NAME, "editor.error-name");
        ERROR_MESSAGE.put(ValidationResult.INVALID_ADDRESS, "editor.error-address");
        ERROR_MESSAGE.put(ValidationResult.INVALID_LICENSE_PLATE, "editor.error-license");
        ERROR_MESSAGE.put(ValidationResult.INVALID_MODEL, "editor.error-model");
        ERROR_MESSAGE.put(ValidationResult.INVALID_MAKE, "editor.error-make");
        ERROR_MESSAGE.put(ValidationResult.INVALID_PRICE, "editor.error-price");
        ERROR_MESSAGE.put(ValidationResult.INVALID_CAR, "editor.error-car");
        ERROR_MESSAGE.put(ValidationResult.INVALID_USER, "editor.error-user");
        ERROR_MESSAGE.put(ValidationResult.INVALID_ROLE, "editor.error-role");
        ERROR_MESSAGE.put(ValidationResult.PASSWORDS_NOT_MATCH, "editor.error-match");
        ERROR_MESSAGE.put(ValidationResult.SAME_LOGIN, "editor.error-login");
        ERROR_MESSAGE.put(ValidationResult.USER_NOT_FOUND, "editor.error-user-not-found");
        ERROR_MESSAGE.put(ValidationResult.REASON_TEXT_TOO_LONG, "editor.error-reason-too-long");
    }
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        if (request.getAttribute(ERROR_NUMBER) != null) {
            int errParam = (Integer) request.getAttribute(ERROR_NUMBER);
            String errorMessage = ERROR_MESSAGE.get(errParam);
            if (errorMessage == null)
                errorMessage = UNKNOW_ERROR;
            request.setAttribute(ERROR_MESSAGE_PARAM, errorMessage);
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {}
    
}
