package ch5;

import java.io.*;
import java.net.*;

public class ch5_4 {

	public static void main(String[] args) {

		String hostname;
		String hostname2 = "www.naver.com";
		InetAddress addr2;
		BufferedReader br;
		br = new BufferedReader(new InputStreamReader(System.in));

		System.out.println("ȣ��Ʈ�̸� �Ǵ� IP�ּҸ� �Է��ϼ���.");
		try {
			if((hostname = br.readLine()) != null){
				InetAddress addr = InetAddress.getByName(hostname);
				addr2 = InetAddress.getByName(hostname2);
//				equals
				if(addr.equals(addr2)) {
					System.out.println("�� ���� IP�ּҰ� �����ϴ�.");
				}else {
					System.out.println("�� ���� IP�ּҰ� �ٸ��ϴ�.");
				}
				
//				hashCode
				System.out.println("hashCode : " + addr.hashCode());
				
//				toString
				System.out.println("ȣ��Ʈ�̸�/Ip�ּ� : " + addr.toString());
			}
		}catch(UnknownHostException e) {
			System.out.println(e);
		}catch(IOException e) {
			System.out.println(e);
		}
		
	}

}
