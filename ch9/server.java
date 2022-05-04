package ch9;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.List;

public class server extends Frame implements ActionListener{
	
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
	
	public server() {
		super("서버");
		display = new TextArea("", 0, 0, TextArea.SCROLLBARS_VERTICAL_ONLY);
		display.setEditable(false);
		add(display, BorderLayout.CENTER);
		
		
		Panel pword = new Panel(new BorderLayout());
		lword = new Label("대화말");
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
					connection = server.accept();//접속요청이 올 때까지 기다린다.
					SThread = new ServerThread(connection, display);
					SThread.start();
					display.setText(connection.getInetAddress().getHostName() + " 서버는 클라이언트와 연결됨");
					OutputStream os = connection.getOutputStream();
					OutputStreamWriter osw = new OutputStreamWriter(os);
					output = new BufferedWriter(osw);
				}
			}catch(IOException e) {
				server.close();
				System.out.println(e);
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	public void actionPerformed(ActionEvent ae) {

		
		serverdata = text.getText();
		try {
			display.append("\n서버 : " + serverdata);
			output.write(serverdata + "\r\n");
			output.flush();
			text.setText("");
			
			if(serverdata.equals("quit")){
				connection.close();	
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		server s = new server();
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
	Socket connection;
	server sv;
	TextArea display;
	ServerThread(Socket socket, TextArea ta){
		this.connection = socket;
		display = ta;
//		sv = s;
	}

	public void run() {
		try {
			InputStream is = connection.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			input = new BufferedReader(isr);
			OutputStream os = connection.getOutputStream();
			OutputStreamWriter osw = new OutputStreamWriter(os);
			output = new BufferedWriter(osw);
			
			while(true) {
				String clientdata = input.readLine();
				if(clientdata.equals("quit")) {
					display.append("\n 클라이언트와의 접속이 중단되었습니다.");
					output.flush();
					break;
				}else {
					display.append("\n클라이언트 메시지 :  " + clientdata);
//					int cnt = sv.list.size();
//					for(int i=0;i<cnt;i++) {
//						ServerThread SThread = (ServerThread)sv.list.get(i);
//						SThread.output.write(clientdata+"\r\n");
//						SThread.output.flush();
//					}
					output.flush();
				}
			}
			connection.close();
		}catch(IOException e) {
			System.out.println(e);
		}
		sv.list.remove(this);
//		try {
//			connection.close();
//		}catch(IOException e) {
//			System.out.println(e);
//		}
	}
}
