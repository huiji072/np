package TCP_IP_Server;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class GetSocketFields {

	public static void main(String[] args) {

		Socket theSocket;
		
		for(int i = 0 ; i < args.length ; i++) {
			try {
				theSocket = new Socket(args[i], 80);
				System.out.println("���� ȣ��Ʈ�� " + theSocket.getLocalPort() + " ��Ʈ�κ��� "+ 
				theSocket.getInetAddress() + " ȣ��Ʈ�� " + theSocket.getPort() + "��Ʈ�� ����");
			}catch(UnknownHostException e) {
				System.err.println(args[i] + "ȣ��Ʈ�� ã�� �� �����ϴ�.");
			}catch(SocketException e) {
				System.err.println(args[i] + "ȣ��Ʈ�� ������ �� �����ϴ�.");
			}catch(IOException e) {
				System.err.println(e);
			}
		}

	}

}
