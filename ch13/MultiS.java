package ch13;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.event.*;

public class MultiS extends Frame {
	 protected InetAddress group;
	   protected int port;
   TextArea display;
   Label info;
   List<ServerThread> list;
   protected MulticastSocket socket;
   protected DatagramPacket outgoing, incoming;
   public ServerThread SThread;
	
   public MultiS(InetAddress group, int port){
      super("����");
      this.group = group;
      this.port = port;
      initAWT();
   }
   
   protected void initAWT() {
	      info = new Label();
	      add(info, BorderLayout.CENTER);
	      display = new TextArea("", 0, 0, TextArea.SCROLLBARS_VERTICAL_ONLY);
	      display.setEditable(false);
	      add(display, BorderLayout.SOUTH);
	      addWindowListener(new WinListener());
	      setSize(300,250);
	      setVisible(true);
   }
	
   protected void initNet() throws IOException{
	      socket = new MulticastSocket();
	      socket.setTimeToLive(1);
	      outgoing = new DatagramPacket(new byte[1], 1, group, port);
	      incoming = new DatagramPacket(new byte[65508], 65508);
	   }
   
//   public void run(){
//	      try{
//	    	  initNet();
//	         while(!Thread.interrupted()){
//	            incoming.setLength(incoming.getData().length);
//	            socket.receive(incoming);
//	            String message = new String(incoming.getData(), 0, incoming.getLength(), "UTF8");
//	            display.append(message+"\n");
//	         }
//	      }catch(IOException e){
//	         e.printStackTrace();
//	      }
//	   }

   
   public void runServer() {
      ServerSocket server;
      Socket sock;
      ServerThread SThread;
      try {
    	  initNet();
         list = new ArrayList<ServerThread>();
         server = new ServerSocket(5000, 100);
         try {
            while(true) {
               sock = server.accept();
               SThread = new ServerThread(this, sock, display, info);
               SThread.start();
               info.setText(sock.getInetAddress().getHostName() + " ������ Ŭ���̾�Ʈ�� �����");
            }
         } catch(IOException ioe) {
            server.close();
            ioe.printStackTrace();
         }
      } catch(IOException ioe) {
         ioe.printStackTrace();
      }
   }
   
   
		
   public static void main(String args[]) throws IOException {
	   if((args.length != 1) || (args[0].indexOf(":") < 0)) // ��Ƽĳ��Ʈ�ּ�:��Ʈ��ȣ ���·� �Է��� �ؾ���.
	          throw new IllegalArgumentException("�߸��� ��Ƽĳ��Ʈ �ּ��Դϴ�.");
	       int idx = args[0].indexOf(":");
	       InetAddress group = InetAddress.getByName(args[0].substring(0, idx));
	       int port = Integer.parseInt(args[0].substring(idx+1));
      MultiS s = new MultiS(group,port);
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
   MultiS cs;
	
   private static final String SEPARATOR = "|";
   private static final int REQ_LOGON = 1001;
   private static final int REQ_SENDWORDS = 1021;
   private static final int REQ_LOGOUT = 1002;
private static final int REQ_LOGON_OVERLAP = 1003;
   public ServerThread(MultiS c, Socket s, TextArea ta, Label l) {
      sock = s;
      display = ta;
      info = l;
      cs = c;
      try {
         input = new BufferedReader(new InputStreamReader(sock.getInputStream()));
         output = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
      } catch(IOException ioe) {
         ioe.printStackTrace();
      }
   }
   public void run() {
      cs.list.add(this);
      try {
         while((clientdata = input.readLine()) != null) {
            StringTokenizer st = new StringTokenizer(clientdata, SEPARATOR);
            int command = Integer.parseInt(st.nextToken());
            int cnt = cs.list.size();
            switch(command) {
               case REQ_LOGON : { // ��1001|���̵𡱸� ������ ���
                  String ID = st.nextToken();
                  display.append("Ŭ���̾�Ʈ�� " + ID + "(��)�� �α��� �Ͽ����ϴ�.\r\n");
                  break;
               }
               case REQ_SENDWORDS : { // ��1021|���̵�|��ȭ������ ����
                  String ID = st.nextToken();
                  String message = st.nextToken();
                  display.append(ID + " : " + message + "\r\n");
                  for(int i=0; i<cnt; i++) { // ��� Ŭ���̾�Ʈ�� ����
                     ServerThread SThread = (ServerThread)cs.list.get(i);
                     SThread.output.write(ID + " : " + message + "\r\n");
                     SThread.output.flush();
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