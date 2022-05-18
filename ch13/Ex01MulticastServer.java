package ch13;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class Ex01MulticastServer {

 public static void main(String[] args) {
  
	  DatagramPacket packet = null;
	  MulticastSocket msocket = null;
	  
	  try {
	   msocket = new MulticastSocket();
	   InetAddress address = InetAddress.getByName("224.128.1.5"); // ��Ƽĳ��Ʈ ������� ���� �ּҸ� ������.
	   System.out.println(address.getHostAddress()+" �׷� �ּ� �� "+9006+" ��Ʈ�� ���ε�� ��Ƽĳ��Ʈ ������ ������.");	   	  
	   msocket.setSoTimeout(10000);
       msocket.setTimeToLive(1); // ���ۿ����� ���� ��Ʈ��ũ�� ����. 

       String sendmsg = "This is a multicast data";
	    packet = new DatagramPacket(sendmsg.getBytes(), sendmsg.getBytes().length, address, 9006);
	    System.out.println("��Ƽĳ��Ʈ �޽����� �������Դϴ� : "+sendmsg);
	    msocket.send(packet);
	   
	   
	  } catch (IOException e) {
	   e.printStackTrace();
	  }
	 }
}

