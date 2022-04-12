package ch11;

import java.net.*;
import java.io.*;

public class UDPEchoServer extends Thread{
	
	public static final int PORT = 7;
	public static final int BUFFER_SIZE = 8192;
	protected DatagramSocket ds;
	
	public UDPEchoServer() throws SocketException{
		ds = new DatagramSocket(PORT);
	}
	
	public void run() {
		byte[] buffer = new byte[BUFFER_SIZE];
		while(true) {
			//수신용 데이터그램
			DatagramPacket incoming = new DatagramPacket(buffer, buffer.length);
			
			try {
				 //데이터그램 수신
				ds.receive(incoming);
				//수신한 바이트 데이터를 문자열로 변환
				String recData = new String(incoming.getData(),incoming.getOffset(), incoming.getLength());
				System.out.println("문자열 : " + recData);
				//송신용 데이터그램 생성
				DatagramPacket outgoing = new DatagramPacket(incoming.getData(), incoming.getLength(),
						incoming.getAddress(), incoming.getPort());
				//데이터그램을 보낸 호스트에 다시 전송
				ds.send(outgoing);
				 
			}catch(IOException e) {
				System.out.println(e);
			}
		}
	}

	public static void main(String[] args) {
		try {
			UDPEchoServer server = new UDPEchoServer();
			server.start(); //스레드 시작
		}catch(SocketException se) {
			System.out.println(se);
		}
	}

}
