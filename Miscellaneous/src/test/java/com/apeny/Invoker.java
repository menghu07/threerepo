package com.apeny;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by apeny on 2017/12/11.
 */
public class Invoker {

    public static void main(String[] args) {
        toInvokeString();
    }

    private static void toInvokeString() {
        Method[] methods = InvokerToString.class.getMethods();
        for (Method method : methods) {
            System.out.println("method: " + method + " declared class: " + method.getDeclaringClass());
            try {
                if ("toString".equals(method.getName())) {
                    System.out.println(method.invoke(new InvokerToString()));
                    System.out.println(new InvokerToString().toString());
                }
                if ("getId".equals(method.getName())) {
                    System.out.println("InvokeToString id " + method.invoke(new InvokerToString()));
//!                    System.out.println("InvokeOther id " + method.invoke(new InvokeOther()));
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        try {
            Method method1 = Object.class.getMethod("toString");
            System.out.println("Declared"+ method1.getDeclaringClass() + "Object ToString." + method1.invoke(new InvokerToString()));
            Method method2 = InvokeOther.class.getMethod("toString");
            System.out.println("Declared"+ method2.getDeclaringClass() +"InvokeOther.toString: " + method2.invoke(new InvokerToString()));
            Method method3 = InvokerToString.class.getMethod("toString");
            System.out.println("Declared"+ method3.getDeclaringClass() +"InvokeToString.toString: " + method2.invoke(new InvokerToString()));

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}

class InvokerToString extends InvokeOther {
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

class InvokeOther {
    private int id = 1020;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private void notSeen() {

    }

    protected void protectMethod() {

    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("InvokeOther{");
        sb.append("id=").append(id);
        sb.append('}');
        return sb.toString();
    }
}
