package test;

import java.net.*;

public class GetLocalAddress {

	public static void main(String[] args) {

		try {
			InetAddress ia = InetAddress.getLocalHost();
			System.out.println(ia.getLocalHost());
			System.out.println(ia);
		}catch(UnknownHostException e) {
			System.out.println(e);
		}

	}

}
