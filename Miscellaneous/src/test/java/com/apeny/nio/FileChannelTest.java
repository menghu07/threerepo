package com.apeny.nio;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by apeny on 2018/2/4.
 */
public class FileChannelTest {
    public static void main(String[] args) {
        readRandomFile();
    }

    private static void readRandomFile() {
        try {
            RandomAccessFile file = new RandomAccessFile("src/main/resources/config/applicationContext.xml", "rw");
            FileChannel fileChannel = file.getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate(256);
            int bytesCount = fileChannel.read(byteBuffer);
            System.out.println("byte count: " + bytesCount);
            while (bytesCount != -1) {
                byte[] bytes = new byte[byteBuffer.position()];
                byteBuffer.flip();
                int i = 0;
                while (byteBuffer.hasRemaining()) {
                    byte currentByte = byteBuffer.get();
                    if (currentByte == '\n') {
                        System.out.println(new String(bytes, 0, i));
                        byteBuffer.compact();
                        break;
                    } else {
                        bytes[i++] = currentByte;
                    }
                }
                bytesCount = fileChannel.read(byteBuffer);
            }
            byte[] bytes = new byte[byteBuffer.position()];
            byteBuffer.flip();
            int i = 0;
            while (byteBuffer.hasRemaining()) {
                byte currentByte = byteBuffer.get();
                bytes[i++] = currentByte;
            }
            System.out.print(new String(bytes, 0, i));
            file.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
