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
import org.apache.log4j.Logger;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class ErrorFilter implements Filter {
    
    private static final Map<Integer, String> ERROR_MESSAGE = new HashMap<>();
    
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
    }
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        if (request.getAttribute("errParam") != null) {
            int errParam = (Integer) request.getAttribute("errParam");
            String errorMessage = ERROR_MESSAGE.get(errParam);
            if (errorMessage == null)
                errorMessage = "editor.error-unknow";
            request.setAttribute("error_message", errorMessage);
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {}
    
}
