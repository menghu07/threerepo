package com.apeny.nio;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by apeny on 2018/2/4.
 */
public class ScatterAndGather {

    public static void main(String[] args) {
//        scatter();
        gather();
    }

    /**
     * 将channel中数据写入到多个buffer
     */
    private static void scatter() {
        try {
            RandomAccessFile file = new RandomAccessFile("src/main/resources/config/applicationContext.xml", "rw");
            FileChannel fileChannel = file.getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate(256);
            ByteBuffer bodyBuffer = ByteBuffer.allocate(512);
            fileChannel.read(new ByteBuffer[]{byteBuffer, bodyBuffer});
            byteBuffer.flip();
            int limitByteBuffer = byteBuffer.limit();
            if (byteBuffer.hasRemaining()) {
                byte[] bytes = new byte[limitByteBuffer];
                byteBuffer.get(bytes);
                System.out.println(new String(bytes));
            }
            bodyBuffer.flip();
            int bodyLimit = bodyBuffer.limit();
            if (bodyBuffer.hasRemaining()) {
                System.out.println("-------------------分割线------------------");
                byte[] bytes = new byte[bodyLimit];
                bodyBuffer.get(bytes);
                System.out.println(new String(bytes));
            }
            file.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将多个buffer中数据写入到channel中
     */
    private static void gather() {
        try {
            RandomAccessFile file = new RandomAccessFile("src/main/resources/docs/ConcurrentFile.html", "rw");
            RandomAccessFile toWriteFile = new RandomAccessFile("src/main/resources/docs/ConcurrentFile.html.backup", "rw");
            FileChannel fileChannel = file.getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate(256);
            ByteBuffer bodyBuffer = ByteBuffer.allocate(512);
            //预读测试
            while (bodyBuffer.hasRemaining()) {
                System.out.print(bodyBuffer.get());
            }
            System.out.println("---------------------预读结束-----------------------");
            bodyBuffer.clear();
            fileChannel.read(new ByteBuffer[]{byteBuffer, bodyBuffer});
            byteBuffer.flip();
            bodyBuffer.flip();
            toWriteFile.getChannel().write(new ByteBuffer[]{byteBuffer, bodyBuffer});
            file.close();
            toWriteFile.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
