package test;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class QuizeServer extends Thread{
	
	public static final int PORT = 7;
	public static final int BUFFER_SIZE = 8192;
	protected DatagramSocket ds;
	BufferedReader input;
	
	public QuizeServer() throws SocketException{
		ds = new DatagramSocket(PORT);
	}
	
	public void run() {
		byte[] buffer = new byte[BUFFER_SIZE];
		input = new BufferedReader(new InputStreamReader(System.in));
		while(true) {
			DatagramPacket incoming = new DatagramPacket(buffer, buffer.length);
			
			try {
				//받고
				ds.receive(incoming);
				//문자열변환
				String recData = new String(incoming.getData(), 0, incoming.getLength());
				//출력
				System.out.println(recData);
				
				String theLine = input.readLine();
				byte[] data = theLine.getBytes();
				if(theLine.equals("y")) {
					System.out.println("애플의 스마트폰 운영체제 이름은? ");
					if(theLine.equals("ios")) {
						System.out.println("정답입니다. 계속하시겠습니까? ");
						if(theLine.equals("y")) {
							System.out.println("구글의 스마트폰 운영체제 이름은? ");
						}else {
							System.exit(0);
						}
					}else {
						System.out.println("오답입니다. 계속하시겠습니까? ");
						if(theLine.equals("y")) {
							System.out.println("구글의 스마트폰 운영체제 이름은? ");
						}else {
							System.exit(0);
						}
					}
				}
				
				
				//client에 다시 되돌려주려고
				DatagramPacket outgoing = new DatagramPacket(incoming.getData(), incoming.getLength(),
						incoming.getAddress(), incoming.getPort());
				//client보냄
				ds.send(outgoing);
						
			}catch(IOException e) {
				System.out.println(e);
			}
		}
	}

	public static void main(String[] args) {
		try {
			QuizeServer server = new QuizeServer();
			server.start();
		}catch(SocketException e) {
			System.out.println(e);
		}

	}

}
