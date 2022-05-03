package ch9;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class client extends Frame implements ActionListener{
	
	TextArea display;
	TextField text;
	Label lword;
	BufferedWriter output;
	BufferedReader input;
	Socket client;
	String clientdata = "";
	Button reconn;
	
	public client() {
		super("클라이언트");
		display = new TextArea("", 0, 0, TextArea.SCROLLBARS_VERTICAL_ONLY);
		display.setEditable(false);
		add(display, BorderLayout.CENTER);
		
		Panel pword = new Panel(new BorderLayout());
		lword = new Label("대화말");
		text = new TextField(30);
		reconn = new Button("재접속");
		text.addActionListener(this);
		reconn.addActionListener(this);
		pword.add(lword, BorderLayout.WEST);
		pword.add(text, BorderLayout.CENTER);
		pword.add(reconn, BorderLayout.EAST);
		add(pword, BorderLayout.SOUTH);
		
		addWindowListener(new WinListener());
		setSize(300, 200);
		setVisible(true);
		
	}
	
	public void runClient() {
		try {
			client = new Socket(InetAddress.getLocalHost(), 5000);
			input = new BufferedReader(new InputStreamReader(client.getInputStream()));
			output = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
			
			while(true) {
				String serverdata = input.readLine();
				if(serverdata.equals("quit")) {
					display.append("\n서버와의 접속이 중단되었습니다.");
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
		try {
			client.close();
		}catch(IOException e) {
			System.out.println(e);
		}
	}
	
	public void actionPerformed(ActionEvent ae) {
		
		clientdata = text.getText();
		try {
			display.append("\n클라이언트 : " + clientdata);
			output.write(clientdata + "\r\n");
			output.flush();
			
			text.setText("");
			if(clientdata.equals("quit")) {
				client.close();
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		//재접속 버튼을 눌렀을 때
		if(ae.getSource() == reconn) {
			try {
				client = new Socket(InetAddress.getLocalHost(), 5000);
				input = new BufferedReader(new InputStreamReader(client.getInputStream()));
				output = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
				
				while(true) {
					String serverdata = input.readLine();
					if(serverdata.equals("quit")) {
						display.append("\n서버와의 접속이 중단되었습니다.");
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
			try {
				client.close();
			}catch(IOException e) {
				System.out.println(e);
			}
		}
			

		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		client c = new client();
		c.runClient();

	}
	
	class WinListener extends WindowAdapter
	{
		public void windowClosing(WindowEvent e) {
			System.exit(0);
		}
	}

}