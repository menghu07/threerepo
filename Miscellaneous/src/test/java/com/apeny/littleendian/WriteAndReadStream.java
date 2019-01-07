package com.apeny.littleendian;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * CPU小端模式
 * Created by ahu on 2017年09月16日.
 */
public class WriteAndReadStream {
    public static void main(String[] args) throws IOException {
        OutputStream outputStream = new FileOutputStream("file1");
        outputStream.write(128);
        int i128 = 128;
        System.out.println("hex string 128: " + Integer.toHexString(i128));
        outputStream.flush();
        outputStream.close();
        InputStream inputStream = new FileInputStream("file1");
        byte[] bytes = new byte[128];
        inputStream.read(bytes);
        inputStream.close();
        System.out.println("Little Endian: " + Arrays.toString(bytes));
        byte[] bytes1 = {-128, 0, 0, 0};
        //<< 算数左移或按位与会先转换为 int 再操作
        System.out.println((((Object)(((byte)(-1)) << 0)) ) + " & " + ((Object)(((byte)(-2)) + 0xFF) instanceof Integer));
        int ii128 = getInt(bytes1, 0);
        System.out.println("is i128? " + ii128);
    }

    /**
     * 这样得到的int不是真正的int
     * @param b
     * @param off
     * @return
     */
    static int getInt(byte[] b, int off) {
        return ((b[off + 3] & 0xFF)      ) +
                ((b[off + 2] & 0xFF) <<  8) +
                ((b[off + 1] & 0xFF) << 16) +
                ((b[off    ]       ) << 24);
    }
}