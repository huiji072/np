package test;

import java.io.IOException;
import java.net.*;

public class GetSocketFields {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Socket theSocket;
		for(int i = 0 ; i < args.length ; i++) {
			try {
				theSocket = new Socket(args[i], 80);
				System.out.println("����ȣ��Ʈ�� " + theSocket.getLocalPort() + " ��Ʈ�κ��� "
						+theSocket.getInetAddress() + " ȣ��Ʈ�� " + theSocket.getPort() + " ��Ʈ�� ����");
			}catch(UnknownHostException e) {
				System.err.println(args[i] + " ȣ��Ʈ�� ã�� �� �����ϴ�.");
			}catch(SocketException e) {
				System.out.println(args[i] + " ȣ��Ʈ�� ������ �� �����ϴ�.");
			}catch(IOException e) {
				System.out.println(e);
			}
		}

	}

}
