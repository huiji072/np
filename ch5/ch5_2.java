package ch5;

import java.io.*;
import java.net.*;

public class ch5_2 {

	public static void main(String[] args) {
		
		String hostname;
		BufferedReader br;
		br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("호스트 이름 또는 IP 주소를 입력하세요.");
		
		try {
			if((hostname = br.readLine()) != null) { //키보드로부터 입력
				InetAddress addr = InetAddress.getByName(hostname);
				System.out.println("호스트 이름은 " + addr.getHostName());
				System.out.println("IP 주소는 " + addr.getHostAddress());
			}
			
			InetAddress laddr = InetAddress.getLocalHost();
			System.out.println("로컬호스트의 이름은 " + laddr.getHostName());
			System.out.println("로컬 IP 주소는 " + laddr.getHostAddress());
		}catch(UnknownHostException e) {
			System.out.println(e);
		}catch(IOException e) {
			System.out.println(e);
		}

	}

}
