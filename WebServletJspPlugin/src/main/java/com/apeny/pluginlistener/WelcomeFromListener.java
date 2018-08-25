package com.apeny.pluginlistener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by apeny on 2018/5/30.
 */
public class WelcomeFromListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("hello, i'm from plugin Welcome Listener");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("bye, i'm from plugin Welcome Listener");
    }
}
