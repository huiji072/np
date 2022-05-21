package test;

import java.io.*;
import java.net.*;
import java.util.*;

import test.MulC.ClientThread;

import java.awt.*;
import java.awt.event.*;

public class MultiChatC extends Frame implements ActionListener, KeyListener {
	
TextArea display;
TextField wtext, ltext;
Label mlbl, wlbl, loglbl;
BufferedWriter output;
BufferedReader input;
Socket client;
StringBuffer clientdata;
String serverdata = "";
String ID;
Button logout;
MulticastSocket msocket;
DatagramPacket outgoing, incoming;
InetAddress group;
int port = 5265;
byte[] data = new byte[1024];
DatagramSocket theSocket;
public ClientThread CThread;
private static final String SEPARATOR = "|";
private static final int REQ_LOGON = 1001;
private static final int REQ_SENDWORDS = 1021;
private static final int REQ_WISPERSEND = 1022;
private static final int REQ_LOGOUT = 1002;
public MultiChatC() {
   super("클라이언트");

   mlbl = new Label("멀티캐스트 채팅 서버에 가입 요청합니다.");
   add(mlbl, BorderLayout.NORTH);

   display = new TextArea("", 0, 0, TextArea.SCROLLBARS_VERTICAL_ONLY);
   display.setEditable(false);
   add(display, BorderLayout.CENTER);

   Panel ptotal = new Panel(new BorderLayout());
	
	Panel pword = new Panel(new BorderLayout());
	wlbl = new Label("대화말");
	wtext = new TextField(26); //전송할 데이터를 입력하는 필드
	wtext.addKeyListener(this); //입력된 데이터를 송신하기 위한 이벤트 연결
	pword.add(wlbl, BorderLayout.WEST);
	pword.add(wtext, BorderLayout.EAST);
	ptotal.add(pword, BorderLayout.CENTER);
	
	Panel plabel = new Panel(new BorderLayout());
	loglbl = new Label("로그온");
	ltext = new TextField(26); //전송할 데이터를 입력하는 필드
	ltext.addActionListener(this); //입력된 데이터를 송신하기 위한 이벤트 연결
	logout = new Button("로그아웃"); //로그아웃 버튼
	logout.addActionListener(this);
	logout.setVisible(false); //로그아웃 버튼은 초기에 안보이게 한다.
	plabel.add(logout, BorderLayout.CENTER);
	plabel.add(loglbl, BorderLayout.WEST);
	plabel.add(ltext, BorderLayout.EAST);
	ptotal.add(plabel, BorderLayout.SOUTH);
	
	add(ptotal, BorderLayout.SOUTH);

   addWindowListener(new WinListener());
   setSize(300,250);
   setVisible(true);
}
	
public void runClient() {
   try {
	   
//      mlbl.setText("연결된 서버이름 : " + client.getInetAddress().getHostName());
      clientdata = new StringBuffer(2048);
//      mlbl.setText("접속 완료 사용할 아이디를 입력하세요.");
      theSocket = new DatagramSocket();
      outgoing = new DatagramPacket(new byte[1], 1, InetAddress.getLocalHost(), 5000);
		incoming = new DatagramPacket(new byte[60000], 60000);
		MulticastSocket mssocket = new MulticastSocket(port);
		
		while (true) {
			incoming.setLength(incoming.getData().length);
			msocket.receive(incoming);
//			theSocket.receive(incoming);
			String message = new String(incoming.getData(), 0, incoming.getLength());
			display.append(message);
	         if(serverdata == "중복된 ID입니다.") {
					mlbl.setText("중복된 ID 입니다!!!");
					ltext.setText("");
					ID = null;
					logout.setVisible(false); //로그아웃 버튼이 보이지 않게
				}
		}

   } catch(IOException e) {
      e.printStackTrace();
   }
}


		
@SuppressWarnings("deprecation")
public void actionPerformed(ActionEvent ae){
	logout.setVisible(false); //로그아웃 버튼이 보이지 않게s
   if(ID == null) {
//	   System.out.println(serverdata);
	   ID = ltext.getText();
       loglbl.setVisible(false);
       logout.setVisible(true); //로그아웃 버튼이 보이게 한다.
       ltext.setVisible(false);
 		 ltext.setText("");
      try {
    	  clientdata.setLength(0);
         clientdata.append(REQ_LOGON);
         clientdata.append(SEPARATOR);
         clientdata.append(ID);
         clientdata.append("로그인 하였습니다.\r\n");
         
         data = new String(clientdata).getBytes();
         outgoing.setData(data);
         outgoing.setLength(data.length);
         theSocket.send(outgoing);
         
         theSocket.receive(incoming);
         String address = new String(incoming.getData(), 0, incoming.getLength());
			StringTokenizer st = new StringTokenizer(address, SEPARATOR);
			String address2 = st.nextToken();
			address2 = address2.replace("/", "");
			group = InetAddress.getByName(address2);
			port = Integer.parseInt(st.nextToken());
			
//			MulticastSocket msocket = new MulticastSocket(port);
			msocket.joinGroup(group);			


      } catch(Exception e) {
         e.printStackTrace();
      }
   }
   
	if(ae.getSource().equals(logout)) { //로그아웃 버튼을 누르면
		mlbl.setText("");		
		ltext.setVisible(true); 
		loglbl.setVisible(true); //로그인 입력창 보임
		logout.setVisible(false); //로그아웃 버튼 안보임
		ID = ltext.getText();
		mlbl.setText(ID + "(이)가 로그아웃 하였습니다.");
		display.setText("");
		try {
			ltext.setText("");
			clientdata.setLength(0);
			clientdata.append(REQ_LOGOUT);
			clientdata.append(SEPARATOR);
			clientdata.append(ID);
			 data = new String(clientdata).getBytes();
	         outgoing.setData(data);
	         outgoing.setLength(data.length);
	         theSocket.send(outgoing);
			ID = null;
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
}

public static void main(String args[]) {
MultiChatC c = new MultiChatC();
c.runClient();
}

class WinListener extends WindowAdapter {
   public void windowClosing(WindowEvent e){
	   ID = ltext.getText();
		mlbl.setText(ID + "(이)가 로그아웃 하였습니다.");
		try {
			clientdata.setLength(0);
			clientdata.append(REQ_LOGOUT);
			clientdata.append(SEPARATOR);
			clientdata.append(ID);
			 data = new String(clientdata).getBytes();
	         outgoing.setData(data);
	         outgoing.setLength(data.length);
	         theSocket.send(outgoing);
			ID = null;
		}catch(IOException e2) {
			e2.printStackTrace();
		}
		System.exit(0);
   }
}

public void keyPressed(KeyEvent ke) {
    if(ke.getKeyChar() == KeyEvent.VK_ENTER) {
       String message = new String();
       message = wtext.getText();
       if (ID == null) {
          mlbl.setText("로그인 후 이용 하세요!!!");
          wtext.setText("");
       } else {
          try {
             clientdata.setLength(0);
             clientdata.append(REQ_SENDWORDS);
             clientdata.append(SEPARATOR);
             clientdata.append(ID);
             clientdata.append(SEPARATOR);
             clientdata.append(message);
             data = new String(clientdata).getBytes();
             outgoing.setData(data);
             outgoing.setLength(data.length);
             theSocket.send(outgoing);
             wtext.setText("");
             
 			incoming.setLength(incoming.getData().length);
 			msocket.receive(incoming);
 			String message1 = new String(incoming.getData(), 0, incoming.getLength());
 			display.append(message1);
          } catch (IOException e) {
             e.printStackTrace();
          }
       }
    }
 }

//class ClientThread extends Thread {
//	MulticastSocket mssocket;
//
//	public ClientThread(MulticastSocket ms) {
//		mssocket = ms;
//	}
//
//	public void run() {
//		try {
//			while (!Thread.interrupted()) {
//				incoming.setLength(incoming.getData().length);
//				mssocket.receive(incoming);
//				String message = new String(incoming.getData(), 0, incoming.getLength());
//				display.append(message);
//		         if(serverdata == "중복된 ID입니다.") {
//						mlbl.setText("중복된 ID 입니다!!!");
//						ltext.setText("");
//						ID = null;
//						logout.setVisible(false); //로그아웃 버튼이 보이지 않게
//					}
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//}

public void keyReleased(KeyEvent ke) {
}

public void keyTyped(KeyEvent ke) {
}

}