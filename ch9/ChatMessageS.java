package ch9;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import java.io.*;
import java.net.*;

public class ChatMessageS extends Frame{
	TextArea display;
	Label info;
	List <ServerThread> list;
	
	public ServerThread SThread;
	
	public ChatMessageS() {
		super("서버");
		info = new Label();
		add(info, BorderLayout.CENTER);
		display = new TextArea("", 0, 0, TextArea.SCROLLBARS_VERTICAL_ONLY);
		display.setEditable(false);
		add(display, BorderLayout.SOUTH);
		addWindowListener(new WinListener());
		setSize(300, 250);
		setVisible(true);
	}
	
	public void runServer() {
		ServerSocket server;
		Socket sock;
		ServerThread SThread;
		
		try {
			list = new ArrayList<ServerThread>();
			server = new ServerSocket(5000, 100); //5000번 포트 사용, 최대 100개 동시 연결 가능
			
			try {
				while(true) {
					sock = server.accept(); //클라이언트로 접속요청이 오면 클라이언트 소켓과 연결됨
					SThread = new ServerThread(this, sock, display, info);
					SThread.start(); //멀티 스레드로 실행
					//서버 호스트의 이름 출력
					info.setText(sock.getInetAddress().getHostName());
				}
			}catch(IOException e) {
				server.close();
				e.printStackTrace();
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		ChatMessageS s = new ChatMessageS();
		s.runServer();
	}
	
	class WinListener extends WindowAdapter{
		public void windowClosing(WindowEvent e) {
			System.exit(0);
		}
	}
}

class ServerThread extends Thread{
	Socket sock;
	BufferedWriter output;
	BufferedReader input;
	TextArea display;
	Label info;
	TextField text;
	String clientdata;
	String serverdata = "";
	ChatMessageS cs;
	
	private static final String SEPARATOR = "|";
	private static final int REQ_LOGON = 1001;
	private static final int REQ_SENDWORDS = 1021;
	
	public ServerThread(ChatMessageS c, Socket s, TextArea ta, Label l){
		sock = s;
		display = ta;
		info = l;
		cs = c;
		
		try {
			input = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			output = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		cs.list.add(this);
		
		try {
			while((clientdata = input.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(clientdata, SEPARATOR); // '|'을 구분으로 단어 나눔
				int command = Integer.parseInt(st.nextToken());
				int cnt = cs.list.size();
				
				switch(command) {
					case REQ_LOGON : {
						String ID = st.nextToken(); //"1001|아이디"를 수신한 경우
						display.append("클라이언트가 " + ID + " (으)로 로그인 하였습니다.\r\n");
						break;
					}
					case REQ_SENDWORDS : {//"1021|아이디|대화말"를 수신
						String ID = st.nextToken();
						String message = st.nextToken();
						display.append(ID + " : " + message + "\r\n");
						
						for(int i = 0 ; i < cnt ; i++) { //모든 클라이언트에 전송
							ServerThread SThread = (ServerThread)cs.list.get(i);
							SThread.output.write(ID + " : " + message + "\r\n");
							SThread.output.flush();
						}
						break;
					}
				}
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
		cs.list.remove(this);
		try {
			sock.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
}
