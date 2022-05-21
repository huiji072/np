package test;

import java.io.*;
import java.net.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MulC extends Frame implements ActionListener, KeyListener {
	TextArea display;
	TextField wtext, ltext;
	Label mlbl, wlbl, loglbl;
	BufferedWriter output;
	BufferedReader input;
	Socket client, client1;
	StringBuffer clientdata;
	String serverdata;
	String ID;
	Button logon, logout;
	Panel plabel, ptotal, pword;
	Boolean check;

	int port = 5265;
	InetAddress group;
	String m = "주소요청";
	DatagramPacket packet, packetA;
	DatagramSocket ds;
	byte[] data;

	public ClientThread CThread;
	DatagramPacket odp, idp;

	private static final String SEPARATOR = "|";
	private static final int REQ_LOGON = 1001;
	private static final int REQ_SENDWORDS = 1021;
	private static final int REQ_LOGOUT = 1004;

	public MulC() {
		super("클라이언트");
		
		mlbl = new Label("멀티캐스트 채팅 서버에 가입을 요청합니다!");
		add(mlbl, BorderLayout.NORTH);

		display = new TextArea("", 0, 0, TextArea.SCROLLBARS_VERTICAL_ONLY);
		display.setEditable(false);
		add(display, BorderLayout.CENTER);

		ptotal = new Panel(new BorderLayout());

		pword = new Panel(new BorderLayout());
		wlbl = new Label("대화말");
		wtext = new TextField(30);
		wtext.addKeyListener(this);
		pword.add(wlbl, BorderLayout.WEST);
		pword.add(wtext, BorderLayout.CENTER);
		ptotal.add(pword, BorderLayout.CENTER);

		plabel = new Panel(new BorderLayout());
		loglbl = new Label("로그온");
		ltext = new TextField(30);
		//ltext.addActionListener(this); //입력된 데이터를 송신하기 위한 이벤트 연결
		logon = new Button("ON");
		logon.addActionListener(this);
		plabel.add(logon, BorderLayout.EAST);
		plabel.add(loglbl, BorderLayout.WEST);
		plabel.add(ltext, BorderLayout.CENTER);
		ptotal.add(plabel, BorderLayout.SOUTH);

		add(ptotal, BorderLayout.SOUTH);

		addWindowListener(new WinListener());
		setSize(300, 250);
		setVisible(true);
	}

	public void runClient() {
		try {
			clientdata = new StringBuffer(2048);
			ds = new DatagramSocket();
			odp = new DatagramPacket(new byte[1], 1, InetAddress.getLocalHost(), 5000);
			idp = new DatagramPacket(new byte[60000], 60000);
		} catch (IOException e) {
			mlbl.setText("Unknown host");
		}
	}

	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == logon) {
			if (ID == null) {
				ID = ltext.getText();
				try {
					clientdata.setLength(0);
					clientdata.append(REQ_LOGON);
					clientdata.append(SEPARATOR);
					clientdata.append(ID);
					clientdata.append(SEPARATOR);
					clientdata.append("로그인 하였습니다.\r\n");
					data = new String(clientdata).getBytes();
					odp.setData(data);
					odp.setLength(data.length);
					ds.send(odp);

					ds.receive(idp);
					String address = new String(idp.getData(), 0, idp.getLength());
					StringTokenizer st = new StringTokenizer(address, SEPARATOR);
					String address_g = st.nextToken();
					address_g = address_g.replace("/", "");
					group = InetAddress.getByName(address_g);
					port = Integer.parseInt(st.nextToken());

					MulticastSocket mssocket = new MulticastSocket(port); //멀티캐스트 그룹 가입
					mssocket.joinGroup(group);

					CThread = new ClientThread(mssocket);
					CThread.start();

					ltext.setVisible(false);
					plabel.remove(logon); //로그온 삭제 후
					
					logout = new Button("로그아웃"); //로그아웃 버튼 추가
					logout.addActionListener(this);
					plabel.add(logout, BorderLayout.CENTER);
					mlbl.setText(ID + "(으)로 로그인 하였습니다.");
					plabel.validate();
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				mlbl.setText("다시 로그인 하세요!!!");
			}

		} else if (ae.getSource() == logout) {
			mlbl = new Label("멀티캐스트 채팅 서버에 가입을 요청합니다!");
			logout.setVisible(false);
			try {
				ltext.setText("");
				clientdata.setLength(0);
				clientdata.append(REQ_LOGOUT);
				clientdata.append(SEPARATOR);
				clientdata.append(ID);
				clientdata.append(SEPARATOR);
				clientdata.append("이(가) 로그아웃하였습니다.");

				data = new String(clientdata).getBytes();

				odp.setData(data);
				odp.setLength(data.length);
				ds.send(odp);

				plabel.remove(logout);
				ltext.setVisible(true);

				logon = new Button("ON");
				logon.addActionListener(this);
				plabel.add(logon, BorderLayout.EAST);
				plabel.validate();
				ID = null;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String args[]) {
		MulC c = new MulC();
		c.runClient();
	}

	class WinListener extends WindowAdapter { // 창은 닫히는데 로그아웃을 한 상태를 만들어줘야함.
		public void windowClosing(WindowEvent e) {
			try {
				ltext.setText("");
				clientdata.setLength(0);
				clientdata.append(REQ_LOGOUT);
				clientdata.append(SEPARATOR);
				clientdata.append(ID);
				clientdata.append(SEPARATOR);
				clientdata.append("이(가) 로그아웃하였습니다.");
				data = new String(clientdata).getBytes();
				odp.setData(data);
				odp.setLength(data.length);
				ds.send(odp);
				ltext.setVisible(true);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			System.exit(0);
		}
	}

	public void keyPressed(KeyEvent ke) {
		if (ke.getKeyChar() == KeyEvent.VK_ENTER) {
			String message = new String();
			message = wtext.getText();
			if (ID == null) {
				mlbl.setText("다시 로그인 하세요!!!");
				wtext.setText("");
			} else {
				try {
					clientdata.setLength(0);
					clientdata.append(REQ_SENDWORDS);
					clientdata.append(SEPARATOR);
					clientdata.append(ID);
					clientdata.append(SEPARATOR);
					clientdata.append(message);
					clientdata.append("\r\n");
					data = new String(clientdata).getBytes();
					odp.setData(data);
					odp.setLength(data.length);
					ds.send(odp);
					wtext.setText("");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	class ClientThread extends Thread {
		MulticastSocket mssocket;

		public ClientThread(MulticastSocket ms) {
			mssocket = ms;
		}

		public void run() {
			try {
				while (!Thread.interrupted()) {
					idp.setLength(idp.getData().length);
					mssocket.receive(idp);
					String message = new String(idp.getData(), 0, idp.getLength());
					display.append(message);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void keyReleased(KeyEvent ke) {
	}

	public void keyTyped(KeyEvent ke) {
	}
}