package test;

import java.io.IOException;
import java.net.*;

//public class UDPEchoServer extends Thread{
//	
//	public static final int PORT = 7;
//	public static final int BUFFER_SIZE = 8192;
//	protected DatagramSocket ds;
//	
//	public UDPEchoServer() throws SocketException{
//		ds = new DatagramSocket(PORT);
//	}
//	
//	public void run() {
//		byte[] buffer = new byte[BUFFER_SIZE];
//		
//		while(true) {
//			DatagramPacket incoming = new DatagramPacket(buffer, buffer.length);
//			
//			try {
//				//받고
//				ds.receive(incoming);
//				//문자열변환
//				String recData = new String(incoming.getData(), 0, incoming.getLength());
//				//출력
//				System.out.println(recData);
//				//client에 다시 되돌려주려고
//				DatagramPacket outgoing = new DatagramPacket(incoming.getData(), incoming.getLength(),
//						incoming.getAddress(), incoming.getPort());
//				//client보냄
//				ds.send(outgoing);
//						
//			}catch(IOException e) {
//				System.out.println(e);
//			}
//		}
//	}
//
//	public static void main(String[] args) {
//		try {
//			UDPEchoServer server = new UDPEchoServer();
//			server.start();
//		}catch(SocketException e) {
//			System.out.println(e);
//		}
//
//	}
//
//}
