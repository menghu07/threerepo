package com.apeny.servletjsp.chapter07;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;

/**
 * Created by ahu on 2017年07月29日.
 */
@WebListener
public class ServletRequestListenerImpl implements ServletRequestListener {
    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        System.out.println("i am a servlet request destroyed" + sre.getServletRequest() + " class: " + sre.getServletRequest().getClass());
    }

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        System.out.println("i am a servlet request initialize " + sre.getServletRequest());
        System.out.println(sre.getServletRequest().getAttributeNames());
    }
}
