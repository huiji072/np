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
			System.out.println("���� ȣ��Ʈ �̸� " + loacl.getHostName());
			System.out.println("���� ȣ��Ʈ IP " + loacl.getHostAddress());
			System.out.println("���� ȣ��Ʈ class "  );
			System.out.println("���� ȣ��Ʈ InetAddress " + loacl);
		}catch(IOException e) {
			System.out.println(e);
		}
		
		try {
			
			do {
				System.out.println("ȣ��Ʈ �̸� �� IP �ּҸ� �Է��ϼ���");
				if((host = br.readLine()) != null) {
					InetAddress ia = InetAddress.getByName(host);
					System.out.println("���� ȣ��Ʈ �̸� " + ia.getHostName());
					System.out.println("���� ȣ��Ʈ IP " + ia.getHostAddress());
					System.out.println("���� ȣ��Ʈ class "  );
					System.out.println("���� ȣ��Ʈ InetAddress " + ia);
				}
			}while(host != null);
			
		}catch(IOException e) {
			System.out.println(e);
		}
		

	}
	

}
