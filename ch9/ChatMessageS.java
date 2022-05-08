package ch9;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import java.io.*;
import java.net.*;

public class ChatMessageS extends Frame{
	TextArea display;
	Label info;
	List <ServerThread> list;
	
	public ServerThread SThread;
	
	public ChatMessageS() {
		super("����");
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
		ServerThread SThread;
		
		try {
			list = new ArrayList<ServerThread>();
			server = new ServerSocket(5000, 100); //5000�� ��Ʈ ���, �ִ� 100�� ���� ���� ����
			
			try {
				while(true) {
					sock = server.accept(); //Ŭ���̾�Ʈ�� ���ӿ�û�� ���� Ŭ���̾�Ʈ ���ϰ� �����
					SThread = new ServerThread(this, sock, display, info);
					SThread.start(); //��Ƽ ������� ����
					//���� ȣ��Ʈ�� �̸� ���
					info.setText(sock.getInetAddress().getHostName());
				}
			}catch(IOException e) {
				server.close();
				e.printStackTrace();
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		ChatMessageS s = new ChatMessageS();
		s.runServer();
	}
	
	class WinListener extends WindowAdapter{
		public void windowClosing(WindowEvent e) {
			System.exit(0);
		}
	}
}

class ServerThread extends Thread{
	Socket sock;
	BufferedWriter output;
	BufferedReader input;
	TextArea display;
	Label info;
	TextField text;
	String clientdata;
	String serverdata = "";
	ChatMessageS cs;
	
	private static final String SEPARATOR = "|";
	private static final int REQ_LOGON = 1001;
	private static final int REQ_SENDWORDS = 1021;
	
	public ServerThread(ChatMessageS c, Socket s, TextArea ta, Label l){
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
		cs.list.add(this);
		
		try {
			while((clientdata = input.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(clientdata, SEPARATOR); // '|'�� �������� �ܾ� ����
				int command = Integer.parseInt(st.nextToken());
				int cnt = cs.list.size();
				
				switch(command) {
					case REQ_LOGON : {
						String ID = st.nextToken(); //"1001|���̵�"�� ������ ���
						display.append("Ŭ���̾�Ʈ�� " + ID + " (��)�� �α��� �Ͽ����ϴ�.\r\n");
						break;
					}
					case REQ_SENDWORDS : {//"1021|���̵�|��ȭ��"�� ����
						String ID = st.nextToken();
						String message = st.nextToken();
						display.append(ID + " : " + message + "\r\n");
						
						for(int i = 0 ; i < cnt ; i++) { //��� Ŭ���̾�Ʈ�� ����
							ServerThread SThread = (ServerThread)cs.list.get(i);
							SThread.output.write(ID + " : " + message + "\r\n");
							SThread.output.flush();
						}
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
