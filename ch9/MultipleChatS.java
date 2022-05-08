package ch9;

import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.util.*;
import java.io.*;
import java.util.List;

//�޽����� �̿����� �ʰ� �ټ��� Ŭ���̾�Ʈ ���� ä�� ���α׷�
public class MultipleChatS extends Frame{
	
	TextArea display;
	Label info;
	String clientdata = "";
	String serverdata = "";
	List <ServerThread> list;
	public ServerThread SThread;
	
	public MultipleChatS() {
		super("����");
		info = new Label();
		add(info, BorderLayout.CENTER);
		display = new TextArea("", 0, 0 , TextArea.SCROLLBARS_VERTICAL_ONLY);
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
			list = new ArrayList<ServerThread>(); //�����迭
			server = new ServerSocket(5000, 100); //5000�� ��Ʈ�� ���� 100�� ���� ����
			
			try {
				while(true) {
					sock = server.accept(); //Ŭ���̾�Ʈ�� ���ӿ�û�� ���� Ŭ���̾�Ʈ ���ϰ� �����
					SThread = new ServerThread(this, sock, display, info, serverdata);
					SThread.start(); //��Ƽ������� ����
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
		MultipleChatS s = new MultipleChatS();
		s.runServer();
	}
	
	class WinListener extends WindowAdapter{
		public void windowClosing(WindowEvent we) {
			System.exit(0);
		}
	}

}

class ServerThread extends Thread{
	Socket sock;
	InputStream is;
	InputStreamReader isr;
	BufferedReader input;
	OutputStream os;
	OutputStreamWriter osw;
	BufferedWriter output;
	TextArea display;
	Label info;
	TextField text;
	String serverdata = "";
	MultipleChatS cs;
	String serverUser = "";
	
	public ServerThread(MultipleChatS c, Socket s, TextArea ta, Label l, String data) {
		sock = s;
		display = ta;
		info = l;
		serverdata = data;
		cs = c;
		
		try {
			is = sock.getInputStream();
			isr = new InputStreamReader(is);
			input = new BufferedReader(isr);
			os = sock.getOutputStream();
			osw = new OutputStreamWriter(os);
			output = new BufferedWriter(osw);
		}catch(IOException io) {
			io.printStackTrace();
		}
	}
	
	public void run() {
		cs.list.add(this); //list�� �� �߰�
		String clientdata;
		
		try {
			while((clientdata = input.readLine()) != null) {
				display.append(clientdata + "\r\n");
				int cnt = cs.list.size();
				
				//��� Ŭ���̾�Ʈ�� �����͸� �����Ѵ�.
				for(int i = 0 ; i < cnt ; i++) {
					ServerThread SThread = (ServerThread)cs.list.get(i);
					if(sock != this.sock) {
						SThread.output.write(clientdata + "\r\n"); 
						SThread.output.flush();
					}
				}
			}
		}catch(IOException io) {
			io.printStackTrace();
		}
		cs.list.remove(this); //����Ʈ���� close �� Ŭ���̾�Ʈ�� �����.
	}
}
