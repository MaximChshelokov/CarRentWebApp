package com.mv.schelokov.car_rent.listeners;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Web application lifecycle listener.
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class WebAppListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        System.setProperty("root", context.getRealPath("/"));
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}
