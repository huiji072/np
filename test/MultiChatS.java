package test;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.event.*;

public class MultiChatS extends Frame {
   TextArea display;
   Label info;
   List<ServerThread> list;
   Hashtable hash;
   public ServerThread SThread;
   MulticastSocket msocket;
   DatagramPacket outgoing, incoming;
   String group = "239.10.1.1";
   InetAddress ia;
   int port = 5265;
   byte[] data = new byte[1024];
   DatagramSocket theSocket;
   public MultiChatS() {
      super("����");
      info = new Label();
      add(info, BorderLayout.CENTER);
      display = new TextArea("", 0, 0, TextArea.SCROLLBARS_VERTICAL_ONLY);
      display.setEditable(false);
      add(display, BorderLayout.SOUTH);
      addWindowListener(new WinListener());
      setSize(300,250);
      setVisible(true);
   }
	
   public void runServer() {
      ServerSocket server;
      Socket sock;
      ServerThread SThread;
      try {
//			ia = InetAddress.getByName(group);
			theSocket = new DatagramSocket(5000);
			outgoing = new DatagramPacket(new byte[1], 1);
			incoming = new DatagramPacket(new byte[60000], 60000);
			info.setText("��Ƽĳ��Ʈ ä�� �׷� �ּ� : " + group);
			msocket = new MulticastSocket();
         hash = new Hashtable();
         list = new ArrayList<ServerThread>();
         while(true) {
		   SThread = new ServerThread(this, theSocket, display, info);
		   SThread.start();
		}
      } catch(IOException ioe) {
         ioe.printStackTrace();
      }
			
   }

   public static void main(String args[]) {
      MultiChatS s = new MultiChatS();
      s.runServer();
   }
		
   class WinListener extends WindowAdapter {
      public void windowClosing(WindowEvent e) {
         System.exit(0);
      }
   }
}

class ServerThread extends Thread {
   Socket sock;
   BufferedWriter output;
   BufferedReader input;
   TextArea display;
   Label info;
   TextField text;
   String clientdata;
   String serverdata = "";
   MultiChatS cs;
	MulticastSocket msocket;
	   DatagramPacket outgoing, incoming;
	   String group = "239.10.1.1";
	   InetAddress ia;
	   int port = 5265;
	   byte[] data = new byte[1024];
	   DatagramSocket theSocket;
   private static final String SEPARATOR = "|";
   private static final int REQ_LOGON = 1001;
   private static final int REQ_SENDWORDS = 1021;
   private static final int REQ_WISPERSEND = 1022;
   private static final int REQ_LOGOUT = 1002;
   public ServerThread(MultiChatS c, DatagramSocket s, TextArea ta, Label l) throws IOException {
	   theSocket = s;
      display = ta;
      info = l;
      cs = c;
      ia = InetAddress.getByName(group);
      outgoing = new DatagramPacket(new byte[1], 1);
		incoming = new DatagramPacket(new byte[60000], 60000);
   }
   public void run() {
      try {
         cs.list.add(this);
         while((clientdata = incoming.toString()) != null) {
        	 incoming.setLength(incoming.getData().length);
        	 theSocket.receive(incoming);
        	 clientdata = new String(incoming.getData(), 0, incoming.getLength());
        	 System.out.println(clientdata);
        	 
            StringTokenizer st = new StringTokenizer(clientdata, SEPARATOR);
            int command = Integer.parseInt(st.nextToken());
            int Lcnt = cs.list.size();
            switch(command) {
               case REQ_LOGON : {
                  String ID = st.nextToken();
                  if(cs.hash.containsKey(ID) == true) { //�̹� �ִ� ���̵��� ��
//						ServerThread SThread = (ServerThread)cs.hash.get(ID);
						display.append(ID + "�� �̹� �ִ� ���̵� �Դϴ�.\r\n"); //���� ȭ�鿡 ���

						String sdata = "�ߺ��� ID�Դϴ�." + "\r\n";
						data = new String(sdata).getBytes();
						outgoing.setData(data);
	                     outgoing.setLength(data.length);
	                     outgoing.setAddress(ia);
	                     outgoing.setPort(port);
	                     theSocket.send(outgoing);
						break;
					}
                  String sdata = "��Ƽĳ��Ʈ ä�� �׷� �ּҴ�" + ia + SEPARATOR + 
                		  Integer.toString(port) + "�Դϴ�.\r\n";
                  System.out.println("��Ƽĳ��Ʈ ä�� �׷� �ּҴ�" + ia + SEPARATOR + 
                		  Integer.toString(port) + "�Դϴ�.\r\n");
                  data = new String(sdata).getBytes();
                  outgoing.setData(data);
                  outgoing.setLength(data.length);
                  outgoing.setAddress(ia);
                  outgoing.setPort(port);
                  theSocket.send(outgoing);
                  display.append("Ŭ���̾�Ʈ�� " + ID);
                  cs.hash.put(ID, this); // �ؽ� ���̺� ���̵�� �����带 �����Ѵ�

                  
                  break;
               }
               case REQ_SENDWORDS : {
                  String ID = st.nextToken();
                  String message = st.nextToken();
                  
                  display.append(ID + " : " + message + "\r\n");
                  for(int i=0; i<Lcnt; i++) {
                     ServerThread SThread = (ServerThread)cs.list.get(i);
                     String sdata = ID + " : " + message + "\r\n";
                     data = new String(sdata).getBytes();
                     outgoing.setData(data);
                     outgoing.setLength(data.length);
                     outgoing.setAddress(ia);
                     outgoing.setPort(port);
                     msocket.send(outgoing);
                  }
                  break;
               }
               case REQ_LOGOUT : { //�α׾ƿ� ��� �߰�
					cs.list.remove(this); //�α׾ƿ� �� Ŭ���̾�Ʈ ����
					String ID = st.nextToken();
					display.append("Ŭ���̾�Ʈ " + ID + "(��)�� �α׾ƿ� �Ͽ����ϴ�.\r\n");
					break;
				}
            }
         }
      } catch(IOException e) {
         e.printStackTrace();
      }
      cs.list.remove(this);
      try{
         sock.close();
      }catch(IOException ea){
         ea.printStackTrace();
      }
   }
}