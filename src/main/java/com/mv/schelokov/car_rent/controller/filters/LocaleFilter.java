package com.mv.schelokov.car_rent.controller.filters;

import java.io.IOException;
import java.util.Locale;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.jstl.core.Config;
import org.apache.log4j.Logger;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class LocaleFilter implements Filter {
    public static final Logger log = Logger.getLogger(LocaleFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, 
            FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        Cookie[] cookies = req.getCookies();
        String localeString = null;
        if (cookies != null)
        {
            for (Cookie cookie : cookies) {
                if ("lang".equals(cookie.getName())) {
                    localeString = cookie.getValue();
                }
            }
        }
        
        if (localeString == null) {
            localeString = "en";
        }
        Locale locale = new Locale.Builder().setLanguage(localeString).setRegion("US").build();
        Config.set(req.getSession(), Config.FMT_LOCALE, locale);
        log.debug(String.format("Locale was set, %s", locale.toString()));
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {}
    
}
