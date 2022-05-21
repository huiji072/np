package test;

import java.io.*;
import java.net.*;
import java.util.*;
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
	
private static final String SEPARATOR = "|";
private static final int REQ_LOGON = 1001;
private static final int REQ_SENDWORDS = 1021;
private static final int REQ_WISPERSEND = 1022;
private static final int REQ_LOGOUT = 1002;
public MultiChatC() {
   super("클라이언트");

   mlbl = new Label("채팅 상태를 보여줍니다.");
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
      client = new Socket(InetAddress.getLocalHost(), 5000);
      mlbl.setText("연결된 서버이름 : " + client.getInetAddress().getHostName());
      input = new BufferedReader(new InputStreamReader(client.getInputStream()));
      output = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
      clientdata = new StringBuffer(2048);
      mlbl.setText("접속 완료 사용할 아이디를 입력하세요.");
      while(true) {
         serverdata = input.readLine();
         display.append(serverdata+"\r\n");
         output.flush();
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

public static void main(String args[]) {
MultiChatC c = new MultiChatC();
c.runClient();
}
		
public void actionPerformed(ActionEvent ae){
	logout.setVisible(false); //로그아웃 버튼이 보이지 않게s
   if(ID == null) {
//	   System.out.println(serverdata);
      ID = ltext.getText();
      loglbl.setVisible(false);
      logout.setVisible(true); //로그아웃 버튼이 보이게 한다.
      ltext.setVisible(false);
      mlbl.setText(ID + "(으)로 로그인 하였습니다.");
		 ltext.setText("");
      try {
         clientdata.setLength(0);
         clientdata.append(REQ_LOGON);
         clientdata.append(SEPARATOR);
         clientdata.append(ID);
         output.write(clientdata.toString()+"\r\n");
         output.flush();

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
			output.write(clientdata.toString() + "\r\n");
			output.flush();
			ID = null;
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
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
			output.write(clientdata.toString() + "\r\n");
			output.flush();
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
          mlbl.setText("다시 로그인 하세요!!!");
          wtext.setText("");
       } else {
          try {
             clientdata.setLength(0);
             clientdata.append(REQ_SENDWORDS);
             clientdata.append(SEPARATOR);
             clientdata.append(ID);
             clientdata.append(SEPARATOR);
             clientdata.append(message);
             output.write(clientdata.toString()+"\r\n");
             output.flush();
             wtext.setText("");
          } catch (IOException e) {
             e.printStackTrace();
          }
       }
    }
 }

public void keyReleased(KeyEvent ke) {
}

public void keyTyped(KeyEvent ke) {
}
}