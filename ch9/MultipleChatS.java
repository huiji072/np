package ch9;

import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.util.*;
import java.io.*;
import java.util.List;

//메시지를 이용하지 않고 다수의 클라이언트 간의 채팅 프로그램
public class MultipleChatS extends Frame{
	
	TextArea display;
	Label info;
	String clientdata = "";
	String serverdata = "";
	List <ServerThread> list;
	public ServerThread SThread;
	
	public MultipleChatS() {
		super("서버");
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
			list = new ArrayList<ServerThread>(); //동적배열
			server = new ServerSocket(5000, 100); //5000반 포트에 동시 100개 연결 가능
			
			try {
				while(true) {
					sock = server.accept(); //클라이언트로 접속요청이 오면 클라이언트 소켓과 연결됨
					SThread = new ServerThread(this, sock, display, info, serverdata);
					SThread.start(); //멀티스레드로 실행
					//서버 호스트의 이름 출력
					info.setText(sock.getInetAddress().getHostName() + " 서버는 클라이언트와 연결됨");
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
		cs.list.add(this); //list에 값 추가
		String clientdata;
		
		try {
			while((clientdata = input.readLine()) != null) {
				display.append(clientdata + "\r\n");
				int cnt = cs.list.size();
				
				//모든 클라이언트에 데이터를 전송한다.
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
		cs.list.remove(this); //리스트에서 close 된 클라이언트를 지운다.
	}
}
