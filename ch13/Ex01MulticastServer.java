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
	   InetAddress address = InetAddress.getByName("224.128.1.5"); // 멀티캐스트 방식으로 서버 주소를 설정함.
	   System.out.println(address.getHostAddress()+" 그룹 주소 및 "+9006+" 포트에 바인드된 멀티캐스트 소켓을 생성함.");	   	  
	   msocket.setSoTimeout(10000);
       msocket.setTimeToLive(1); // 전송영역을 로컬 네트워크에 한함. 

       String sendmsg = "This is a multicast data";
	    packet = new DatagramPacket(sendmsg.getBytes(), sendmsg.getBytes().length, address, 9006);
	    System.out.println("멀티캐스트 메시지를 전송중입니다 : "+sendmsg);
	    msocket.send(packet);
	   
	   
	  } catch (IOException e) {
	   e.printStackTrace();
	  }
	 }
}

