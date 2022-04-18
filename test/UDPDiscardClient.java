package test;

import java.io.*;
import java.net.*;

public class UDPDiscardClient {
	
	public static final int PORT = 9;
	public static String hostname = "localhost";

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		BufferedReader input;
		DatagramPacket packet;
		DatagramSocket socket;
		
		try {
			InetAddress ia = InetAddress.getByName(hostname);
			input = new BufferedReader(new InputStreamReader(System.in));
			socket = new DatagramSocket();
			
			while(true) {
				String line = input.readLine();
				if(line.equals(".")) break;
				byte[] data = line.getBytes();
				packet = new DatagramPacket(data, data.length, ia, PORT);
				socket.send(packet);
			}
		}catch(UnknownHostException e) {
			System.out.println(e);
		}catch(SocketException e) {
			System.out.println(e);
		}catch(IOException e) {
			System.out.println(e);
		}

	}

}
