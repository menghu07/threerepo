package com.apeny.proxy;

/**
 * Created by apeny on 2017/12/3.
 */
public class ProxyMain {
    public static void main(String[] args) {
        IMath math = (IMath) new JdkProxyMathHandler().getProxy(new Math());
        int xy = math.add(10, 20);
        System.out.println("main xy = " + xy + " ; proxy = " + math.getClass());
    }
}
