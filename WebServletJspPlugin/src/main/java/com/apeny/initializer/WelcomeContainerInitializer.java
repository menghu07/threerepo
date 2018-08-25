package com.apeny.initializer;

import com.apeny.pluginlistener.WelcomeFromListener;
import com.apeny.pluginservlet.WelcomeFromPluginServlet;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.HandlesTypes;
import java.util.Set;

/**
 * Created by apeny on 2018/5/30.
 */
@HandlesTypes(WelcomeFromPluginServlet.class)
public class WelcomeContainerInitializer implements ServletContainerInitializer {

    @Override
    public void onStartup(Set<Class<?>> set, ServletContext servletContext) throws ServletException {
        System.out.println("welcome Container Initializer");
        ServletRegistration registration = servletContext.addServlet("welcomeservlet", WelcomeFromPluginServlet.class);
        registration.addMapping("/welcomeservlet.do");
        servletContext.addListener(WelcomeFromListener.class);
        System.out.println("welcome Container startup end.");
    }
}
