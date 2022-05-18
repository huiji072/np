package ch13;

import java.net.*;
import java.io.*;
public class MultiSender
{
   public static void main(String args[]){
      int port = 5265;
      try{
         InetAddress group = InetAddress.getByName("239.255.10.10");
         System.out.println(group.getHostAddress()+" 그룹 주소 및 "+port+" 포트에 바인드된 멀티캐스트 소켓을 생성함.");
         MulticastSocket msocket = new MulticastSocket();
         msocket.setSoTimeout(10000);
         msocket.setTimeToLive(1); // 전송영역을 로컬 네트워크에 한함. 

         // 멀티캐스트 패킷을 생성하고 전송한다.

         String sendmsg = "This is a multicast data";
         byte data[] = sendmsg.getBytes();
         DatagramPacket packet = new DatagramPacket(data, data.length, group, port);
         System.out.println("멀티캐스트 메시지를 전송중입니다 : "+sendmsg);
         msocket.send(packet);

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