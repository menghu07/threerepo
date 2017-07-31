package com.apeny.servletjsp.domain;

import javax.servlet.http.HttpSessionActivationListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import javax.servlet.http.HttpSessionEvent;
import java.io.Serializable;

/**
 * 书
 * @author mengqh
 *
 */
public class Book implements HttpSessionBindingListener, HttpSessionActivationListener, Serializable {
    private String isbn;
    private String title;
    private Double price;
    public Book() {}
    public Book(String isbn, String title, Double price) {
        this.isbn = isbn;
        this.title = title;
        this.price = price;
    }
    public String getIsbn() {
        return isbn;
    }
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }
    @Override
    public String toString() {
        return "Book [isbn=" + isbn + ", title=" + title + ", price=" + price + "]";
    }

    @Override
    public void valueBound(HttpSessionBindingEvent event) {
        System.out.println("value book is bound: " + event.getName() + " value: " + event.getValue() + "value == this"
                + (event.getValue() == this));
    }

    @Override
    public void valueUnbound(HttpSessionBindingEvent event) {
        System.out.println("value book is unbound: " + event.getName() + " value: " + event.getValue());
    }

    @Override
    public void sessionWillPassivate(HttpSessionEvent se) {
        System.out.println("before session serialized: " + se.getSession());
    }

    @Override
    public void sessionDidActivate(HttpSessionEvent se) {
        System.out.println("after session serialized: " + se.getSession());
    }
}
