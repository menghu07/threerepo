package com.apeny.rabbitmq.spring.spittle;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by monis on 2018/12/23.
 */
public class Spittle implements Serializable {

    private static final long serialVersionUID = 12L;

    private long id;
    private String spitter;
    private String message;
    private Date postDate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSpitter() {
        return spitter;
    }

    public void setSpitter(String spitter) {
        this.spitter = spitter;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }
}
