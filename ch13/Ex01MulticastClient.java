package ch13;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;

public class Ex01MulticastClient {

 public static void main(String[] args) {
  

	 DatagramPacket packet = null;
	  MulticastSocket msocket = null;
	  int port = 9006;
	  try {
	   // �׷쿡 ����(����Ͱ� ����)
	   InetAddress group = InetAddress.getByName("224.128.1.5"); // ��Ƽ ĳ��Ʈ�� ���� ������ ����
	   System.out.println(group.getHostAddress()+" �׷� �ּ� �� "+port+" ��Ʈ�� ���ε�� ��Ƽĳ��Ʈ ������ ������.");
	   msocket = new MulticastSocket(port);
	   msocket.setSoTimeout(10000);
       msocket.setTimeToLive(1); // ���ۿ����� ���� ��Ʈ��ũ�� ����. 
       System.out.println("��Ƽĳ��Ʈ �׷쿡 �����ϰ� �ֽ��ϴ�.");
	   msocket.joinGroup(group);
	   
	   byte[] buf = new byte[512];
	   packet = new DatagramPacket(buf, buf.length);
	   

	    // ��Ŷ ����
	   packet.setData(new byte[512]);
       packet.setLength(512);    // ������ ũ�⸦ ������.
	   System.out.println("��Ƽĳ��Ʈ ��Ŷ�� �����ϰ� �ֽ��ϴ�.");
	    msocket.receive(packet);
	    String receivemsg = new String(packet.getData(), 0, packet.getLength());
        System.out.println("���ŵ� �޽����� : "+receivemsg);
	   
        System.out.println("��Ƽĳ��Ʈ �׷��� Ż���մϴ�.");
        msocket.leaveGroup(group);
        msocket.close();
        
	  } catch (SocketException e) {
	   e.printStackTrace();
	  } catch (IOException e) {
	   e.printStackTrace();
	  }
	 }

}