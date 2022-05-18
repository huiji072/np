package ch13;

import java.net.*;
import java.io.*;
public class MultiSender
{
   public static void main(String args[]){
      int port = 5265;
      try{
         InetAddress group = InetAddress.getByName("239.255.10.10");
         System.out.println(group.getHostAddress()+" �׷� �ּ� �� "+port+" ��Ʈ�� ���ε�� ��Ƽĳ��Ʈ ������ ������.");
         MulticastSocket msocket = new MulticastSocket();
         msocket.setSoTimeout(10000);
         msocket.setTimeToLive(1); // ���ۿ����� ���� ��Ʈ��ũ�� ����. 

         // ��Ƽĳ��Ʈ ��Ŷ�� �����ϰ� �����Ѵ�.

         String sendmsg = "This is a multicast data";
         byte data[] = sendmsg.getBytes();
         DatagramPacket packet = new DatagramPacket(data, data.length, group, port);
         System.out.println("��Ƽĳ��Ʈ �޽����� �������Դϴ� : "+sendmsg);
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