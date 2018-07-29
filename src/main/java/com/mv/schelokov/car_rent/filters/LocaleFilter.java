package com.mv.schelokov.car_rent.filters;

import com.mv.schelokov.car_rent.consts.Cookies;
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

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class LocaleFilter implements Filter {

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
                if (Cookies.LOCALE.equals(cookie.getName())) {
                    localeString = cookie.getValue();
                }
            }
        }
        
        if (localeString == null || !("en".equals(localeString)
                || "ru".equals(localeString))) {
            localeString = "en";
        }
        String region = "US";
        if ("ru".equals(localeString))
            region = "RU";
        Locale locale = new Locale(localeString, region);
        Config.set(req.getSession(), Config.FMT_LOCALE, locale);
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {}
    
}
