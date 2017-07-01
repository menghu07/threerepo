package com.apeny.servletjsp.function;

public class StringFunctions {
	
	public static String reverseString(String s) {
		return new StringBuffer(s).reverse().toString();
	}
}
