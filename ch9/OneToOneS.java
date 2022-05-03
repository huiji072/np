//package ch9;
//
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.event.WindowAdapter;
//import java.awt.event.WindowEvent;
//import java.io.*;
//import java.net.*;
//
//public class OneToOneS extends Frame implements ActionListener{
//	
//	static TextArea display;
//	TextField text;
//	Label lword;
//	Socket connection;
//	BufferedWriter output;
//	BufferedReader input;
//	String clienddata="";
//	String serverdata = "";
//	
//	public OneToOneS() {
//		super("서버");
//		display = new TextArea("", 0, 0, TextArea.SCROLLBARS_VERTICAL_ONLY);
//		display.setEditable(false);
//		add(display, BorderLayout.CENTER);
//		
//		
//		Panel pword = new Panel(new BorderLayout());
//		lword = new Label("대화말");
//		text = new TextField(30);
//		text.addActionListener(this);
//		pword.add(lword, BorderLayout.WEST);
//		pword.add(text, BorderLayout.EAST);
//		add(pword, BorderLayout.SOUTH);
//		
//		addWindowListener(new WinListener());
//		setSize(300, 200);
//		setVisible(true);
//	}
//	
//	public void runServer() {
//		ServerSocket server;
//		ServerThread serverThread = null;
//		try {
//			server = new ServerSocket(5000, 100);
//			connection = server.accept();
//			serverThread = new ServerThread(this, connection, display);
//			serverThread.start();
//			System.out.println(connection.getInetAddress().getLocalHost() + "서버는 클라이언트와 연결됨");
//		}catch(IOException e) {
//			e.printStackTrace();
//		}
//	}
//	
//	public void actionPerformed(ActionEvent ae) {
//		serverdata = text.getText();
//		try {
//			display.append("\n서버 : " + serverdata);
//			output.write(serverdata + "\r\n");
//			output.flush();
//			text.setText("");
//			
//			if(serverdata.equals("quit")){
//				connection.close();	
//			}
//		}catch(IOException e) {
//			e.printStackTrace();
//		}
//	}
//
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		OneToOneS s = new OneToOneS();
//		s.runServer();
//
//	}
//	
//	class WinListener extends WindowAdapter{
//		public void windowClosing(WindowEvent e) {
//			System.exit(0);
//		}
//	}
//
//}
//
//class ServerThread extends Thread{
//	Socket connection;
//	BufferedWriter output;
//	BufferedReader input;
//	ServerSocket server;
//	TextArea display;
//	
//	public ServerThread(OneToOneS s1, Socket s, TextArea ta) {
//		connection = s;
//		display = ta;
//		try {
//			InputStream is = connection.getInputStream();
//			InputStreamReader isr = new InputStreamReader(is);
//			input = new BufferedReader(isr);
//			OutputStream os = connection.getOutputStream();
//			OutputStreamWriter osw = new OutputStreamWriter(os);
//			output = new BufferedWriter(osw);
//		}catch(IOException e) {
//			System.out.println(e);
//		}
//	}
//	
//	public void run() {
//		try {
//			while(true) {
//				String clientdata = input.readLine();
//				if(clientdata.equals("quit")) {
//					display.append("\n 클라이언트와의 접속이 중단되었습니다.");
//					output.flush();
//					break;
//				}else {
//					display.append("\n클라이언트 메시지 :  " + clientdata);
//					output.flush();
//				}
//			}
//			connection.close();
//		}catch(IOException e) {
//			e.printStackTrace();
//		}
//	}
//
//}