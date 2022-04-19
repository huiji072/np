package UDP;

import java.io.*;
import java.net.*;

public class ServerDictionary {
	
	public static final int PORT = 9;
	public static final int MAX_PACKET_SIZE = 65508;
	public static String hostname = "localhost";
	static FileInputStream fin;
	static InputStreamReader isr;
	static BufferedReader br;
	static BufferedReader socket_br;
	static String[] dic = new String[10];
	public static void main(String[] args) {
		
		byte[] buffer = new byte[MAX_PACKET_SIZE];
		try {
			InetAddress server = InetAddress.getByName(hostname);
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length); //수신패킷
			DatagramSocket socket = new DatagramSocket(PORT);
			DatagramPacket kor_packet;
			
			//서버는 데이터그램 소켓을 만들고, 포트에 들어오는 패킷을 수신한다.
			
			while(true) {
				try {
					socket.receive(packet);//클라이언트 패킷 받고
					String data = new String(packet.getData(), packet.getOffset(), packet.getLength());
					System.out.println(data);
					
					String s1;
					String s2 = null;
					fin = new FileInputStream("Dictionary.txt");
					isr = new InputStreamReader(fin);
					br = new BufferedReader(isr);
					int i = 0;
					byte[] kor = new byte[100];
					
//					파일에 있는단어를 한줄씩 s1에
					while((s1 = br.readLine()) != null) {
//						파일에 있는 단어 dic에 영어한글 넣기
						dic[i] = s1; //apple 사과 banana 바나나
						if(dic[i].equals(data)) {
							System.out.println(dic[i-1]);
						}
						i++;
					}
//					kor_packet = new DatagramPacket(kor, kor.length, server, PORT);
//					socket.send(kor_packet);
				}catch(IOException e) {
					System.out.println(e);
				}
			}
		}catch(SocketException se) {
			System.out.println(se);
		}catch(UnknownHostException e) {
			System.out.println(e);
		}
		
	}

}
