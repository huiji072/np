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
	   // 그룹에 조인(라우터가 보냄)
	   InetAddress address = InetAddress.getByName("224.128.1.5"); // 멀티 캐스트를 위한 아이피 설정
	   System.out.println(address.getHostAddress()+" 그룹 주소 및 "+9006+" 포트에 바인드된 멀티캐스트 소켓을 생성함.");
	   msocket.setSoTimeout(10000);
       msocket.setTimeToLive(1); // 전송영역을 로컬 네트워크에 한함. 
       System.out.println("멀티캐스트 그룹에 가입하고 있습니다.");
	   msocket.joinGroup(address);
	   
	   byte[] buf = new byte[512];
	   packet = new DatagramPacket(buf, buf.length);
	   

	    // 패킷 수신
	   System.out.println("멀티캐스트 패킷을 수신하고 있습니다.");
	    msocket.receive(packet);
	    String receivemsg = new String(packet.getData(), 0, packet.getLength());
        System.out.println("수신된 메시지는 : "+receivemsg);
	   
        System.out.println("멀티캐스트 그룹을 탈퇴합니다.");
        msocket.leaveGroup(address);
        msocket.close();
        
	  } catch (SocketException e) {
	   e.printStackTrace();
	  } catch (IOException e) {
	   e.printStackTrace();
	  }
	 }

}

