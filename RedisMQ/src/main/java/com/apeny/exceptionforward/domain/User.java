package com.apeny.exceptionforward.domain;

/**
 *
 * @author monis
 * @date 2019/1/5
 */
public class User {
    private String id;

    private String name;

    private String lastTime;

    private String password;

    private User() {

    }
    public User(String id, String name, String lastTime, String password) {
        this.id = id;
        this.name = name;
        this.lastTime = lastTime;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
