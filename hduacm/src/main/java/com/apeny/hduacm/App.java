package com.apeny.hduacm;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        System.out.println("total memory: " + Runtime.getRuntime().totalMemory() +
                ", free memory: " +Runtime.getRuntime().freeMemory() + ", max memory: " + Runtime.getRuntime().maxMemory());
    }
}
