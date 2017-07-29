package com.apeny.servletjsp.chapter07.attributelster;

import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.annotation.WebListener;

/**
 * Created by ahu on 2017年07月29日.
 */
@WebListener
public class ServletServletContextAttributeListener implements ServletContextAttributeListener{

    public void attributeAdded(ServletContextAttributeEvent event) {
        String name = event.getName();
        Object value = event.getValue();
        System.out.println("added to ServletContext name " + name + " value " + value);
    }

    public void attributeRemoved(ServletContextAttributeEvent event) {
        String name = event.getName();
        Object value = event.getValue();
        System.out.println("removed to ServletContext name " + name + " value " + value);
    }

    public void attributeReplaced(ServletContextAttributeEvent event) {
        String name = event.getName();
        Object value = event.getValue();
        System.out.println("replaced to ServletContext name " + name + " value " + value);
    }
}
