package test;

import java.net.*;

public class GetIPAddress {

	public static void main(String[] args) {

		//�۷ɾ� ���ο��� ȣ��Ʈ �̸� �Է�;
		if(args.length != 1) {
			System.out.println("ȣ��Ʈ �̸��� �Է��ϼ���");
			return;
		}
		//ȣ��Ʈ�̸�/�ּ� ���
		
		try {
			InetAddress ia = InetAddress.getByName(args[0]);
			System.out.println(ia);
		}catch(UnknownHostException e) {
			System.out.println(e);
		}

	}

}
