package test;

import java.io.IOException;
import java.net.*;

public class CreateServerSocket {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ServerSocket theServer = null;
		int port;
		if(args.length == 1) {
			port = Integer.parseInt(args[0]);
		}else {
			System.out.println("��ɾ� ���ο��� ������ ��Ʈ��ȣ�� �Է��ϼ���.");
			return;
		}
		
		try {
			theServer = new ServerSocket(port); //���� ���� ��ü�� �����Ѵ�.
			System.out.println(port + "�� ���ε�� ���� ���� ��ü�� �����Ͽ����ϴ�.");
			
			//�� �κп��� Ŭ���̾�Ʈ�� ������ ��ٸ��� ����� �����Ѵ�.
			
			theServer.close();
			
		}catch(IOException e) {
			System.out.println(e);
		}
	}

}
