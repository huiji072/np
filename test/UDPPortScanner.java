package test;

import java.net.*;

public class UDPPortScanner {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		for(int port=1024 ; port<=65536 ; port++) {
			try {
				DatagramSocket server = new DatagramSocket(port);
				server.close();
			}catch(SocketException e) {
				System.out.println(e);
			}
		}

	}

}
