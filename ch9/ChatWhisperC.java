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
		super("Ŭ���̾�Ʈ");
		
		mlbl = new Label("ä�� ���¸� �����ݴϴ�.");
		add(mlbl, BorderLayout.NORTH);
		display = new TextArea("", 0, 0, TextArea.SCROLLBARS_HORIZONTAL_ONLY);
		display.setEditable(false);
		add(display, BorderLayout.CENTER);
		
		Panel ptotal = new Panel(new BorderLayout());
		
		Panel pword = new Panel(new BorderLayout());
		wlbl = new Label("��ȭ��");
		wtext = new TextField(26); //������ �����͸� �Է��ϴ� �ʵ�
		wtext.addKeyListener(this); //�Էµ� �����͸� �۽��ϱ� ���� �̺�Ʈ ����
		pword.add(wlbl, BorderLayout.WEST);
		pword.add(wtext, BorderLayout.EAST);
		ptotal.add(pword, BorderLayout.CENTER);
		
		Panel plabel = new Panel(new BorderLayout());
		loglbl = new Label("�α׿�");
		ltext = new TextField(26); //������ �����͸� �Է��ϴ� �ʵ�
		ltext.addActionListener(this); //�Էµ� �����͸� �۽��ϱ� ���� �̺�Ʈ ����
		logout = new Button("�α׾ƿ�"); //�α׾ƿ� ��ư
		logout.addActionListener(this);
		logout.setVisible(false); //�α׾ƿ� ��ư�� �ʱ⿡ �Ⱥ��̰� �Ѵ�.
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
			//�� PC�� ȣ��Ʈ���� 5000�� ��Ʈ ����ϴ� ������ ���ϰ� ����
			client = new Socket(InetAddress.getLocalHost(), 5000);
			//����� ���� ȭ�鿡 ���
			mlbl.setText("����� �����̸� : " + client.getInetAddress().getHostName());
			input = new BufferedReader(new InputStreamReader(client.getInputStream()));
			output = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
			clientdata = new StringBuffer(2048);
			mlbl.setText("���� �Ϸ� ����� ���̵� �Է��ϼ���");
			
			while(true) {
				//�������� ���� ������ �ޱ�
				serverdata = input.readLine();
				//�������� ���� ������ ȭ�鿡 ���
				display.append(serverdata + "\r\n");
				//�ٽ� ������ ������
				output.flush();
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void actionPerformed(ActionEvent ae) {
		if(ID == null) {
			ID = ltext.getText();
			mlbl.setText(ID + "(��)�� �α��� �Ͽ����ϴ�.");
			ltext.setVisible(false); //�α׿� �Է�â�� �������
			loglbl.setVisible(false);
			logout.setVisible(true); //�α׾ƿ� ��ư�� ���̰� �Ѵ�.
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
		
		if(ae.getSource().equals(logout)) { //�α׾ƿ� ��ư�� ������
			mlbl.setText("");		
			ltext.setVisible(true); 
			loglbl.setVisible(true); //�α��� �Է�â ����
			logout.setVisible(false); //�α׾ƿ� ��ư �Ⱥ���
			ID = ltext.getText();
			mlbl.setText(ID + "(��)�� �α׾ƿ� �Ͽ����ϴ�.");
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
				mlbl.setText("�ٽ� �α׾� �ϼ���!!!");
				wtext.setText("");
			}else {
				try {
					if(st.nextToken().equals("/w")) {
						message = message.substring(3); // "/w"�� �����Ѵ�.
						String WID = st.nextToken();
						String Wmessage = st.nextToken();
						while(st.hasMoreTokens()) { //���鹮�� ������ ���� ��ȭ�� �߰�
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
