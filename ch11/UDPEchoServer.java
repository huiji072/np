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
			//���ſ� �����ͱ׷�
			DatagramPacket incoming = new DatagramPacket(buffer, buffer.length);
			
			try {
				 //�����ͱ׷� ����
				ds.receive(incoming);
				//������ ����Ʈ �����͸� ���ڿ��� ��ȯ
				String recData = new String(incoming.getData(),incoming.getOffset(), incoming.getLength());
				System.out.println("���ڿ� : " + recData);
				//�۽ſ� �����ͱ׷� ����
				DatagramPacket outgoing = new DatagramPacket(incoming.getData(), incoming.getLength(),
						incoming.getAddress(), incoming.getPort());
				//�����ͱ׷��� ���� ȣ��Ʈ�� �ٽ� ����
				ds.send(outgoing);
				 
			}catch(IOException e) {
				System.out.println(e);
			}
		}
	}

	public static void main(String[] args) {
		try {
			UDPEchoServer server = new UDPEchoServer();
			server.start(); //������ ����
		}catch(SocketException se) {
			System.out.println(se);
		}
	}

}
