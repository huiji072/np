package test;

import java.io.*;
import java.net.*;

public class GetNameAddress {
	
	static BufferedReader br;
	
	
	public static void main(String[] args) {
		
		br = new BufferedReader(new InputStreamReader(System.in));
		String host;
		
		try {
			if((host = br.readLine()) != null) {
				InetAddress ia = InetAddress.getByName(host);
				System.out.println("ȣ��Ʈ �̸��� " + ia.getHostName());
				System.out.println("IP �ּҴ� " + ia.getHostAddress());
			}
			
			InetAddress local = InetAddress.getLocalHost();
			System.out.println("ȣ��Ʈ �̸��� " + local.getHostName());
			System.out.println("IP �ּҴ� " + local.getHostAddress());
		}catch(UnknownHostException e) {
			System.out.println(e);
		}catch(IOException e) {
			System.out.println(e);
		}


	}

}
