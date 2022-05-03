//package ch9;
//
//import java.awt.*;
//import java.awt.event.*;
//import java.io.*;
//import java.net.*;
//
//public class OneToOneC extends Frame implements ActionListener{
//	
//	static TextArea display;
//	static TextField text;
//	Label lword;
//	BufferedWriter output;
//	BufferedReader input;
//	Socket client;
//	String clientdata = "";
//	Button reconn;
//	
//	public OneToOneC() {
//		super("클라이언트");
//		display = new TextArea("", 0, 0, TextArea.SCROLLBARS_VERTICAL_ONLY);
//		display.setEditable(false);
//		add(display, BorderLayout.CENTER);
//		
//		Panel pword = new Panel(new BorderLayout());
//		lword = new Label("대화말");
//		text = new TextField(30);
//		text.addActionListener(this);
//		reconn = new Button("재연결");
//		
//		pword.add(lword, BorderLayout.WEST);
//		pword.add(text, BorderLayout.CENTER);
//		pword.add(reconn, BorderLayout.EAST);
//		add(pword, BorderLayout.SOUTH);
//		
//		addWindowListener(new WinListener());
//		setSize(300, 200);
//		setVisible(true);
//		
//	}
//	
//	public void runClient() {
//		ClientThread clientThread = null;
//		try {
//			client = new Socket(InetAddress.getLocalHost(), 5000);
//			clientThread = new ClientThread(this, client, display);
//			clientThread.start();
//			
//		}catch(IOException e) {
//			e.printStackTrace();
//		}
//	}
//	
//	public void actionPerformed(ActionEvent ae) {
//		clientdata = text.getText();
//		try {
//			display.append("\n클라이언트 : " + clientdata);
//			output.write(clientdata + "\r\n");
//			output.flush();
//			
//			text.setText("");
//			if(clientdata.equals("quit")) {
//				client.close();
//			}
//		}catch(IOException e) {
//			e.printStackTrace();
//		}
//	}
//
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		OneToOneC c = new OneToOneC();
//		c.runClient();
//
//	}
//	
//	class WinListener extends WindowAdapter
//	{
//		public void windowClosing(WindowEvent e) {
//			System.exit(0);
//		}
//	}
//
//}
//
//class ClientThread extends Thread{
//	BufferedWriter output;
//	BufferedReader input;
//	Socket client;
//	TextArea display;
//	
//	public ClientThread(OneToOneC c, Socket s, TextArea ta) {
//		client = s;
//		display = ta;
//		
//		try {
//			client = new Socket(InetAddress.getLocalHost(), 5000);
//			input = new BufferedReader(new InputStreamReader(client.getInputStream()));
//			output = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
//		}catch(IOException e) {
//			System.out.println(e);
//		}
//	}
//	
//	public void run() {
//		try {
//			while(true) {
//				String serverdata = input.readLine();
//				if(serverdata.equals("quit")) {
//					display.append("\n서버와의 접속이 중단되었습니다.");
//					output.flush();
//					break;
//				}else {
//					display.append("\n서버 메시지 : " + serverdata);
//					output.flush();
//				}
//			}
//			client.close();
//		}catch(IOException e) {
//			e.printStackTrace();
//		}
//	}
//}