package com.apeny.nio;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.FileChannel;
import java.util.EnumSet;

/**
 * Created by apeny on 2018/2/4.
 */
public class TransferChannel {
    public static void main(String[] args) {
//        transferFrom();
        transferTo();
    }

    private static void transferFrom() {
        try {
            RandomAccessFile file = new RandomAccessFile("src/main/resources/docs/ConcurrentFile.html", "rw");
            RandomAccessFile toWriteFile = new RandomAccessFile("src/main/resources/docs/ConcurrentFile.html.backup", "rw");
            FileChannel fileChannel = file.getChannel();
            FileChannel toTransferChannel = toWriteFile.getChannel();
            long size = fileChannel.size();
            long position = 0;
            toTransferChannel.transferFrom(fileChannel, position, size);
            EnumSet.of(FileEnum.READ);
            fileChannel.close();
            toTransferChannel.close();
            file.close();
            toWriteFile.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void transferTo() {
        try {
            RandomAccessFile file = new RandomAccessFile("src/main/resources/docs/ConcurrentFile.html", "rw");
            RandomAccessFile toWriteFile = new RandomAccessFile("src/main/resources/docs/ConcurrentFile.html.backup", "rw");
            FileChannel fileChannel = file.getChannel();

            FileChannel toTransferChannel = toWriteFile.getChannel();
            long size = fileChannel.size();
            long position = 0;
            fileChannel.transferTo(position, size, toTransferChannel);
            fileChannel.close();
            toTransferChannel.close();
            file.close();
            toWriteFile.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    enum  FileEnum {
        READ, WRITE, READWRITE
    }
}
