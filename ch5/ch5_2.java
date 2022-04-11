package ch5;

import java.io.*;
import java.net.*;

public class ch5_2 {

	public static void main(String[] args) {
		
		String hostname;
		BufferedReader br;
		br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("ȣ��Ʈ �̸� �Ǵ� IP �ּҸ� �Է��ϼ���.");
		
		try {
			if((hostname = br.readLine()) != null) { //Ű����κ��� �Է�
				InetAddress addr = InetAddress.getByName(hostname);
				System.out.println("ȣ��Ʈ �̸��� " + addr.getHostName());
				System.out.println("IP �ּҴ� " + addr.getHostAddress());
			}
			
			InetAddress laddr = InetAddress.getLocalHost();
			System.out.println("����ȣ��Ʈ�� �̸��� " + laddr.getHostName());
			System.out.println("���� IP �ּҴ� " + laddr.getHostAddress());
		}catch(UnknownHostException e) {
			System.out.println(e);
		}catch(IOException e) {
			System.out.println(e);
		}

	}

}
