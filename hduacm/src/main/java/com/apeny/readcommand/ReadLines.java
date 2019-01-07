package com.apeny.readcommand;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by monis on 2018/12/15.
 */
public class ReadLines {
    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String command;
        while ((command = br.readLine()) != null) {
            try {
                System.out.println("read lines:" + command + ";");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.print("main is end");
    }
}
