package test;

import java.io.*;
import java.net.*;

public class GetObject {

	public static void main(String[] args) {
		
		InetAddress ia;
		try {
			ia = InetAddress.getByName("www.gwnu.ac.kr");

			System.out.println(ia);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
