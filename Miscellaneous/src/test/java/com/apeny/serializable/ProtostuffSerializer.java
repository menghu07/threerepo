package com.apeny.serializable;

import io.protostuff.*;
import io.protostuff.runtime.RuntimeSchema;

import java.io.IOException;

/**
 * Created by apeny on 2017/12/6.
 */
public class ProtostuffSerializer {
    public static void main(String[] args) {
        serializeAndDeserialize();
        serializeAndDeserializeAnother();
        serializeAndDeserializeJson();
        serializeAndDeserializeXml();
    }

    /**
     * 相同的类
     */
    private static void serializeAndDeserialize() {
        Foo foo = new Foo("hize", 192, 82.2);
        System.out.println("serializable before: " + foo);
        Schema<Foo> schema = RuntimeSchema.getSchema(Foo.class);
        LinkedBuffer buffer = LinkedBuffer.allocate();
        //protostuff-core
        byte[] protostuff = null;
        try {
            protostuff = ProtostuffIOUtil.toByteArray(foo, schema, buffer);
        } finally {
            buffer.clear();
        }
        System.out.println("serializing: " + new String(protostuff) + ", length: " + protostuff.length);
        Foo desFoo = schema.newMessage();
        ProtostuffIOUtil.mergeFrom(protostuff, desFoo, schema);
        System.out.println("deserializable after: " + desFoo);
    }


    /**
     * 反序列化为不同的类
     */
    private static void serializeAndDeserializeAnother() {
        Foo foo = new Foo("hize", 192, 82.2);
        System.out.println("serializable before: " + foo);
        Schema<Foo> schema = RuntimeSchema.getSchema(Foo.class);
        LinkedBuffer buffer = LinkedBuffer.allocate();
        //protostuff-core
        byte[] protostuff = null;
        try {
            protostuff = ProtostuffIOUtil.toByteArray(foo, schema, buffer);
        } finally {
            buffer.clear();
        }
        System.out.println("serializing: " + new String(protostuff) + ", length: " + protostuff.length);
        Schema<DesFoo> desFooSchema = RuntimeSchema.getSchema(DesFoo.class);
        DesFoo desFoo = desFooSchema.newMessage();
        ProtostuffIOUtil.mergeFrom(protostuff, desFoo, desFooSchema);
        System.out.println("deserializable after: " + desFoo);
    }

    /**
     * 反序列化为不同的类
     */
    private static void serializeAndDeserializeJson() {
        Foo foo = new Foo("hize", 192, 82.2);
        System.out.println("json serializable before: " + foo);
        Schema<Foo> schema = RuntimeSchema.getSchema(Foo.class);
        LinkedBuffer buffer = LinkedBuffer.allocate();
        //protostuff-json
        boolean numeric = true;
        byte[] protostuff = null;
        try {
            protostuff = JsonIOUtil.toByteArray(foo, schema, numeric, buffer);
        } finally {
            buffer.clear();
        }
        System.out.println("serializing: " + new String(protostuff) + ", length: " + protostuff.length);
        Schema<DesFoo> desFooSchema = RuntimeSchema.getSchema(DesFoo.class);
        DesFoo desFoo = desFooSchema.newMessage();
        try {
            JsonIOUtil.mergeFrom(protostuff, desFoo, desFooSchema, numeric);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("json deserializable after: " + desFoo);
    }


    /**
     * 只能用Foo反序列化
     */
    private static void serializeAndDeserializeXml() {
        Foo foo = new Foo("hize", 192, 82.2);
        System.out.println("xml serializable before: " + foo);
        Schema<Foo> schema = RuntimeSchema.getSchema(Foo.class);
        //protostuff-xml
        boolean numeric = true;
        byte[] protostuff = null;
        protostuff = XmlIOUtil.toByteArray(foo, schema);
        System.out.println("serializing: " + new String(protostuff) + ", length: " + protostuff.length);
        Schema<Foo> desFooSchema = RuntimeSchema.getSchema(Foo.class);
        Foo desFoo = desFooSchema.newMessage();
        XmlIOUtil.mergeFrom(protostuff, desFoo, desFooSchema);
        System.out.println("xml deserializable after: " + desFoo);
    }
}

class Foo {
    private String name;
    private int mount;
    private double money;

    public Foo(String name, int mount, double money) {
        this.name = name;
        this.mount = mount;
        this.money = money;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMount() {
        return mount;
    }

    public void setMount(int mount) {
        this.mount = mount;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    @Override
    public String toString() {
        return "Foo{" +
                "name='" + name + '\'' +
                ", mount=" + mount +
                ", money=" + money +
                '}';
    }
}

class DesFoo {
    private String name;
    private int mount;
    private double money;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMount() {
        return mount;
    }

    public void setMount(int mount) {
        this.mount = mount;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DesFoo{");
        sb.append("name='").append(name).append('\'');
        sb.append(", mount=").append(mount);
        sb.append(", money=").append(money);
        sb.append('}');
        return sb.toString();
    }
}
