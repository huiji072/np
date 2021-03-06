package UDP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class QuizeClient {

	public static final int PORT = 7;

	public static void main(String[] args) {
		
		String hostname = "localhost";
		if(args.length > 0) {
			hostname = args[0];
		}
		
		try {
			InetAddress ia = InetAddress.getByName(hostname);
			DatagramSocket theSocket = new DatagramSocket();
			Sender send = new Sender(ia, PORT, theSocket);
			send.start();
			Receiver receive = new Receiver(theSocket);
			receive.start();
			}catch(UnknownHostException e) {
				System.out.println(e);
			}catch(SocketException se) {
				System.out.println(se);
			}
	}

}

class Sender extends Thread{
	InetAddress server;
	int port;
	DatagramSocket theSocket;
	
	public Sender(InetAddress ia, int port, DatagramSocket ds) {
		server = ia;
		this.port =  port;
		theSocket = ds;
	}
	
	public void run() {
		BufferedReader reader;
		String line;
		DatagramPacket packet;
		
		try {
			reader = new BufferedReader(new InputStreamReader(System.in));
			
			while(true) {
				line = reader.readLine();
				if(line.equals(".")) break;
				line = line.toString() +"\r\n";
				byte[] data = line.getBytes("ASCII");
				packet = new DatagramPacket(data, data.length, server, port);
				theSocket.send(packet);
				Thread.yield();
			}
		}catch(IOException e) {
			System.out.println(e);
		}
	}
}

class Receiver extends Thread{
	DatagramSocket theSocket;
	protected DatagramPacket packet;
	
	public Receiver(DatagramSocket ds) {
		theSocket = ds;
		byte[] buffer = new byte[65508];
		packet = new DatagramPacket(buffer, buffer.length);
	}
	
	public void run() {
		while(true) {
			try {
				theSocket.receive(packet);
				String data = new String(packet.getData(),0,packet.getLength());
				System.out.println(data);
				Thread.yield();
			} catch (IOException e) {
				System.out.println(e);
			}
			
		}
	}
}
