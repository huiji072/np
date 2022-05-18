package ch13;

import java.net.*;
import java.io.*;
public class MultiReceiver
{
   public static void main(String args[]){
      int port = 9006;
      try{
         InetAddress group = InetAddress.getByName("239.255.10.10");
         System.out.println(group.getHostAddress()+" �׷� �ּ� �� "+port+" ��Ʈ�� ���ε�� ��Ƽĳ��Ʈ ������ ������.");
         MulticastSocket msocket = new MulticastSocket(port);
         msocket.setSoTimeout(10000);
         msocket.setTimeToLive(1); // ���ۿ����� ���� ��Ʈ��ũ�� ����. 
         System.out.println("��Ƽĳ��Ʈ �׷쿡 �����ϰ� �ֽ��ϴ�.");
         msocket.joinGroup(group);

         byte[] buffer = new byte[512];
         DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

         // loop-back�Ǵ� ��Ƽĳ��Ʈ �����͸� �����Ѵ�.

         packet.setData(new byte[512]);
         packet.setLength(512);    // ������ ũ�⸦ ������.
         System.out.println("��Ƽĳ��Ʈ ��Ŷ�� �����ϰ� �ֽ��ϴ�.");
         msocket.receive(packet);
         String receivemsg = new String(packet.getData(), 0, packet.getLength());
         System.out.println("���ŵ� �޽����� : "+receivemsg);

         System.out.println("��Ƽĳ��Ʈ �׷��� Ż���մϴ�.");
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