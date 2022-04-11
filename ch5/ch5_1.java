package ch5;

import java.net.*;

public class ch5_1 {

	public static void main(String[] args) {

		if(args.length != 1) {
			System.out.println("ȣ��Ʈ �̸��� �Է��ϼ���!");
			return;
		}
		
//		���ڿ� ȣ��Ʈ�ּ�/IP�ּ� 
		try {
			InetAddress address1 = InetAddress.getByName(args[0]);
			System.out.println(address1);
		}catch(UnknownHostException e) {
			System.out.println("������ ȣ��Ʈ�� ã�� �� �����ϴ�.");
		}
		
//		�μ��� ������ ȣ��Ʈ �̸��� �ش��ϴ� ��� InetAddress �迭 ��ü
		try {
			InetAddress[] address2 = InetAddress.getAllByName(args[0]);
			for(int i = 0 ; i < address2.length ; i++) {
				System.out.println(address2[i]);
			}
		}catch(UnknownHostException e) {
			System.out.println("������ ȣ��Ʈ�� ã�� �� �����ϴ�.");
		}
		
//		���� ���α׷��� ����ǰ� �ִ� ������ǻ���� InetAddress ��ü�� ��ȯ�Ѵ�.
		try {
			InetAddress address3 = InetAddress.getLocalHost();
			System.out.println(address3);
		}catch(UnknownHostException e) {
			System.out.println("������ ȣ��Ʈ�� ã�� �� �����ϴ�.");
		}
	}

}
