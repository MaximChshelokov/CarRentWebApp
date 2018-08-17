package com.mv.schelokov.carent.listeners;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Web application lifecycle listener.
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class WebAppListener implements ServletContextListener {
    
    private static final String ROOT = "root";

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        System.setProperty(ROOT, context.getRealPath("/"));
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}
