package test;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class GetServerSocketFields {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		ServerSocket theServer = null;
		Socket theSocket = null;
		int port = 4000;
		
		try {
			System.out.println(port + " ��Ʈ�� ���ε�� ���� ������ �����ϴ� ���Դϴ�.");
			theServer = new ServerSocket(port);
			System.out.println(theServer.getInetAddress().getHostName() + " ȣ��Ʈ �� " 
					+theServer.getLocalPort() + " ��Ʈ�� ���ε�� ���� ������ �����Ͽ����ϴ�.");
		}catch(IOException e) {
			System.out.println(e);
			System.exit(1);
		}
		
		while(true) {
			System.out.println("Ŭ���̾�Ʈ�� ���� ��û�� ��ٸ��ϴ�. ");
			
			try {
				//Ŭ���̾�Ʈ�� ���ӿ�û�� �� ������ ������ ��ٸ���.
				theSocket = theServer.accept();
				System.out.println(theSocket.getInetAddress().getHostName()+" �̸� �� "
						+theSocket.getPort()+" ��Ʈ�� Ŭ���̾�Ʈ�κ��� ���� ��û�� �޾ҽ��ϴ�.");
				
				//�� �κп��� ���ӵ� Ŭ���̾�Ʈ�� ����� �����Ѵ�.
				
				 theSocket.close();
			}catch(IOException e) {
				System.err.println(e);
			}
		}

	}

}
