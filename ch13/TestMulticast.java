package ch13;

import java.net.*;
import java.io.*;
public class TestMulticast
{
   public static void main(String args[]){
      int port = 5265;
      try{
         InetAddress group = InetAddress.getByName("239.255.10.10");
         System.out.println(group.getHostAddress()+" 그룹 주소 및 "+port+" 포트에 바인드된 멀티캐스트 소켓을 생성함.");
         MulticastSocket msocket = new MulticastSocket(port);
         msocket.setSoTimeout(10000);
         msocket.setTimeToLive(1); // 전송영역을 로컬 네트워크에 한함. 
         System.out.println("멀티캐스트 그룹에 가입하고 있습니다.");
         msocket.joinGroup(group);

         // 멀티캐스트 패킷을 생성하고 전송한다.

         String sendmsg = "This is a multicast data";
         byte data[] = sendmsg.getBytes();
         DatagramPacket packet = new DatagramPacket(data, data.length, group, port);
         System.out.println("멀티캐스트 메시지를 전송중입니다 : "+sendmsg);
         msocket.send(packet);

         // loop-back되는 멀티캐스트 데이터를 수신한다.

         packet.setData(new byte[512]);
         packet.setLength(512);    // 버퍼의 크기를 설정함.
         System.out.println("멀티캐스트 패킷을 수신하고 있습니다.");
         msocket.receive(packet);
         String receivemsg = new String(packet.getData(), 0, packet.getLength());
         System.out.println("수신된 메시지는 : "+receivemsg);

         System.out.println("멀티캐스트 그룹을 탈퇴합니다.");
         msocket.leaveGroup(group);
         msocket.close();
      }catch(UnknownHostException e){
         System.out.println(e);
      }catch(SocketException e){
         System.out.println(e);
      }catch(IOException e){
         System.out.println(e);
      }
   }
}