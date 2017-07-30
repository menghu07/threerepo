package com.apeny.servletjsp.chapter07.attributelster;

import javax.servlet.ServletRequestAttributeEvent;
import javax.servlet.ServletRequestAttributeListener;
import javax.servlet.annotation.WebListener;

/**
 * Created by ahu on 2017/7/29.
 */
@WebListener
public class ServletHttpRequestAttributeListener implements ServletRequestAttributeListener {
    @Override
    public void attributeAdded(ServletRequestAttributeEvent srae) {
        System.out.println("a request attribute added" + "name: " + srae.getName() + ", value: " + srae.getValue());
    }

    @Override
    public void attributeRemoved(ServletRequestAttributeEvent srae) {
        System.out.println("a request attribute removed");
    }

    @Override
    public void attributeReplaced(ServletRequestAttributeEvent srae) {
        System.out.println("old value: " + srae.getServletRequest().getAttribute(srae.getName())
                + "a request attribute replaced: name=" + srae.getName() + ",value=" + srae.getValue());
    }
}
