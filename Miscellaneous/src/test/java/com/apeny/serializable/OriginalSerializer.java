package com.apeny.serializable;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import org.objenesis.strategy.StdInstantiatorStrategy;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by apeny on 2017/12/4.
 */
public class OriginalSerializer {
    public static void main(String[] args) {
//        testJdkTime();
        testKryoTime();
    }

    private static void testJdkTime() {
        long beginTime = System.currentTimeMillis();
        serialize();
        System.out.println("jdk serializable use time :" + (System.currentTimeMillis() - beginTime));
        beginTime = System.currentTimeMillis();
        deserializable();
        System.out.println("jdk desializable use time :" + (System.currentTimeMillis() - beginTime));
    }

    private static void testKryoTime() {
        long beginTime = System.currentTimeMillis();
        kryoSeriable();
        System.out.println("kryo serializable use time :" + (System.currentTimeMillis() - beginTime));
        beginTime = System.currentTimeMillis();
        kryoDeserializable();
        System.out.println("kryo desializable use time :" + (System.currentTimeMillis() - beginTime));
    }


    /**
     * 标准jdk序列化
     */
    private static void serialize() {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("simple.txt");
            ObjectOutputStream outputStream = new ObjectOutputStream(fileOutputStream);
            for (int i = 0; i < 1000_000; i++) {
                SimpleObject simpleObject = new SimpleObject();
                simpleObject.setAge(i);
                simpleObject.setName("i" + i);
                Map<String, Integer> map = new HashMap<>();
                map.put("i1", 1);
                map.put("i2", 2);
                simpleObject.setMap(map);
                outputStream.writeObject(simpleObject);
            }
            outputStream.flush();
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void deserializable() {
        try {
            FileInputStream fileInputStream = new FileInputStream("simple.txt");
            ObjectInputStream inputStream = new ObjectInputStream(fileInputStream);
            SimpleObject simpleObject = null;
            int i = 0;
            while (i < 1000_000 && (simpleObject = (SimpleObject) inputStream.readObject()) != null) {
                i++;
//                System.out.println(simpleObject.getMap() + " deserializable " + i++);
            }
            fileInputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * kryo序列化
     */
    private static void kryoSeriable() {
        Kryo kryo = new Kryo();
        kryo.setReferences(false);
        kryo.setRegistrationRequired(false);
        kryo.setInstantiatorStrategy(new StdInstantiatorStrategy());
        kryo.register(SimpleObject.class);
        try {
            Output output = new Output(new FileOutputStream("kryosimple.txt"));
            for (int i = 0; i < 1000_000; i++) {
                SimpleObject simpleObject = new SimpleObject();
                simpleObject.setAge(i);
                simpleObject.setName("i" + i);
                Map<String, Integer> map = new HashMap<>();
                map.put("i1", 1);
                map.put("i2", 2);
                simpleObject.setMap(map);
                kryo.writeObject(output, simpleObject);
            }
            output.flush();
            output.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void kryoDeserializable() {
        Kryo kryo = new Kryo();
        kryo.setInstantiatorStrategy(new StdInstantiatorStrategy());
        kryo.setRegistrationRequired(false);
        kryo.setReferences(false);
        try {
            Input input = new Input(new FileInputStream("kryosimple.txt"));
            int i = 0;
            while (i < 1000_000 && kryo.readObject(input, SimpleObject.class) != null) {
                i++;
            }
            input.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
