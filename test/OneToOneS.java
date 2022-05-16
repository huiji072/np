package test;

import java.awt.*;
import java.io.*;
import java.awt.event.*;
import java.net.*;

public class OneToOneS extends Frame implements ActionListener{
	
	TextArea display;
	TextField text;
	Label lword;
	Socket connection;
	BufferedWriter output;
	BufferedReader input;
	String clientdata = "";
	String serverdata = "";
	
	public OneToOneS() {
		super("서버");
		display = new TextArea("", 0, 0, TextArea.SCROLLBARS_VERTICAL_ONLY);
		display.setEditable(false);
		add(display, BorderLayout.CENTER);
		
		Panel pword = new Panel(new BorderLayout());
		lword = new Label("대화말");
		text = new TextField(30);
		text.addActionListener(this);
		pword.add(lword, BorderLayout.WEST);
		pword.add(text, BorderLayout.EAST);
		add(pword, BorderLayout.SOUTH);
		
		addWindowListener(new WinListener());
		setSize(300, 200);
		setVisible(true);
	}
	
	public void runServer() {
		ServerSocket server;
		try {
			server = new ServerSocket(5000, 100); //5000번 포트에 최대 100개 연결 가능, 클라이언트 접속을 기다림
			connection = server.accept();
			
			//서버가 전송한 대화말을 수신
			InputStream is = connection.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			input = new BufferedReader(isr);
			
			//클라이언트에 대화말을 전송
			OutputStream os = connection.getOutputStream();
			OutputStreamWriter osw = new OutputStreamWriter(os);
			output = new BufferedWriter(osw);
			
			while(true) {
				String clientdata = input.readLine();
				if(clientdata.equals("quit")) { //클라이언트로부터 quit 받으면 서버에 접속 중단됐다고 알려줌
					display.append("\n클라이언트와의 접속이 중단되었습니다.");
					output.flush();
					break;
				}else {
					display.append("\n클라이언트 메시지 : " + clientdata); //클라이언트가 보낸걸 서버에 출력
					output.flush();
				}
			}
			connection.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		OneToOneS s = new OneToOneS();
		s.runServer();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		serverdata = text.getText(); //대화말 입력창에 있는걸 serverdata에 저장
		
		try {
			display.append("\n서버 : " + serverdata);
			output.write(serverdata + "\r\n");
			output.flush();
			text.setText("");
			if(serverdata.equals("quit")) {
				connection.close();
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
	class WinListener extends WindowAdapter{
		public void windowClosing(WindowEvent we) {
			System.exit(0);
		}
	}

}
