package com.apeny.nio;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by apeny on 2018/2/4.
 */
public class FileHole {
    public static void main(String[] args) throws Exception {
        createFile();
    }

    private static void createFile() throws Exception {
        File file = File.createTempFile("filehole", null);
        RandomAccessFile accessFile = new RandomAccessFile(file, "rw");
        FileChannel fileChannel = accessFile.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(100);
        putData(0, byteBuffer, fileChannel);
        putData(5000000, byteBuffer, fileChannel);
        putData(5000, byteBuffer, fileChannel);
        System.out.println("wrote temp file: " + file.getPath() + " size=" + fileChannel.size());
        fileChannel.position(8000000);
        byteBuffer.clear();
        fileChannel.read(byteBuffer);
        System.out.println(byteBuffer);
        fileChannel.close();
        accessFile.close();
    }

    private static void putData(long position, ByteBuffer byteBuffer, FileChannel fileChannel) throws IOException {
        String string = "*<---- location: " + position;
        byteBuffer.clear();
        byteBuffer.put(string.getBytes());
        byteBuffer.flip();
        fileChannel.position(position);
        fileChannel.write(byteBuffer);
    }
}
