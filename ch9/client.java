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
		super("Ŭ���̾�Ʈ");
		display = new TextArea("", 0, 0, TextArea.SCROLLBARS_VERTICAL_ONLY);
		display.setEditable(false);
		add(display, BorderLayout.CENTER);
		
		Panel pword = new Panel(new BorderLayout());
		lword = new Label("��ȭ��");
		text = new TextField(30);
		reconn = new Button("������");
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
			try {
				
				while(true) {
					ClientThread CThread = null;
					CThread = new ClientThread(client, display);
					CThread.start();
					output = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
				}
			}catch(IOException e) {
				e.printStackTrace();
			}
		}catch(IOException e) {
			e.printStackTrace();
		}

	}
	
	public void actionPerformed(ActionEvent ae) {
		//Ŭ���̾�Ʈ �� ���, ������ ������
		clientdata = text.getText();
		try {
			display.append("\nŬ���̾�Ʈ : " + clientdata);
			output.write(clientdata + "\r\n");
			output.flush();
			text.setText("");
			
			if(clientdata.equals("quit")) {
				client.close();
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		if(ae.getSource() == reconn) {
			if(client.isClosed()) {
				try {
					client = new Socket(InetAddress.getLocalHost(), 5000);
					display.setText("restart"); //test
					ClientThread CThread = null;
					CThread = new ClientThread(client, display);
					CThread.start();
					

				}catch(IOException e) {}
			}else {
				try {
					client.close();
					display.setText("restart2"); //test
					this.runClient(); //������
				}catch(IOException e) {}

			}
			
		}

//		������ ��ư�� ������ ��

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		client c = new client();
		c.runClient();
	}
	
	class WinListener extends WindowAdapter{
		public void windowClosing(WindowEvent e) {
			System.exit(0);
		}
	}

}

class ClientThread extends Thread{
	BufferedWriter output;
	BufferedReader input;
	Socket client;
	TextArea display;
	
	ClientThread(Socket socket, TextArea ta){
		this.client = socket;
		display = ta;
	}
	
	public void run() {
		try {
			input = new BufferedReader(new InputStreamReader(client.getInputStream()));
			output = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
			
			while(true) {
				String serverdata = input.readLine();
				if(serverdata.equals("quit")) {
					display.append("\n�������� ������ �ߴܵǾ����ϴ�.");
					output.flush();
					break;
				}else {
					display.append("\n���� �޽��� : " + serverdata);
					output.flush();
				}
			}
		}catch(IOException e) {
			e.printStackTrace();
//			System.out.println("@@@@@@@@@@@@@@@@@@@");
		}

		

	}
}