package UDP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class ClientDictionary {
	
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
				
				byte[] data = theLine.getBytes(); //단어 입력
				packet = new DatagramPacket(data, data.length, server, PORT);
				theSocket.send(packet); //단어 전송

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
