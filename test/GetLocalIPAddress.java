package test;

import java.net.*;

public class GetLocalIPAddress {

	public static void main(String[] args) {
		
		try {
			InetAddress local = InetAddress.getLocalHost();
			byte[] la = local.getAddress();
			
			for(int i=0;i<la.length;i++) {
				System.out.print(la[i]<0 ? la[i]+256 : la[i]);
				System.out.print(".");
			}
		}catch(UnknownHostException e) {
			System.out.println(e);
		}

	}

}
