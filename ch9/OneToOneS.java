
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
		super("서버");
		display = new TextArea("", 0, 0, TextArea.SCROLLBARS_VERTICAL_ONLY);
		display.setEditable(false);
		add(display, BorderLayout.CENTER);
		
		
		Panel pword = new Panel(new BorderLayout());
		lword = new Label("대화말");
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
					connection = server.accept();//접속요청이 올 때까지 기다린다.
					SThread = new ServerThread(connection, display);
					SThread.start();
	
				}
			}catch(IOException e) {
				server.close();
				server=null;
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
		//커넥션 ===null 이면 conection = socket 스레드 생성,스레드 시작
	}
	
	public void actionPerformed(ActionEvent ae) {

		if(connection == null) {
			display.append("연결된 클라이언트가 없습니다.");
			text.setText("");
			return;
		}
		
		serverdata = text.getText();
		try {
			display.append("\n서버 : " + serverdata);
			output.write(serverdata + "\r\n");
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
	Socket connection;
	OneToOneS sv;
	TextArea display;
	ServerThread(Socket socket, TextArea ta){
		connection = socket;
		display = ta;
	}

	public void run() {
		try {
			InputStream is = connection.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			input = new BufferedReader(isr);
			OutputStream os = connection.getOutputStream();
			OutputStreamWriter osw = new OutputStreamWriter(os);
			output = new BufferedWriter(osw);
			
			while(true) {
				String clientdata = input.readLine();
				if(clientdata == null) {
					display.append("\n 클라이언트와의 접속이 중단되었습니다.");
					output.flush();
					break;
				}else {
					display.append("\n클라이언트 메시지 :  " + clientdata);
				}
			}
			connection.close();
		}catch(IOException e) {
			System.out.println(e);
		}

	}
}
