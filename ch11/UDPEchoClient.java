package ch11;

import java.net.*;
import java.io.*;

public class UDPEchoClient {
	
	public static final int PORT = 7;

	public static void main(String[] args) {

		String hostname = "localhost";
		if(args.length > 0) {
			hostname = args[0];
		}
		
		try {
			InetAddress ia = InetAddress.getByName(hostname);
			DatagramSocket theSocket = new DatagramSocket();
			//데이터를 송신하는 스레드 클래스로부터 객체를 생성한다.
			Sender send = new Sender(ia, PORT, theSocket);
			//데이터 송신
			send.start();
			
			//데이터를 수신하는 스레드 클래스로부터 객체를 생성한다.
			Receiver receive = new Receiver(theSocket);
			//데이터 수신
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
		this.port = port;
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
				if(line.equals(".")) System.exit(0);
				line = line.toString()+"\r\n";
				//바이트 데이터로 변환
				byte[] data = line.getBytes("ASCII");
				packet = new DatagramPacket(data, data.length, server, port);
				theSocket.send(packet); //데이터 송신
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
				theSocket.receive(packet); //데이터 수신
				String data = new String(packet.getData(), 0, packet.getLength());
				System.out.println(data);
				Thread.yield();
			}catch(IOException e) {
				System.out.println(e);
			}
		}
	}
	
}
