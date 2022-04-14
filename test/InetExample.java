package test;

import java.io.*;
import java.net.*;

public class InetExample {
	
	static BufferedReader br;

	public static void main(String[] args) {
		
		br = new BufferedReader(new InputStreamReader(System.in));
		String host;
		
		try {

			InetAddress loacl = InetAddress.getLocalHost();
			System.out.println("로컬 호스트 이름 " + loacl.getHostName());
			System.out.println("로컬 호스트 IP " + loacl.getHostAddress());
			System.out.println("로컬 호스트 class "  );
			System.out.println("로컬 호스트 InetAddress " + loacl);
		}catch(IOException e) {
			System.out.println(e);
		}
		
		try {
			
			do {
				System.out.println("호스트 이름 및 IP 주소를 입력하세요");
				if((host = br.readLine()) != null) {
					InetAddress ia = InetAddress.getByName(host);
					System.out.println("원격 호스트 이름 " + ia.getHostName());
					System.out.println("원격 호스트 IP " + ia.getHostAddress());
					System.out.println("원격 호스트 class "  );
					System.out.println("원격 호스트 InetAddress " + ia);
				}
			}while(host != null);
			
		}catch(IOException e) {
			System.out.println(e);
		}
		

	}
	

}
