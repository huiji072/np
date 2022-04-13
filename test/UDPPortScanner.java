package test;

import java.net.*;

public class UDPPortScanner {

	public static void main(String[] args) {

		for(int port=1024 ; port<=65535 ; port++) {
			try {
				DatagramSocket server = new DatagramSocket(port);
				server.close();
			}catch(SocketException se) {
				System.out.println(port + " 포트는 이미 서버가 사용중입니다.");
			}
		}

	}

}
