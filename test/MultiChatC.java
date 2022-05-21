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
   super("Ŭ���̾�Ʈ");

   mlbl = new Label("ä�� ���¸� �����ݴϴ�.");
   add(mlbl, BorderLayout.NORTH);

   display = new TextArea("", 0, 0, TextArea.SCROLLBARS_VERTICAL_ONLY);
   display.setEditable(false);
   add(display, BorderLayout.CENTER);

   Panel ptotal = new Panel(new BorderLayout());
	
	Panel pword = new Panel(new BorderLayout());
	wlbl = new Label("��ȭ��");
	wtext = new TextField(26); //������ �����͸� �Է��ϴ� �ʵ�
	wtext.addKeyListener(this); //�Էµ� �����͸� �۽��ϱ� ���� �̺�Ʈ ����
	pword.add(wlbl, BorderLayout.WEST);
	pword.add(wtext, BorderLayout.EAST);
	ptotal.add(pword, BorderLayout.CENTER);
	
	Panel plabel = new Panel(new BorderLayout());
	loglbl = new Label("�α׿�");
	ltext = new TextField(26); //������ �����͸� �Է��ϴ� �ʵ�
	ltext.addActionListener(this); //�Էµ� �����͸� �۽��ϱ� ���� �̺�Ʈ ����
	logout = new Button("�α׾ƿ�"); //�α׾ƿ� ��ư
	logout.addActionListener(this);
	logout.setVisible(false); //�α׾ƿ� ��ư�� �ʱ⿡ �Ⱥ��̰� �Ѵ�.
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
      mlbl.setText("����� �����̸� : " + client.getInetAddress().getHostName());
      input = new BufferedReader(new InputStreamReader(client.getInputStream()));
      output = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
      clientdata = new StringBuffer(2048);
      mlbl.setText("���� �Ϸ� ����� ���̵� �Է��ϼ���.");
      while(true) {
         serverdata = input.readLine();
         display.append(serverdata+"\r\n");
         output.flush();
         if(serverdata == "�ߺ��� ID�Դϴ�.") {
				mlbl.setText("�ߺ��� ID �Դϴ�!!!");
				ltext.setText("");
				ID = null;
				logout.setVisible(false); //�α׾ƿ� ��ư�� ������ �ʰ�
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
	logout.setVisible(false); //�α׾ƿ� ��ư�� ������ �ʰ�s
   if(ID == null) {
//	   System.out.println(serverdata);
      ID = ltext.getText();
      loglbl.setVisible(false);
      logout.setVisible(true); //�α׾ƿ� ��ư�� ���̰� �Ѵ�.
      ltext.setVisible(false);
      mlbl.setText(ID + "(��)�� �α��� �Ͽ����ϴ�.");
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
   
	if(ae.getSource().equals(logout)) { //�α׾ƿ� ��ư�� ������
		mlbl.setText("");		
		ltext.setVisible(true); 
		loglbl.setVisible(true); //�α��� �Է�â ����
		logout.setVisible(false); //�α׾ƿ� ��ư �Ⱥ���
		ID = ltext.getText();
		mlbl.setText(ID + "(��)�� �α׾ƿ� �Ͽ����ϴ�.");
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
		mlbl.setText(ID + "(��)�� �α׾ƿ� �Ͽ����ϴ�.");
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
          mlbl.setText("�ٽ� �α��� �ϼ���!!!");
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