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
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length); //������Ŷ
			DatagramSocket socket = new DatagramSocket(PORT);
			DatagramPacket kor_packet;
			
			//������ �����ͱ׷� ������ �����, ��Ʈ�� ������ ��Ŷ�� �����Ѵ�.
			
			while(true) {
				try {
					socket.receive(packet);//Ŭ���̾�Ʈ ��Ŷ �ް�
					String data = new String(packet.getData(), packet.getOffset(), packet.getLength());
					System.out.println(data);
					
					String s1;
					String s2 = null;
					fin = new FileInputStream("Dictionary.txt");
					isr = new InputStreamReader(fin);
					br = new BufferedReader(isr);
					int i = 0;
					byte[] kor = new byte[100];
					
//					���Ͽ� �ִ´ܾ ���پ� s1��
					while((s1 = br.readLine()) != null) {
//						���Ͽ� �ִ� �ܾ� dic�� �����ѱ� �ֱ�
						dic[i] = s1; //apple ��� banana �ٳ���
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
