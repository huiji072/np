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
				//�ް�
				ds.receive(incoming);
				//���ڿ���ȯ
				String recData = new String(incoming.getData(), 0, incoming.getLength());
				//���
				System.out.println(recData);
				
				String theLine = input.readLine();
				byte[] data = theLine.getBytes();
				if(theLine.equals("y")) {
					System.out.println("������ ����Ʈ�� �ü�� �̸���? ");
					if(theLine.equals("ios")) {
						System.out.println("�����Դϴ�. ����Ͻðڽ��ϱ�? ");
						if(theLine.equals("y")) {
							System.out.println("������ ����Ʈ�� �ü�� �̸���? ");
						}else {
							System.exit(0);
						}
					}else {
						System.out.println("�����Դϴ�. ����Ͻðڽ��ϱ�? ");
						if(theLine.equals("y")) {
							System.out.println("������ ����Ʈ�� �ü�� �̸���? ");
						}else {
							System.exit(0);
						}
					}
				}
				
				
				//client�� �ٽ� �ǵ����ַ���
				DatagramPacket outgoing = new DatagramPacket(incoming.getData(), incoming.getLength(),
						incoming.getAddress(), incoming.getPort());
				//client����
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
