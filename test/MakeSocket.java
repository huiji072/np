package test;

import java.io.IOException;
import java.net.*;

public class MakeSocket {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Socket theSocket;
		String host = "www.ssc.ac.kr";
		int port = 7;
		
		try {
			System.out.println(host + " ȣ��Ʈ�� ��Ʈ��ȣ " + port + " �� �����ϰ� �ֽ��ϴ�.");
			theSocket = new Socket(host, port);
			System.out.println("������ �Ϸ�Ǿ����ϴ�.");
			
			//�� �κп��� ������ ����� ������ �� �ִ�.
			
			theSocket.close();
		}catch(UnknownHostException e) {
			System.out.println("������ ȣ��Ʈ�� �����ϴ�.");
		}catch(IOException e) {
			System.out.println("������ ������ �ʴ´�.");
		}

	}

}