package ch9;

import java.awt.*;
import java.util.*;
import java.io.*;
import java.awt.event.*;
import java.net.*;
import java.util.List;

public class ChatWhisperS extends Frame{
	
	TextArea display;
	Label info;
	List <ServerThread2> list;
	Hashtable hash;
	public ServerThread2 SThread;
	
	public ChatWhisperS() {
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
		ServerThread2 SThread;
		
		try {
			server = new ServerSocket(5000, 100);//5000번 포트에 동시에 100개 연결 가능
			hash = new Hashtable(); //name/value 쌍으로 데이터 저장
			list = new ArrayList<ServerThread2>();
			try {
				while(true) {
					sock = server.accept(); //클라이언트에서 접속이 올 때까지 대기
					SThread = new ServerThread2(this, sock, display, info);
					SThread.start();
					//서버 호스트의 이름 출력
					info.setText(sock.getInetAddress().getHostName() + " 서버는 클라이언트와 연결됨");
				}
			}catch(IOException io) {
				server.close();
				io.printStackTrace();
			}
		}catch(IOException io) {
			io.printStackTrace();
		}
	}
	

	public static void main(String[] args) {
		ChatWhisperS s = new ChatWhisperS();
		s.runServer();
	}
	
	class WinListener extends WindowAdapter{
		public void windowClosing(WindowEvent e) {
			System.exit(0);
		}
	}
}

class ServerThread2 extends Thread{
	Socket sock;
	BufferedWriter output;
	BufferedReader input;
	TextArea display;
	Label info;
	TextField text;
	String clientdata;
	String serverdata = "";
	ChatWhisperS cs;
	StringBuffer data;
	String[] id = new String[100];
	private static final String SEPARATOR = "|";
	private static final int REQ_LOGON = 1001;
	private static final int REQ_LOGOUT = 1002;
	private static final int REQ_LOGON_OVERLAP = 1003;
	private static final int REQ_SENDWORDS = 1021;
	private static final int REQ_WISPERSEND = 1022;
	public ServerThread2(ChatWhisperS c, Socket s, TextArea ta, Label l) {
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
		try {
			cs.list.add(this);
			while((clientdata = input.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(clientdata, SEPARATOR);
				int command = Integer.parseInt(st.nextToken());
				int Lcnt = cs.list.size();
				
				switch(command) {
				//로그인
				case REQ_LOGON:{
					String ID = st.nextToken();
					
					if(cs.hash.containsKey(ID) == true) { //이미 있는 아이디일 때
//						ServerThread2 SThread = (ServerThread2)cs.hash.get(ID);
						display.append(ID + "는 이미 있는 아이디 입니다.\r\n"); //서버 화면에 출력
						output.write("중복된 ID입니다." + "\r\n"); //중복 아이디. 클라리언트로 전송
						output.flush();
						break;
					}else {
						display.append("클라이언트가 " + ID + "(으)로 로그인 하였습니다.\r\n");
						cs.list.add(this);
						cs.hash.put(ID, this);
						output.write(ID + "가 로그인 하였습니다." + "\r\n"); //로그인, 클라이언트로 전송
						output.flush();
					}	
					break;
				}

				//클라이언트에 전송
				case REQ_SENDWORDS : {
					String ID = st.nextToken();
					String message = st.nextToken();
					display.append(ID + " : " + message + "\r\n");
					for(int i = 0 ; i < Lcnt ; i++) {
						ServerThread2 SThread = (ServerThread2)cs.list.get(i);
						SThread.output.write(ID + " : " + message + "\r\n");
						SThread.output.flush();
					}
					break;
				}
					//클라이언트에 귓속말 전송
					case REQ_WISPERSEND : {
						String ID = st.nextToken(); //귓속말 발신자
						String WID = st.nextToken(); //귓속말 수신자
						String message = st.nextToken(); //귓속말 내용
						display.append(ID + " -> " + WID + " : " + message + "\r\n");
						//해쉬테이블에서 귀속말 메시지를 전송한 클라이언트의 스레드를 구함
						ServerThread2 SThread = (ServerThread2)cs.hash.get(ID);	
						//귓속말 메시지를 전송한 클라이언트에 전송함
						SThread.output.write(ID + " -> " + WID + " : " + message + "\r\n");
						SThread.output.flush();
						//해쉬테이블에서 귓속말 메시지를 수신할 클라이언트이 스레드를 구함
						SThread = (ServerThread2)cs.hash.get(WID);
						//귓속말 메시지를 수신할 클라이어느에 전송함
						SThread.output.write(ID + " : " + message + "\r\n");
						SThread.output.flush();
						break;
					}
					case REQ_LOGOUT : { //로그아웃 기능 추가
						cs.list.remove(this); //로그아웃 한 클라이언트 제거
						String ID = st.nextToken();
						display.append("클라이언트 " + ID + "(이)가 로그아웃 하였습니다.\r\n");
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