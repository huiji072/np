package ch11;

import java.io.*;
import java.net.*;

public class UDPDiscardClient {

	public static final int PORT = 9;
	public static String hostname = "localhost";
	
	public static void main(String[] args) {
		
		BufferedReader input;
		DatagramPacket packet;
		DatagramSocket theSocket;
		
		try {

			InetAddress server = InetAddress.getByName(hostname);
			input = new BufferedReader(new InputStreamReader(System.in));
			theSocket = new DatagramSocket();
			
			while(true) {
				String theLine = input.readLine();
				if(theLine.equals(".")) break;
				
				byte[] data = theLine.getBytes();
				packet = new DatagramPacket(data, data.length, server, PORT);
				theSocket.send(packet); //데이터그램 전송
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
