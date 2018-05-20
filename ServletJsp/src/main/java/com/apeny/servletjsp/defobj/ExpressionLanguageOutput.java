package com.apeny.servletjsp.defobj;

import java.io.Serializable;

/**
 * Created by apeny on 2018/5/20.
 */
public class ExpressionLanguageOutput implements Serializable {

    private static final long serialVersionUID = 23345L;

    public void outNoReturn() {
        System.out.println("just see can call.");
    }

    public String outWithReturn() {
        System.out.println("has return value.");
        return "you can see> " + this;
    }
}
