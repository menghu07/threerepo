package com.apeny.servletjsp.domain;

/**
 * 书
 * @author mengqh
 *
 */
public class Book {
    private String isbn;
    private String title;
    private Double price;
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
}
