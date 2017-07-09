package com.apeny.servletjsp.chapter07;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * servletcontext listener
 * @author ahu
 *
 */
@WebListener
public class ServletContextListenerImpl implements ServletContextListener {

	private ServletContext context;
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		Map<String, String> countries = new HashMap<>();
		countries.put("ca", "Canada");
		countries.put("us", "United States");
		countries.put("cn", "china");
		context = sce.getServletContext();
		context.setAttribute("countries", countries);
		System.out.println("ServletContext Listener initialized");
		AtomicInteger sessionCounter = new AtomicInteger();
		context.setAttribute("counter", sessionCounter);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println("ServlectContext Listener destroyed");
	}

}
