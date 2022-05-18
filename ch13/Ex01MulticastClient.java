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
	  
	  try {
	   msocket = new MulticastSocket(9006);
	   // �׷쿡 ����(����Ͱ� ����)
	   InetAddress address = InetAddress.getByName("224.128.1.5"); // ��Ƽ ĳ��Ʈ�� ���� ������ ����
	   System.out.println(address.getHostAddress()+" �׷� �ּ� �� "+9006+" ��Ʈ�� ���ε�� ��Ƽĳ��Ʈ ������ ������.");
	   msocket.setSoTimeout(10000);
       msocket.setTimeToLive(1); // ���ۿ����� ���� ��Ʈ��ũ�� ����. 
       System.out.println("��Ƽĳ��Ʈ �׷쿡 �����ϰ� �ֽ��ϴ�.");
	   msocket.joinGroup(address);
	   
	   byte[] buf = new byte[512];
	   packet = new DatagramPacket(buf, buf.length);
	   

	    // ��Ŷ ����
	   System.out.println("��Ƽĳ��Ʈ ��Ŷ�� �����ϰ� �ֽ��ϴ�.");
	    msocket.receive(packet);
	    String receivemsg = new String(packet.getData(), 0, packet.getLength());
        System.out.println("���ŵ� �޽����� : "+receivemsg);
	   
        System.out.println("��Ƽĳ��Ʈ �׷��� Ż���մϴ�.");
        msocket.leaveGroup(address);
        msocket.close();
        
	  } catch (SocketException e) {
	   e.printStackTrace();
	  } catch (IOException e) {
	   e.printStackTrace();
	  }
	 }

}

