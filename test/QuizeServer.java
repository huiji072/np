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
				

				String quest1;
				String quest2;
				String quest3;
				String answer1;
				String answer2;
				String no1;
				String no2;
				String no3;
				
				System.out.println("퀴즈를 시작합니다.(y/n)");
				quest1 = input.readLine();
				if(quest1.equals("y")) { //y선택 - 퀴즈시작o
					System.out.println("애플의 스마트폰 운영체제 이름은? ");
					answer1 = input.readLine();
					if(answer1.equals("ios")) { //ios - 정답o - 계속?
						System.out.println("정답입니다. 계속하시겠습니까?(y/n)"); 
						quest2 = input.readLine();
						if(quest2.equals("y")) { //ios - 정답0 - 계속o
							System.out.println("구글의 스마트폰 운영체제 이름은?");
							quest3 = input.readLine();
							if(quest3.equals("android")) { // android - 정답o
								System.out.println("정답입니다. 계속하시겠습니까?(y/n)"); //android 정답 - 계속?
								no1 = input.readLine();
								if(no1.equals("n")){ //android 정답 - 계속x
									System.out.println("quit");
									System.exit(0);
								}
							}else {
								System.out.println("오답입니다. 계속하시겠습니까?(y/n)"); //android 오답 - 계속?
								no2 = input.readLine();
								if(no2.equals("n")){ //android 오답 - 계속x
									System.out.println("quit");
									System.exit(0);
								}
							}
						}else if(quest2.equals("n")){ //ios - 정답o - 계속x
							System.out.println("quit");
							System.exit(0);
						}
						
					}else {
						System.out.println("오답입니다. 계속하시겠습니까?(y/n)"); //ios - 오답 - 계속?
						no3 = input.readLine();
						if(no3.equals("y")) { //ios - 오답 - 계속o
							System.out.println("구글의 스마트폰 운영체제 이름은?");
							quest3 = input.readLine();
							if(quest3.equals("android")) { // android - 정답o
								System.out.println("정답입니다. 계속하시겠습니까?(y/n)"); //android 정답 - 계속?
								no1 = input.readLine();
								if(no1.equals("n")){ //android 정답 - 계속x
									System.out.println("quit");
									System.exit(0);
								}
							}else {
								System.out.println("오답입니다. 계속하시겠습니까?(y/n)"); //android 오답 - 계속?
								no2 = input.readLine();
								if(no2.equals("n")){ //android 오답 - 계속x
									System.out.println("quit");
									System.exit(0);
								}
							}
						}
						if(no3.equals("n")){ //ios - 오답 - 계속x
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
