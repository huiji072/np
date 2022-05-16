package test;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class OneToOneC extends Frame implements ActionListener{
	
	TextArea display;
	TextField text;
	Label lword;
	Socket client;
	BufferedWriter output;
	BufferedReader input;
	String clientdata = "";
	String serverdata = "";
	
	public OneToOneC() {
		super("클라이언트");
		display = new TextArea("", 0, 0, TextArea.SCROLLBARS_HORIZONTAL_ONLY);
		display.setEditable(false);
		add(display, BorderLayout.CENTER);
		
		Panel pword = new Panel(new BorderLayout());
		lword = new Label("대화말");
		text = new TextField(30); //전송할 데이터를 입력하는 필드
		text.addActionListener(this);
		pword.add(lword, BorderLayout.WEST);
		pword.add(text, BorderLayout.EAST);
		add(pword, BorderLayout.SOUTH);
		addWindowListener(new WinListener());
		setSize(300, 200);
		setVisible(true);
		
	}
	
	public void runClient() {
		try {
			client = new Socket(InetAddress.getLocalHost(), 5000); //5000번 포트와 연결
			input = new BufferedReader(new InputStreamReader(client.getInputStream()));
			output = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
			
			while(true) {
				String serverdata = input.readLine(); //서버에서 보낸 데이터를 serverdata에 저장
				if(serverdata.equals("quit")) {
					display.append(serverdata);
					output.flush();
					break;
				}else {
					display.append("\n서버 메시지 : " + serverdata);
					output.flush();
				}
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		OneToOneC c = new OneToOneC();
		c.runClient();
	}

	class WinListener extends WindowAdapter{
		public void windowClosing(WindowEvent we) {
			System.exit(0);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		clientdata = text.getText();
		try {
			display.append("\n클라이언트 : " + clientdata);
			output.write(clientdata+"\r\n");
			output.flush();
			text.setText("");
			if(clientdata.equals("quit")) {
				client.close();
			}
		}catch(IOException e2) {
			e2.printStackTrace();
		}
		
	}
	
}
