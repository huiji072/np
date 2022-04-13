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
				

				String quest1;
				String quest2;
				String quest3;
				String answer1;
				String answer2;
				String no1;
				String no2;
				String no3;
				
				System.out.println("��� �����մϴ�.(y/n)");
				quest1 = input.readLine();
				if(quest1.equals("y")) { //y���� - �������o
					System.out.println("������ ����Ʈ�� �ü�� �̸���? ");
					answer1 = input.readLine();
					if(answer1.equals("ios")) { //ios - ����o - ���?
						System.out.println("�����Դϴ�. ����Ͻðڽ��ϱ�?(y/n)"); 
						quest2 = input.readLine();
						if(quest2.equals("y")) { //ios - ����0 - ���o
							System.out.println("������ ����Ʈ�� �ü�� �̸���?");
							quest3 = input.readLine();
							if(quest3.equals("android")) { // android - ����o
								System.out.println("�����Դϴ�. ����Ͻðڽ��ϱ�?(y/n)"); //android ���� - ���?
								no1 = input.readLine();
								if(no1.equals("n")){ //android ���� - ���x
									System.out.println("quit");
									System.exit(0);
								}
							}else {
								System.out.println("�����Դϴ�. ����Ͻðڽ��ϱ�?(y/n)"); //android ���� - ���?
								no2 = input.readLine();
								if(no2.equals("n")){ //android ���� - ���x
									System.out.println("quit");
									System.exit(0);
								}
							}
						}else if(quest2.equals("n")){ //ios - ����o - ���x
							System.out.println("quit");
							System.exit(0);
						}
						
					}else {
						System.out.println("�����Դϴ�. ����Ͻðڽ��ϱ�?(y/n)"); //ios - ���� - ���?
						no3 = input.readLine();
						if(no3.equals("y")) { //ios - ���� - ���o
							System.out.println("������ ����Ʈ�� �ü�� �̸���?");
							quest3 = input.readLine();
							if(quest3.equals("android")) { // android - ����o
								System.out.println("�����Դϴ�. ����Ͻðڽ��ϱ�?(y/n)"); //android ���� - ���?
								no1 = input.readLine();
								if(no1.equals("n")){ //android ���� - ���x
									System.out.println("quit");
									System.exit(0);
								}
							}else {
								System.out.println("�����Դϴ�. ����Ͻðڽ��ϱ�?(y/n)"); //android ���� - ���?
								no2 = input.readLine();
								if(no2.equals("n")){ //android ���� - ���x
									System.out.println("quit");
									System.exit(0);
								}
							}
						}
						if(no3.equals("n")){ //ios - ���� - ���x
							System.out.println("quit");
							System.exit(0);
						}
						
					}
				}else if(quest1.equals("n")) { //n���� - �������x
					System.out.println("quit");
					System.exit(0);
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
