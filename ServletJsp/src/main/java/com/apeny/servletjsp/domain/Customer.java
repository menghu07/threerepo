package com.apeny.servletjsp.domain;

/**
 * 隐藏表单中使用的客户类
 * @author ahu
 *
 */
public class Customer {
    
    private Integer id;
    
    private String name;
    
    private String city;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "Customer [id=" + id + ", name=" + name + ", city=" + city + "]";
    }
}
