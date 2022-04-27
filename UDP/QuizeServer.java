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
				//받고
				ds.receive(incoming);
				//문자열변환
				String recData = new String(incoming.getData(), 0, incoming.getLength());
				//출력
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
				
				System.out.println("퀴즈를 시작합니다.(y/n)");
				quest1 = input.readLine();
				if(quest1.equals("y")) { //y선택 - 퀴즈시작o
					
					System.out.println("사과는 영어로?");//q1
					answer1 = input.readLine(); 
					
					if(answer1.equals("apple")) { //q1 - 정답o - 계속?
						System.out.println("정답입니다. 계속하시겠습니까?(y/n)"); //q1 o
						quest2 = input.readLine(); //y
						
						if(quest2.equals("y")) { //q1 - 정답0 - 계속o
							System.out.println("바나나는 영어로?"); //q2
							answer2 = input.readLine();
							
							if(answer2.equals("banana")) { // q2 o
								System.out.println("정답입니다. 계속하시겠습니까?(y/n)"); //q2 정답 - 계속?
								quest4 = input.readLine();
								
								if(quest4.equals("y")) {
									System.out.println("포도는 영어로?"); // q1
									answer3 = input.readLine();
									
									if(answer3.equals("grape")) {
										System.out.println("정답입니다. 계속하시겠습니까?(y/n)"); //q2 정답 - 계속?
										no3 = input.readLine();
										
										if(no3.equals("n")){ //q3 정답 - 계속x
											System.out.println("quit");
											System.exit(0);
										}
									}
									
										
									
								}
							}else {
								System.out.println("오답입니다. 계속하시겠습니까?(y/n)"); //q2 오답 - 계속?
								no2 = input.readLine();
								if(no2.equals("n")){ //q2 오답 - 계속x
									System.out.println("quit");
									System.exit(0);
								}else {
									System.out.println("바나나를 영어로?");
									answer2 = input.readLine();
									
									if(answer2.equals("banana")) { // q2 - 정답o
										System.out.println("정답입니다. 계속하시겠습니까?(y/n)"); //q2 정답 - 계속?
										no1 = input.readLine();
										
										if(no1.equals("y")){ //q2 정답 - 계속x
											System.out.println("quit");
											System.exit(0);
										}
										else {
											System.out.println("포도를 영어로?");
											answer3 = input.readLine();
											
											if(answer3.equals("grape")) {
												System.out.println("정답입니다. 계속하시겠습니까?(y/n)");
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
						System.out.println("오답입니다. 계속하시겠습니까?(y/n)"); //q1 - 오답 - 계속?
						no3 = input.readLine();
						
						if(no3.equals("y")) { //q1 - 오답 - 계속o
							System.out.println("바나나를 영어로?");
							answer2 = input.readLine();
							
							if(answer2.equals("banana")) { // q2 - 정답o
								System.out.println("정답입니다. 계속하시겠습니까?(y/n)"); //q2 정답 - 계속?
								no1 = input.readLine();
								
								if(no1.equals("n")){ //q2 정답 - 계속x
									System.out.println("quit");
									System.exit(0);
								}
							}
							
							else {
								System.out.println("오답입니다. 계속하시겠습니까?(y/n)"); //q2 오답 - 계속?
								no2 = input.readLine();
								
								if(no2.equals("n")){ //android 오답 - 계속x
									System.out.println("quit");
									System.exit(0);
								}
							}
							
							
						}
						if(no3.equals("n")){ //q1 - 오답 - 계속x
							System.out.println("quit");
							System.exit(0);
						}
						
					}
				}else if(quest1.equals("n")) { //n선택 - 퀴즈시작x
					System.out.println("quit");
					System.exit(0);
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
