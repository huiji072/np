package test;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class ia {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		InetAddress local;
		try {
			local = InetAddress.getLocalHost();
			System.out.println(local);
			
			InetAddress addr = InetAddress.getByName("www.gwnu.ac.kr");
			System.out.println(addr);
			
			String s = "203.255.216.139";
			InetAddress ia =  InetAddress.getByName("203.255.216.139");
			System.out.println(ia);
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
