package com.apeny.servletjsp.chapter07.attributelster;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

/**
 * Created by ahu on 2017年07月29日.
 */
@WebListener
public class ServletHttpSessionAttributeListener implements HttpSessionAttributeListener {
    @Override
    public void attributeAdded(HttpSessionBindingEvent event) {
        System.out.println("a session attribute added");
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent event) {
        System.out.println("a session attribute removed");
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent event) {
        System.out.println("a session attribute replaced");
    }
}
