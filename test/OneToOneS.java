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
		super("����");
		display = new TextArea("", 0, 0, TextArea.SCROLLBARS_VERTICAL_ONLY);
		display.setEditable(false);
		add(display, BorderLayout.CENTER);
		
		Panel pword = new Panel(new BorderLayout());
		lword = new Label("��ȭ��");
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
			server = new ServerSocket(5000, 100); //5000�� ��Ʈ�� �ִ� 100�� ���� ����, Ŭ���̾�Ʈ ������ ��ٸ�
			connection = server.accept();
			
			//������ ������ ��ȭ���� ����
			InputStream is = connection.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			input = new BufferedReader(isr);
			
			//Ŭ���̾�Ʈ�� ��ȭ���� ����
			OutputStream os = connection.getOutputStream();
			OutputStreamWriter osw = new OutputStreamWriter(os);
			output = new BufferedWriter(osw);
			
			while(true) {
				String clientdata = input.readLine();
				if(clientdata.equals("quit")) { //Ŭ���̾�Ʈ�κ��� quit ������ ������ ���� �ߴܵƴٰ� �˷���
					display.append("\nŬ���̾�Ʈ���� ������ �ߴܵǾ����ϴ�.");
					output.flush();
					break;
				}else {
					display.append("\nŬ���̾�Ʈ �޽��� : " + clientdata); //Ŭ���̾�Ʈ�� ������ ������ ���
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
		serverdata = text.getText(); //��ȭ�� �Է�â�� �ִ°� serverdata�� ����
		
		try {
			display.append("\n���� : " + serverdata);
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
