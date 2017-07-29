package com.apeny.servletjsp.chapter07;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.concurrent.atomic.AtomicInteger;

@WebListener
public class HttpSessionListenerImpl implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		ServletContext context = se.getSession().getServletContext();
		AtomicInteger counter = (AtomicInteger) context.getAttribute("counter");
		System.out.println("create old session count " + counter);
		int newValue = counter.incrementAndGet();
		System.out.println("create new session count " + newValue);
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		ServletContext context = se.getSession().getServletContext();
		AtomicInteger counter = (AtomicInteger) context.getAttribute("counter");
		System.out.println("destroy old session count " + counter);
		int newValue = counter.decrementAndGet();
		System.out.println("destroy new session count " + newValue);
		
	}
}
