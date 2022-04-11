package ch5;

import java.io.*;
import java.net.*;

public class ch5_5 {

	public static void main(String[] args) {
		
		String hostname;
		BufferedReader br;
		br = new BufferedReader(new InputStreamReader(System.in));
		printLocalAddress(); //����ȣ��Ʈ�� �̸� �� IP �ּ� ���
		
		try {
			do {
				System.out.println("ȣ��Ʈ �̸� �� IP �ּҸ� �Է��ϼ���");
				if((hostname = br.readLine()) != null) {
					printRemoteAddress(hostname); //����ȣ��Ʈ�� �ּ� ���
				}
			}while(hostname != null);
			System.out.println("���α׷��� �����մϴ�.");
		}catch(IOException ex) {
			System.out.println("�Է� ����!");
		}

	}
	
	static void printLocalAddress() {
		try {
			InetAddress local = InetAddress.getLocalHost();
			System.out.println("ȣ��Ʈ�̸� : " + local.getHostName());
			System.out.println("���� IP �ּ� : " + local.getHostAddress());
			System.out.println("���� ȣ��Ʈ class : " + ipClass(local.getAddress()));
			System.out.println("���� ȣ��Ʈ Inet Address : " + local.toString());
		}catch(UnknownHostException e) {
			System.out.println(e);
		}
	}
	
	static void printRemoteAddress(String hostname) {
		try {
			System.out.println("ȣ��Ʈ�� ã�� �ֽ��ϴ�.  " + hostname);
			InetAddress machine = InetAddress.getByName(hostname);
			System.out.println("���� ȣ��Ʈ �̸� : " + machine.getHostName());
			System.out.println("���� ȣ��Ʈ IP : " + machine.getHostAddress());
			System.out.println("��� ȣ��Ʈ class : " + ipClass(machine.getAddress()));
			System.out.println("���� ȣ��Ʈ InetAddress" + machine.toString());
		}catch(UnknownHostException e) {
			System.out.println(e);
		}
	}
	
	static char ipClass(byte[] ip) {
		int highByte = 0xff & ip[0];
		return(highByte<128 ? 'A' : (highByte<192) ? 'B' : (highByte<224) ? 'C' :
			(highByte<240) ? 'D' : 'E');
	}

}
