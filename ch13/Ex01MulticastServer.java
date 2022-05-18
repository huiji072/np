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
	   InetAddress group = InetAddress.getByName("224.128.1.5"); // 멀티캐스트 방식으로 서버 주소를 설정함.
	   System.out.println(group.getHostAddress()+" 그룹 주소 및 "+port+" 포트에 바인드된 멀티캐스트 소켓을 생성함.");	   	  
	   msocket = new MulticastSocket();
	   msocket.setSoTimeout(10000);
       msocket.setTimeToLive(1); // 전송영역을 로컬 네트워크에 한함. 

       String sendmsg = "This is a multicast data";
       byte data[] = sendmsg.getBytes();
       packet = new DatagramPacket(data, data.length, group, port);
	    System.out.println("멀티캐스트 메시지를 전송중입니다 : "+sendmsg);
	    msocket.send(packet);
	   
	   
	  } catch (IOException e) {
	   e.printStackTrace();
	  }
	 }
}