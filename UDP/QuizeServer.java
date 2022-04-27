package UDP;

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
				String quest4;
				String answer1;
				String answer2;
				String answer3;
				String no1;
				String no2;
				String no3;
				
				System.out.println("��� �����մϴ�.(y/n)");
				quest1 = input.readLine();
				if(quest1.equals("y")) { //y���� - �������o
					
					System.out.println("����� �����?");//q1
					answer1 = input.readLine(); 
					
					if(answer1.equals("apple")) { //q1 - ����o - ���?
						System.out.println("�����Դϴ�. ����Ͻðڽ��ϱ�?(y/n)"); //q1 o
						quest2 = input.readLine(); //y
						
						if(quest2.equals("y")) { //q1 - ����0 - ���o
							System.out.println("�ٳ����� �����?"); //q2
							answer2 = input.readLine();
							
							if(answer2.equals("banana")) { // q2 o
								System.out.println("�����Դϴ�. ����Ͻðڽ��ϱ�?(y/n)"); //q2 ���� - ���?
								quest4 = input.readLine();
								
								if(quest4.equals("y")) {
									System.out.println("������ �����?"); // q1
									answer3 = input.readLine();
									
									if(answer3.equals("grape")) {
										System.out.println("�����Դϴ�. ����Ͻðڽ��ϱ�?(y/n)"); //q2 ���� - ���?
										no3 = input.readLine();
										
										if(no3.equals("n")){ //q3 ���� - ���x
											System.out.println("quit");
											System.exit(0);
										}
									}
									
										
									
								}
							}else {
								System.out.println("�����Դϴ�. ����Ͻðڽ��ϱ�?(y/n)"); //q2 ���� - ���?
								no2 = input.readLine();
								if(no2.equals("n")){ //q2 ���� - ���x
									System.out.println("quit");
									System.exit(0);
								}else {
									System.out.println("�ٳ����� �����?");
									answer2 = input.readLine();
									
									if(answer2.equals("banana")) { // q2 - ����o
										System.out.println("�����Դϴ�. ����Ͻðڽ��ϱ�?(y/n)"); //q2 ���� - ���?
										no1 = input.readLine();
										
										if(no1.equals("y")){ //q2 ���� - ���x
											System.out.println("quit");
											System.exit(0);
										}
										else {
											System.out.println("������ �����?");
											answer3 = input.readLine();
											
											if(answer3.equals("grape")) {
												System.out.println("�����Դϴ�. ����Ͻðڽ��ϱ�?(y/n)");
												no3 = input.readLine();

												if(no3.equals("n")){ 
													System.out.println("quit");
													System.exit(0);
												}
											}else {
												System.out.println("quit");
												System.exit(0);
											}
										}
									}
								}
								
							}
						}else if(quest2.equals("n")){ //q1 o 
							System.out.println("quit");
							System.exit(0);
						}
						
					}else {
						System.out.println("�����Դϴ�. ����Ͻðڽ��ϱ�?(y/n)"); //q1 - ���� - ���?
						no3 = input.readLine();
						
						if(no3.equals("y")) { //q1 - ���� - ���o
							System.out.println("�ٳ����� �����?");
							answer2 = input.readLine();
							
							if(answer2.equals("banana")) { // q2 - ����o
								System.out.println("�����Դϴ�. ����Ͻðڽ��ϱ�?(y/n)"); //q2 ���� - ���?
								no1 = input.readLine();
								
								if(no1.equals("n")){ //q2 ���� - ���x
									System.out.println("quit");
									System.exit(0);
								}
							}
							
							else {
								System.out.println("�����Դϴ�. ����Ͻðڽ��ϱ�?(y/n)"); //q2 ���� - ���?
								no2 = input.readLine();
								
								if(no2.equals("n")){ //android ���� - ���x
									System.out.println("quit");
									System.exit(0);
								}
							}
							
							
						}
						if(no3.equals("n")){ //q1 - ���� - ���x
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
