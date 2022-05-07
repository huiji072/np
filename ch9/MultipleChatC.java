package ch9;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

//서버-클라이언트 구조에서 다수의 클라이어느가 대화하는 프로그램
public class MultipleChatC extends Frame implements ActionListener{
	
	TextArea display;
	TextField text, tuser;
	Label lword, luser;
	BufferedWriter output;
	BufferedReader input;
	Socket client;
	String clientdata = "";
	String serverdata = "";
	String userName = "";
	
	public MultipleChatC() {
		super("클라이언트");
		display = new TextArea("", 0, 0, TextArea.SCROLLBARS_VERTICAL_ONLY);
		display.setEditable(false);
		add(display, BorderLayout.CENTER);
		
		Panel pword = new Panel(new BorderLayout());
		lword = new Label("대화말");
		text = new TextField(26); //전송할 데이터를 입력하는 필드
		text.addActionListener(this); //입렵된 데이터를 송신하기 위한 이벤트 연결
		pword.add(lword, BorderLayout.WEST);
		pword.add(text, BorderLayout.EAST);
		add(pword, BorderLayout.SOUTH);
		
		Panel pword2 = new Panel(new BorderLayout());
		luser = new Label("사용자 이름");
		tuser = new TextField(23);
		tuser.addActionListener(this);
		pword2.add(luser, BorderLayout.WEST);
		pword2.add(tuser, BorderLayout.EAST);
		add(pword2, BorderLayout.NORTH);
		
		
		addWindowListener(new WinListener());
		setSize(300, 150);
		setVisible(true);
	}
	
	public void runClient() {
		try {
			//내 PC의 호스트에서 5000번 포트 사용하는 서버의 소켓과 연결
			client = new Socket(InetAddress.getLocalHost(), 5000); 
			input = new BufferedReader(new InputStreamReader(client.getInputStream()));
			output = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
			
			while(true) {
				String serverdata = input.readLine(); //서버에서 보낸 채팅 받기
				display.append("\r\n" + serverdata); //서버에서 받은 채팅 출력
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		try {
			client.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void actionPerformed(ActionEvent ae) {
		clientdata = text.getText();
		userName = tuser.getText(); //사용자 이름을 가져옴

		try {
			//사용자명 미입력시 대홧말 전송 X
			if(userName.equals("")) {
				display.append("\r\n 사용자 이름 입력 후 사용하세요!");
				return;
			}else {
				display.append("\r\n나의 대화말 : " + clientdata); //나의 대화말 출력
				output.write(userName + ":" + clientdata + "\r\n"); //서버로 대화말 전송
				output.flush();
				text.setText("");
			}

		}catch(IOException e) {
			e.printStackTrace();
		}
		
		
	}
	

	public static void main(String[] args) {
		MultipleChatC c = new MultipleChatC();
		c.runClient();
	}
	
	class WinListener extends WindowAdapter{
		public void windowClosing(WindowEvent we) {
			System.exit(0);
		}
	}
}
