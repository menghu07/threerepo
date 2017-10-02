package com.apeny.jvmpractice.methodregion;

import net.sf.cglib.beans.BeanGenerator;
import net.sf.cglib.beans.BeanMap;

import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * maven依赖关系，默认有传递依赖&lt;dependency&gt;下optional=false
 * 子项目可以调用传递依赖的类
 * 发现引用没有回收。。。。。！！！！
 *
 * Created by ahu on 2017年10月02日.
 */
//TODO ..引用没回收
public class CglibBeanClass {

    private BeanMap beanMap;

    public static void main(String[] args) {
        CglibBeanClass bean1 = createBean();
        CglibBeanClass bean2 = createBean();
        System.out.println("equals: " + (bean1.getClass() == bean2.getClass()));
        int i = 0;
        try {
            for (; i < 900_000_000; i++) {
                CglibBeanClass b = createBean();
                b = null;
            }
        } catch (Throwable e) {
            e.printStackTrace();
            System.out.println("total create class file: " + i + " " + e);
        }
    }

    public CglibBeanClass generate(HashMap<String, Object> properties) {
        BeanGenerator generator = new BeanGenerator();
        generator.setSuperclass(CglibBeanClass.class);
        CglibBeanClass bean = (CglibBeanClass) generator.create();
        BeanMap beanMap = BeanMap.create(bean);
        for (Map.Entry<String, Object> entry : properties.entrySet()) {
            generator.addProperty(entry.getKey(), (Class) entry.getValue());
        }
        bean.beanMap = beanMap;
        return bean;
    }

    public static CglibBeanClass createBean() {
        CglibBeanClass generator = new CglibBeanClass();
        HashMap<String, Object> propertyMap = new HashMap<>();
        propertyMap.put("id", Integer.class);
        propertyMap.put("name", String.class);
        CglibBeanClass bean = generator.generate(propertyMap);
        bean.beanMap.put("id", 12);
        bean.beanMap.put("name", "new name");
        WeakReference<CglibBeanClass> reference = new WeakReference<CglibBeanClass>(bean);
        Class subclass = bean.getClass();
//        System.out.println("class subclass>>> " + subclass);

        //所有的方法
        Method[] methods = subclass.getMethods();
        for (Method method : methods) {
//            System.out.println("methodName : " + method.getName());
        }
        return reference.get();
    }
}
