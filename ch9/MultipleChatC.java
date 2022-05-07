package ch9;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

//����-Ŭ���̾�Ʈ �������� �ټ��� Ŭ���̾���� ��ȭ�ϴ� ���α׷�
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
		super("Ŭ���̾�Ʈ");
		display = new TextArea("", 0, 0, TextArea.SCROLLBARS_VERTICAL_ONLY);
		display.setEditable(false);
		add(display, BorderLayout.CENTER);
		
		Panel pword = new Panel(new BorderLayout());
		lword = new Label("��ȭ��");
		text = new TextField(26); //������ �����͸� �Է��ϴ� �ʵ�
		text.addActionListener(this); //�ԷƵ� �����͸� �۽��ϱ� ���� �̺�Ʈ ����
		pword.add(lword, BorderLayout.WEST);
		pword.add(text, BorderLayout.EAST);
		add(pword, BorderLayout.SOUTH);
		
		Panel pword2 = new Panel(new BorderLayout());
		luser = new Label("����� �̸�");
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
			//�� PC�� ȣ��Ʈ���� 5000�� ��Ʈ ����ϴ� ������ ���ϰ� ����
			client = new Socket(InetAddress.getLocalHost(), 5000); 
			input = new BufferedReader(new InputStreamReader(client.getInputStream()));
			output = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
			
			while(true) {
				String serverdata = input.readLine(); //�������� ���� ä�� �ޱ�
				display.append("\r\n" + serverdata); //�������� ���� ä�� ���
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
		userName = tuser.getText(); //����� �̸��� ������

		try {
			//����ڸ� ���Է½� ��ȱ�� ���� X
			if(userName.equals("")) {
				display.append("\r\n ����� �̸� �Է� �� ����ϼ���!");
				return;
			}else {
				display.append("\r\n���� ��ȭ�� : " + clientdata); //���� ��ȭ�� ���
				output.write(userName + ":" + clientdata + "\r\n"); //������ ��ȭ�� ����
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
