package test;

import java.io.IOException;
import java.net.ServerSocket;

public class LookForServerPorts {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ServerSocket theServer = null;
		
		for(int i = 1024 ; i < 65535 ; i++) {
			try {
				theServer = new ServerSocket(i); //���� ���� ��ü�� �����Ѵ�.
				theServer.close();
			}catch(IOException e) {
				System.out.println(i + " ��° ��Ʈ�� Ư�������� ������Դϴ�.");
			}
		}

	}

}
