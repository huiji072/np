package test;

import java.util.StringTokenizer;

public class token {
	private static final String SEPARATOR = "|";
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String s = "aaa | hihi";
		
		StringTokenizer st = new StringTokenizer(s, SEPARATOR);
		System.out.println(st.nextToken()); //aaa
		System.out.println(st.nextToken()); //hihi
	}

}
