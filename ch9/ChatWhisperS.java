package ch9;

import java.awt.*;
import java.util.*;
import java.io.*;
import java.awt.event.*;
import java.net.*;
import java.util.List;

public class ChatWhisperS extends Frame{
	
	TextArea display;
	Label info;
	List <ServerThread2> list;
	Hashtable hash;
	public ServerThread2 SThread;
	
	public ChatWhisperS() {
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
		ServerThread2 SThread;
		
		try {
			server = new ServerSocket(5000, 100);//5000�� ��Ʈ�� ���ÿ� 100�� ���� ����
			hash = new Hashtable(); //name/value ������ ������ ����
			list = new ArrayList<ServerThread2>();
			try {
				while(true) {
					sock = server.accept(); //Ŭ���̾�Ʈ���� ������ �� ������ ���
					SThread = new ServerThread2(this, sock, display, info);
					SThread.start();
					//���� ȣ��Ʈ�� �̸� ���
					info.setText(sock.getInetAddress().getHostName() + " ������ Ŭ���̾�Ʈ�� �����");
				}
			}catch(IOException io) {
				server.close();
				io.printStackTrace();
			}
		}catch(IOException io) {
			io.printStackTrace();
		}
	}
	

	public static void main(String[] args) {
		ChatWhisperS s = new ChatWhisperS();
		s.runServer();
	}
	
	class WinListener extends WindowAdapter{
		public void windowClosing(WindowEvent e) {
			System.exit(0);
		}
	}
}

class ServerThread2 extends Thread{
	Socket sock;
	BufferedWriter output;
	BufferedReader input;
	TextArea display;
	Label info;
	TextField text;
	String clientdata;
	String serverdata = "";
	ChatWhisperS cs;
	StringBuffer data;
	String[] id = new String[100];
	private static final String SEPARATOR = "|";
	private static final int REQ_LOGON = 1001;
	private static final int REQ_LOGOUT = 1002;
	private static final int REQ_LOGON_OVERLAP = 1003;
	private static final int REQ_SENDWORDS = 1021;
	private static final int REQ_WISPERSEND = 1022;
	public ServerThread2(ChatWhisperS c, Socket s, TextArea ta, Label l) {
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
		try {
			cs.list.add(this);
			while((clientdata = input.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(clientdata, SEPARATOR);
				int command = Integer.parseInt(st.nextToken());
				int Lcnt = cs.list.size();
				
				switch(command) {
				//�α���
				case REQ_LOGON:{
					String ID = st.nextToken();
					
					if(cs.hash.containsKey(ID) == true) { //�̹� �ִ� ���̵��� ��
//						ServerThread2 SThread = (ServerThread2)cs.hash.get(ID);
						display.append(ID + "�� �̹� �ִ� ���̵� �Դϴ�.\r\n"); //���� ȭ�鿡 ���
						output.write("�ߺ��� ID�Դϴ�." + "\r\n"); //�ߺ� ���̵�. Ŭ�󸮾�Ʈ�� ����
						output.flush();
						break;
					}else {
						display.append("Ŭ���̾�Ʈ�� " + ID + "(��)�� �α��� �Ͽ����ϴ�.\r\n");
						cs.list.add(this);
						cs.hash.put(ID, this);
						output.write(ID + "�� �α��� �Ͽ����ϴ�." + "\r\n"); //�α���, Ŭ���̾�Ʈ�� ����
						output.flush();
					}	
					break;
				}

				//Ŭ���̾�Ʈ�� ����
				case REQ_SENDWORDS : {
					String ID = st.nextToken();
					String message = st.nextToken();
					display.append(ID + " : " + message + "\r\n");
					for(int i = 0 ; i < Lcnt ; i++) {
						ServerThread2 SThread = (ServerThread2)cs.list.get(i);
						SThread.output.write(ID + " : " + message + "\r\n");
						SThread.output.flush();
					}
					break;
				}
					//Ŭ���̾�Ʈ�� �ӼӸ� ����
					case REQ_WISPERSEND : {
						String ID = st.nextToken(); //�ӼӸ� �߽���
						String WID = st.nextToken(); //�ӼӸ� ������
						String message = st.nextToken(); //�ӼӸ� ����
						display.append(ID + " -> " + WID + " : " + message + "\r\n");
						//�ؽ����̺��� �ͼӸ� �޽����� ������ Ŭ���̾�Ʈ�� �����带 ����
						ServerThread2 SThread = (ServerThread2)cs.hash.get(ID);	
						//�ӼӸ� �޽����� ������ Ŭ���̾�Ʈ�� ������
						SThread.output.write(ID + " -> " + WID + " : " + message + "\r\n");
						SThread.output.flush();
						//�ؽ����̺��� �ӼӸ� �޽����� ������ Ŭ���̾�Ʈ�� �����带 ����
						SThread = (ServerThread2)cs.hash.get(WID);
						//�ӼӸ� �޽����� ������ Ŭ���̾���� ������
						SThread.output.write(ID + " : " + message + "\r\n");
						SThread.output.flush();
						break;
					}
					case REQ_LOGOUT : { //�α׾ƿ� ��� �߰�
						cs.list.remove(this); //�α׾ƿ� �� Ŭ���̾�Ʈ ����
						String ID = st.nextToken();
						display.append("Ŭ���̾�Ʈ " + ID + "(��)�� �α׾ƿ� �Ͽ����ϴ�.\r\n");
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