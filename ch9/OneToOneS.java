<<<<<<< HEAD
package ch9;
=======
>>>>>>> 19d0b2ca2316b20174aabaf4db36ac7d4d8bdbd8

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.List;

public class OneToOneS extends Frame implements ActionListener{
	
	TextArea display;
	TextField text;
	Label lword;
	Socket connection;
	BufferedWriter output;
	BufferedReader input;
	String clienddata="";
	String serverdata = "";
	ServerSocket server;
	List <ServerThread> list;
	
	public OneToOneS() {
<<<<<<< HEAD
		super("����");
=======
		super("서버");
>>>>>>> 19d0b2ca2316b20174aabaf4db36ac7d4d8bdbd8
		display = new TextArea("", 0, 0, TextArea.SCROLLBARS_VERTICAL_ONLY);
		display.setEditable(false);
		add(display, BorderLayout.CENTER);
		
		
		Panel pword = new Panel(new BorderLayout());
<<<<<<< HEAD
		lword = new Label("��ȭ��");
=======
		lword = new Label("대화말");
>>>>>>> 19d0b2ca2316b20174aabaf4db36ac7d4d8bdbd8
		text = new TextField(30);
		text.addActionListener(this);
		pword.add(lword, BorderLayout.WEST);
		pword.add(text, BorderLayout.CENTER);
		add(pword, BorderLayout.SOUTH);
		
		addWindowListener(new WinListener());
		setSize(300, 200);
		setVisible(true);
	}
	
	public void runServer() {
		ServerSocket server;
		try {
//			list = new ArrayList<ServerThread>();
			server = new ServerSocket(5000, 100);
			try {
				while(true) {
					ServerThread SThread = null;
<<<<<<< HEAD
					connection = server.accept();//���ӿ�û�� �� ������ ��ٸ���.
					SThread = new ServerThread(connection, display);
					SThread.start();
					OutputStream os = connection.getOutputStream();
					OutputStreamWriter osw = new OutputStreamWriter(os); //Ŭ���̾�Ʈ�� ��ȭ�� ����
					output = new BufferedWriter(osw);
=======
					connection = server.accept();//접속요청이 올 때까지 기다린다.
					SThread = new ServerThread(connection, display);
					SThread.start();
	
>>>>>>> 19d0b2ca2316b20174aabaf4db36ac7d4d8bdbd8
				}
			}catch(IOException e) {
				server.close();
				server=null;
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
<<<<<<< HEAD
=======
		//커넥션 ===null 이면 conection = socket 스레드 생성,스레드 시작
>>>>>>> 19d0b2ca2316b20174aabaf4db36ac7d4d8bdbd8
	}
	
	public void actionPerformed(ActionEvent ae) {

		if(connection == null) {
<<<<<<< HEAD
			display.append("����� Ŭ���̾�Ʈ�� �����ϴ�.");
=======
			display.append("연결된 클라이언트가 없습니다.");
>>>>>>> 19d0b2ca2316b20174aabaf4db36ac7d4d8bdbd8
			text.setText("");
			return;
		}
		
		serverdata = text.getText();
<<<<<<< HEAD
	
		try {
			display.append("\n���� : " + serverdata); //o
			output.write(serverdata + "\r\n");
			System.out.println(serverdata);
=======
		try {
			display.append("\n서버 : " + serverdata);
			output.write(serverdata + "\r\n");
>>>>>>> 19d0b2ca2316b20174aabaf4db36ac7d4d8bdbd8
			output.flush();
			text.setText("");
			
			if(serverdata.equals("quit")){
				connection.close();	
				connection=null;
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		OneToOneS s = new OneToOneS();
		s.runServer();

	}
	
	class WinListener extends WindowAdapter{
		public void windowClosing(WindowEvent e) {
			System.exit(0);
		}
	}

}

class ServerThread extends Thread{
	BufferedWriter output;
	BufferedReader input;
<<<<<<< HEAD
	Socket socket;
	OneToOneS sv;
	TextArea display;
	
	ServerThread(Socket connection, TextArea ta){
		socket = connection;
=======
	Socket connection;
	OneToOneS sv;
	TextArea display;
	ServerThread(Socket socket, TextArea ta){
		connection = socket;
>>>>>>> 19d0b2ca2316b20174aabaf4db36ac7d4d8bdbd8
		display = ta;
	}

	public void run() {
		try {
<<<<<<< HEAD
			String clientIp = socket.getInetAddress().getHostAddress();
			display.append("\n Ŭ���̾�Ʈ " + clientIp + "�� ����Ǿ����ϴ�.\n");
			
			InputStream is = socket.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			input = new BufferedReader(isr); //������ ������ ��ȭ�� ����
			OutputStream os = socket.getOutputStream();
			OutputStreamWriter osw = new OutputStreamWriter(os); //Ŭ���̾�Ʈ�� ��ȭ�� ����
=======
			InputStream is = connection.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			input = new BufferedReader(isr);
			OutputStream os = connection.getOutputStream();
			OutputStreamWriter osw = new OutputStreamWriter(os);
>>>>>>> 19d0b2ca2316b20174aabaf4db36ac7d4d8bdbd8
			output = new BufferedWriter(osw);
			
			while(true) {
				String clientdata = input.readLine();
				if(clientdata == null) {
<<<<<<< HEAD
					display.append("\n Ŭ���̾�Ʈ���� ������ �ߴܵǾ����ϴ�.");
					output.flush();
					break;
				}else {
					display.append("\nŬ���̾�Ʈ �޽��� :  " + clientdata);
					output.flush();
				}
			}
			socket.close();
			socket=null;
=======
					display.append("\n 클라이언트와의 접속이 중단되었습니다.");
					output.flush();
					break;
				}else {
					display.append("\n클라이언트 메시지 :  " + clientdata);
				}
			}
			connection.close();
>>>>>>> 19d0b2ca2316b20174aabaf4db36ac7d4d8bdbd8
		}catch(IOException e) {
			System.out.println(e);
		}

	}
<<<<<<< HEAD
}
=======
}
>>>>>>> 19d0b2ca2316b20174aabaf4db36ac7d4d8bdbd8
