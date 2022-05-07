<<<<<<< HEAD
package ch9;
=======
>>>>>>> 19d0b2ca2316b20174aabaf4db36ac7d4d8bdbd8

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.List;

public class OneToOneS extends Frame implements ActionListener{
	
	TextArea display;
	TextField text;
	Label lword;
	Socket connection;
	BufferedWriter output;
	BufferedReader input;
	String clienddata="";
	String serverdata = "";
	ServerSocket server;
	List <ServerThread> list;
	
	public OneToOneS() {
<<<<<<< HEAD
		super("¼­¹ö");
=======
		super("ì„œë²„");
>>>>>>> 19d0b2ca2316b20174aabaf4db36ac7d4d8bdbd8
		display = new TextArea("", 0, 0, TextArea.SCROLLBARS_VERTICAL_ONLY);
		display.setEditable(false);
		add(display, BorderLayout.CENTER);
		
		
		Panel pword = new Panel(new BorderLayout());
<<<<<<< HEAD
		lword = new Label("´ëÈ­¸»");
=======
		lword = new Label("ëŒ€í™”ë§");
>>>>>>> 19d0b2ca2316b20174aabaf4db36ac7d4d8bdbd8
		text = new TextField(30);
		text.addActionListener(this);
		pword.add(lword, BorderLayout.WEST);
		pword.add(text, BorderLayout.CENTER);
		add(pword, BorderLayout.SOUTH);
		
		addWindowListener(new WinListener());
		setSize(300, 200);
		setVisible(true);
	}
	
	public void runServer() {
		ServerSocket server;
		try {
//			list = new ArrayList<ServerThread>();
			server = new ServerSocket(5000, 100);
			try {
				while(true) {
					ServerThread SThread = null;
<<<<<<< HEAD
					connection = server.accept();//Á¢¼Ó¿äÃ»ÀÌ ¿Ã ¶§±îÁö ±â´Ù¸°´Ù.
					SThread = new ServerThread(connection, display);
					SThread.start();
					OutputStream os = connection.getOutputStream();
					OutputStreamWriter osw = new OutputStreamWriter(os); //Å¬¶óÀÌ¾ðÆ®¿¡ ´ëÈ­¸» Àü¼Û
					output = new BufferedWriter(osw);
=======
					connection = server.accept();//ì ‘ì†ìš”ì²­ì´ ì˜¬ ë•Œê¹Œì§€ ê¸°ë‹¤ë¦°ë‹¤.
					SThread = new ServerThread(connection, display);
					SThread.start();
	
>>>>>>> 19d0b2ca2316b20174aabaf4db36ac7d4d8bdbd8
				}
			}catch(IOException e) {
				server.close();
				server=null;
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
<<<<<<< HEAD
=======
		//ì»¤ë„¥ì…˜ ===null ì´ë©´ conection = socket ìŠ¤ë ˆë“œ ìƒì„±,ìŠ¤ë ˆë“œ ì‹œìž‘
>>>>>>> 19d0b2ca2316b20174aabaf4db36ac7d4d8bdbd8
	}
	
	public void actionPerformed(ActionEvent ae) {

		if(connection == null) {
<<<<<<< HEAD
			display.append("¿¬°áµÈ Å¬¶óÀÌ¾ðÆ®°¡ ¾ø½À´Ï´Ù.");
=======
			display.append("ì—°ê²°ëœ í´ë¼ì´ì–¸íŠ¸ê°€ ì—†ìŠµë‹ˆë‹¤.");
>>>>>>> 19d0b2ca2316b20174aabaf4db36ac7d4d8bdbd8
			text.setText("");
			return;
		}
		
		serverdata = text.getText();
<<<<<<< HEAD
	
		try {
			display.append("\n¼­¹ö : " + serverdata); //o
			output.write(serverdata + "\r\n");
			System.out.println(serverdata);
=======
		try {
			display.append("\nì„œë²„ : " + serverdata);
			output.write(serverdata + "\r\n");
>>>>>>> 19d0b2ca2316b20174aabaf4db36ac7d4d8bdbd8
			output.flush();
			text.setText("");
			
			if(serverdata.equals("quit")){
				connection.close();	
				connection=null;
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		OneToOneS s = new OneToOneS();
		s.runServer();

	}
	
	class WinListener extends WindowAdapter{
		public void windowClosing(WindowEvent e) {
			System.exit(0);
		}
	}

}

class ServerThread extends Thread{
	BufferedWriter output;
	BufferedReader input;
<<<<<<< HEAD
	Socket socket;
	OneToOneS sv;
	TextArea display;
	
	ServerThread(Socket connection, TextArea ta){
		socket = connection;
=======
	Socket connection;
	OneToOneS sv;
	TextArea display;
	ServerThread(Socket socket, TextArea ta){
		connection = socket;
>>>>>>> 19d0b2ca2316b20174aabaf4db36ac7d4d8bdbd8
		display = ta;
	}

	public void run() {
		try {
<<<<<<< HEAD
			String clientIp = socket.getInetAddress().getHostAddress();
			display.append("\n Å¬¶óÀÌ¾ðÆ® " + clientIp + "°¡ ¿¬°áµÇ¾ú½À´Ï´Ù.\n");
			
			InputStream is = socket.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			input = new BufferedReader(isr); //¼­¹ö°¡ Àü¼ÛÇÑ ´ëÈ­¸» ¼ö½Å
			OutputStream os = socket.getOutputStream();
			OutputStreamWriter osw = new OutputStreamWriter(os); //Å¬¶óÀÌ¾ðÆ®¿¡ ´ëÈ­¸» Àü¼Û
=======
			InputStream is = connection.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			input = new BufferedReader(isr);
			OutputStream os = connection.getOutputStream();
			OutputStreamWriter osw = new OutputStreamWriter(os);
>>>>>>> 19d0b2ca2316b20174aabaf4db36ac7d4d8bdbd8
			output = new BufferedWriter(osw);
			
			while(true) {
				String clientdata = input.readLine();
				if(clientdata == null) {
<<<<<<< HEAD
					display.append("\n Å¬¶óÀÌ¾ðÆ®¿ÍÀÇ Á¢¼ÓÀÌ Áß´ÜµÇ¾ú½À´Ï´Ù.");
					output.flush();
					break;
				}else {
					display.append("\nÅ¬¶óÀÌ¾ðÆ® ¸Þ½ÃÁö :  " + clientdata);
					output.flush();
				}
			}
			socket.close();
			socket=null;
=======
					display.append("\n í´ë¼ì´ì–¸íŠ¸ì™€ì˜ ì ‘ì†ì´ ì¤‘ë‹¨ë˜ì—ˆìŠµë‹ˆë‹¤.");
					output.flush();
					break;
				}else {
					display.append("\ní´ë¼ì´ì–¸íŠ¸ ë©”ì‹œì§€ :  " + clientdata);
				}
			}
			connection.close();
>>>>>>> 19d0b2ca2316b20174aabaf4db36ac7d4d8bdbd8
		}catch(IOException e) {
			System.out.println(e);
		}

	}
<<<<<<< HEAD
}
=======
}
>>>>>>> 19d0b2ca2316b20174aabaf4db36ac7d4d8bdbd8
