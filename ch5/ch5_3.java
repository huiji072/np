package ch5;

import java.net.*;

public class ch5_3 {

	public static void main(String[] args) {

		try {
			InetAddress local = InetAddress.getLocalHost();
			byte[] address = local.getAddress(); //IP�ּҰ� ����Ʈ �迭 �����
			System.out.println("��� ���� ȣ��Ʈ�� IP �ּҴ� ");
			
			for(int i = 0 ; i < address.length ; i++) {
				int unsigned = address[i]<0 ? address[i]+256 : address[i];
				System.out.print(unsigned+".");
			}
			System.out.println();
		}catch(UnknownHostException e) {
			System.out.println("����ȣ��Ʈ�� IP�ּҸ� �� �� �����ϴ�.");
		}
	}

}
