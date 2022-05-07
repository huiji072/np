import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

//if client ==null idsplay.append("")

public class OneToOneC extends Frame implements ActionListener{
	
	TextArea display;
	TextField text;
	Label lword;
	BufferedWriter output;
	BufferedReader input;
	Socket client;
	String clientdata = "";
	Button reconn;
	
	public OneToOneC() {
		super("클라이언트");
		display = new TextArea("", 0, 0, TextArea.SCROLLBARS_VERTICAL_ONLY);
		display.setEditable(false);
		add(display, BorderLayout.CENTER);
		
		Panel pword = new Panel(new BorderLayout());
		lword = new Label("대화말");
		text = new TextField(30);
		reconn = new Button("재접속");
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
			input = new BufferedReader(new InputStreamReader(client.getInputStream()));
			output = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
				

					while(true) {
						String serverdata = input.readLine();
						if(serverdata == null) {
							display.append("서버와의 접속이 중단");
							break;
						}else {
							display.append("\n 서버메시지 "+serverdata);
						}
				}
					client.close();
					client = null;

		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void actionPerformed(ActionEvent ae) {
		//클라이언트 값 출력, 서버에 보내기
		
		if(ae.getSource() == reconn && client != null) {
			return;
		}
		if(ae.getSource()==reconn && client == null ) {
			ClientThread reconn = new ClientThread();
			reconn.start();
			return;
		}
		if(client == null) {
			display.append("연결된 서버가 없습니다.");
			display.setText("");
			return;
		}
		
		clientdata = text.getText();
		try {
			display.append("\n클라이언트 : " + clientdata);
			output.write(clientdata + "\r\n");
			output.flush();
			text.setText("");
			
			if(clientdata.equals("quit")) {
				client.close();
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
		
//		if(ae.getSource() == reconn) {
//			if(client.isClosed()) {
//				try {
//					client = new Socket(InetAddress.getLocalHost(), 5000);
//					display.setText("restart"); //test
//					ClientThread CThread = null;
//					CThread = new ClientThread(client, display);
//					CThread.start();
//
//				}catch(IOException e) {}
//			}else {
//				try {
//					client.close();
//					display.setText("restart2"); //test
//					this.runClient(); //재접속
//				}catch(IOException e) {}
//
//			}
//			
//		}


	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		OneToOneC c = new OneToOneC();
		c.runClient();
	}
	
	class WinListener extends WindowAdapter{
		public void windowClosing(WindowEvent e) {
			try {
				if(client!=null)
					client.close();
			}catch(IOException e2) {
				System.out.println(e2);
			}
			System.exit(0);
		}
	}
	class ClientThread extends Thread{
		public void run() {
			runClient();
		}
	}
}

