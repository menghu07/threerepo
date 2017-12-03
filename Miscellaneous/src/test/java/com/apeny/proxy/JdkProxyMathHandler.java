package com.apeny.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by apeny on 2017/12/3.
 */
public class JdkProxyMathHandler implements InvocationHandler {

    private Object targetObject;

    public Object getProxy(Object object) {
        targetObject = object;
        Object o = Proxy.newProxyInstance(targetObject.getClass().getClassLoader(), targetObject.getClass().getInterfaces(), this);
        System.out.println("proxy class o = " + o.getClass());
        return o;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("jdk proxy math called before" + proxy.getClass() + " method name: " + method.getName());
        Object result = method.invoke(targetObject, args);
        System.out.println("jdk proxy math called after");
        return result;
    }
}
