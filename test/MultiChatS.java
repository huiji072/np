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
         server = new ServerSocket(5000, 100);
         hash = new Hashtable();
         list = new ArrayList<ServerThread>();
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
	
   private static final String SEPARATOR = "|";
   private static final int REQ_LOGON = 1001;
   private static final int REQ_SENDWORDS = 1021;
   private static final int REQ_WISPERSEND = 1022;
   private static final int REQ_LOGOUT = 1002;
   public ServerThread(MultiChatS c, Socket s, TextArea ta, Label l) {
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
      try {
         cs.list.add(this);
         while((clientdata = input.readLine()) != null) {
            StringTokenizer st = new StringTokenizer(clientdata, SEPARATOR);
            int command = Integer.parseInt(st.nextToken());
            int Lcnt = cs.list.size();
            switch(command) {
               case REQ_LOGON : {
                  String ID = st.nextToken();
                  if(cs.hash.containsKey(ID) == true) { //�̹� �ִ� ���̵��� ��
//						ServerThread SThread = (ServerThread)cs.hash.get(ID);
						display.append(ID + "�� �̹� �ִ� ���̵� �Դϴ�.\r\n"); //���� ȭ�鿡 ���
						output.write("�ߺ��� ID�Դϴ�." + "\r\n"); //�ߺ� ���̵�. Ŭ�󸮾�Ʈ�� ����
						output.flush();
						break;
					}
                  display.append("Ŭ���̾�Ʈ�� " + ID + "(��)�� �α��� �Ͽ����ϴ�.\r\n");
                  cs.hash.put(ID, this); // �ؽ� ���̺� ���̵�� �����带 �����Ѵ�
                  break;
               }
               case REQ_SENDWORDS : {
                  String ID = st.nextToken();
                  String message = st.nextToken();
                  display.append(ID + " : " + message + "\r\n");
                  for(int i=0; i<Lcnt; i++) {
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