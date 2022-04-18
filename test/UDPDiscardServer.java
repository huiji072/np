package test;

import java.io.IOException;
import java.net.*;

public class UDPDiscardServer {
	
	public static final int PORT = 9;
	public static final int MAX_PACKET_SIZE = 65508;
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		byte[] buffer = new byte[MAX_PACKET_SIZE];
		try {
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
			DatagramSocket socket = new DatagramSocket(PORT);
			
			while(true) {
				try {
					socket.receive(packet);
					String data = new String(packet.getData(), 0, packet.getLength());
					System.out.println(packet.getAddress()+", " + packet.getPort() + ", " + data);
					packet.setLength(buffer.length);
				}catch(IOException e) {
					System.out.println(e);
				}
			}
		}catch(SocketException e) {
			System.out.println(e);
		}

	}

}
