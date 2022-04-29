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
				System.out.println("로컬호스트의 " + theSocket.getLocalPort() + " 포트로부터 "
						+theSocket.getInetAddress() + " 호스트의 " + theSocket.getPort() + " 포트에 연결");
			}catch(UnknownHostException e) {
				System.err.println(args[i] + " 호스트를 찾을 수 없습니다.");
			}catch(SocketException e) {
				System.out.println(args[i] + " 호스트에 연결할 수 없습니다.");
			}catch(IOException e) {
				System.out.println(e);
			}
		}

	}

}
