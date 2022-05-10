package ch9;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class ChatWhisperC extends Frame implements ActionListener, KeyListener{
	
	TextArea display;
	TextField wtext, ltext;
	Label mlbl, wlbl, loglbl;
	BufferedWriter output;
	BufferedReader input;
	Socket client;
	StringBuffer clientdata;
	String serverdata;
	String ID;
	Button logout;
	
	private static final String SEPARATOR = "|";
	private static final int REQ_LOGON = 1001;
	private static final int REQ_LOGOUT = 1002;
	private static final int REQ_SENDWORDS = 1021;
	private static final int REQ_WISPERSEND = 1022;
	
	public ChatWhisperC() {
		super("클라이언트");
		
		mlbl = new Label("채팅 상태를 보여줍니다.");
		add(mlbl, BorderLayout.NORTH);
		display = new TextArea("", 0, 0, TextArea.SCROLLBARS_HORIZONTAL_ONLY);
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
		setSize(300, 250);
		setVisible(true);
	}
	
	public void runClient() {
		try {
			//내 PC의 호스트에서 5000번 포트 사용하는 서버의 소켓과 연결
			client = new Socket(InetAddress.getLocalHost(), 5000);
			//연결된 서버 화면에 출력
			mlbl.setText("연결된 서버이름 : " + client.getInetAddress().getHostName());
			input = new BufferedReader(new InputStreamReader(client.getInputStream()));
			output = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
			clientdata = new StringBuffer(2048);
			mlbl.setText("접속 완료 사용할 아이디를 입력하세요");
			
			while(true) {
				//서버에서 보낸 데이터 받기
				serverdata = input.readLine();
				//서버에서 보낸 데이터 화면에 출력
				display.append(serverdata + "\r\n");
				//다시 서버에 보내기
				output.flush();
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void actionPerformed(ActionEvent ae) {
		if(ID == null) {
			ID = ltext.getText();
			mlbl.setText(ID + "(으)로 로그인 하였습니다.");
			ltext.setVisible(false); //로그온 입력창은 사라지고
			loglbl.setVisible(false);
			logout.setVisible(true); //로그아웃 버튼이 보이게 한다.
			try {
				clientdata.setLength(0);
				clientdata.append(REQ_LOGON);
				clientdata.append(SEPARATOR);
				clientdata.append(ID);
				output.write(clientdata.toString() + "\r\n");
				output.flush();
				
			}catch(Exception e) {
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
	
	public static void main(String[] args) {
		ChatWhisperC c = new ChatWhisperC();
		c.runClient();
	}
	
	class WinListener extends WindowAdapter{
		public void windowClosing(WindowEvent e) {
			System.exit(0);
		}
	}

	
	public void keyPressed(KeyEvent ke) {
		if(ke.getKeyChar() == KeyEvent.VK_ENTER) {
			String message = wtext.getText();
			StringTokenizer st = new StringTokenizer(message, " ");
			
			if(ID == null) {
				mlbl.setText("다시 로그안 하세요!!!");
				wtext.setText("");
			}else {
				try {
					if(st.nextToken().equals("/w")) {
						message = message.substring(3); // "/w"를 삭제한다.
						String WID = st.nextToken();
						String Wmessage = st.nextToken();
						while(st.hasMoreTokens()) { //공백문자 다음에 오는 대화말 추가
							Wmessage = Wmessage + " " + st.nextToken();
						}
						clientdata.setLength(0);
						clientdata.append(REQ_SENDWORDS);
						clientdata.append(SEPARATOR);
						clientdata.append(ID);
						clientdata.append(SEPARATOR);
						clientdata.append(WID);
						clientdata.append(SEPARATOR);
						clientdata.append(Wmessage);
						output.write(clientdata.toString() + "\r\n");
						output.flush();
						wtext.setText("");
					}else {
						clientdata.setLength(0);
						clientdata.append(REQ_SENDWORDS);
						clientdata.append(SEPARATOR);
						clientdata.append(ID);
						clientdata.append(SEPARATOR);
						clientdata.append(message);
						output.write(clientdata.toString() + "\r\n");
						output.flush();
						wtext.setText("");
					}
				}catch(IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

		public void keyReleased(KeyEvent ke) {}
		public void keyTyped(KeyEvent ke) {}
}
