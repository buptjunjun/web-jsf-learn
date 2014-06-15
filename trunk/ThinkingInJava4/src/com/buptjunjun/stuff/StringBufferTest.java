package com.buptjunjun.stuff;

public class StringBufferTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
			StringBuffer sb = new StringBuffer("ababcab");
			System.out.println(sb.indexOf("ab"));
			System.out.println(sb.substring(0,2));
			System.out.println(sb.lastIndexOf("ab"));
			System.out.println(sb.lastIndexOf("ab")+"ab".length());
			System.out.println(sb.substring(sb.lastIndexOf("ab"), sb.length()));
	}

}
