package ch13;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;

public class Ex01MulticastServer {

 public static void main(String[] args) {
  
	  DatagramPacket packet = null;
	  MulticastSocket msocket = null;
	  int port = 9006;
	  try {
	   InetAddress group = InetAddress.getByName("224.128.1.5"); // ��Ƽĳ��Ʈ ������� ���� �ּҸ� ������.
	   System.out.println(group.getHostAddress()+" �׷� �ּ� �� "+port+" ��Ʈ�� ���ε�� ��Ƽĳ��Ʈ ������ ������.");	   	  
	   msocket = new MulticastSocket();
	   msocket.setSoTimeout(10000);
       msocket.setTimeToLive(1); // ���ۿ����� ���� ��Ʈ��ũ�� ����. 

       String sendmsg = "This is a multicast data";
       byte data[] = sendmsg.getBytes();
       packet = new DatagramPacket(data, data.length, group, port);
	    System.out.println("��Ƽĳ��Ʈ �޽����� �������Դϴ� : "+sendmsg);
	    msocket.send(packet);
	   
	   
	  } catch (IOException e) {
	   e.printStackTrace();
	  }
	 }
}