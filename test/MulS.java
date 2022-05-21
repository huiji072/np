package test;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.event.*;

public class MulS extends Frame {
	TextArea display;
	Label info;
	Socket sock;
	BufferedWriter bw;
	BufferedReader br;
	TextField text;
	String clientdata = "";
	String serverdata = "";
	MulticastSocket socket;
	DatagramPacket odp, idp;
	String group = "239.10.1.1";
	InetAddress ia;
	int port = 5265;
	byte[] data = new byte[1024];
	DatagramSocket theSocket;

	private static final String SEPARATOR = "|";
	private static final int REQ_LOGON = 1001;
	private static final int REQ_SENDWORDS = 1021;
	private static final int LOGOUT = 1004;

	public MulS() {
		super("서버");
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
		try {
			ia = InetAddress.getByName("239.10.1.1");
			theSocket = new DatagramSocket(5000);
			odp = new DatagramPacket(new byte[1], 1);
			idp = new DatagramPacket(new byte[60000], 60000);
			info.setText("멀티캐스트 채팅 그룹 주소 : " + group);
			socket = new MulticastSocket();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

		try {
			while (true) {
				idp.setLength(idp.getData().length);
				theSocket.receive(idp);
				clientdata = new String(idp.getData(), 0, idp.getLength());
				System.out.println(clientdata);
				StringTokenizer st = new StringTokenizer(clientdata, SEPARATOR);
				int command = Integer.parseInt(st.nextToken());

				switch (command) {
				case REQ_LOGON: { // “1001|아이디”를 수신한 경우 멀티캐스트 그룹 가입 요청 받음
					String ID = st.nextToken();
					String text = st.nextToken();
					
					String inf = ia + SEPARATOR + Integer.toString(port); //그룹 주소, 포트번호 전송
					
					display.append("클라이언트가 " + ID + text);

					System.out.println("멀티캐스트 채팅 그룹 주소는" + inf + "입니다.\r\n");

					String group_i = "멀티캐스트 채팅 그룹 주소는" + inf + "입니다.\r\n";
					
					data = new String(group_i).getBytes();
					
					data = inf.toString().getBytes();
					
					odp.setData(data);
					odp.setLength(data.length);
					odp.setAddress(idp.getAddress());
					odp.setPort(idp.getPort());
					theSocket.send(odp);

					break;
				}
				case REQ_SENDWORDS: { // “1021|아이디|대화말”를 수신
					String ID = st.nextToken();
					String message = st.nextToken();

					String clientt = ID + ":" + message;
					data = new String(clientt).getBytes();
					odp.setData(data);
					odp.setLength(data.length);
					odp.setAddress(ia);
					odp.setPort(port);
					socket.send(odp);
					display.append(clientt);
					break;
				}
				case LOGOUT: { // 1004|아이디 로그아웃
					String ID = st.nextToken();
					String logms = st.nextToken();
					display.append("클라이언트가 " + ID);
					display.append(logms + "\r\n");
					break;
				}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			sock.close();
		} catch (IOException ea) {
			ea.printStackTrace();
		}

	}

	public static void main(String args[]) {
		MulS s = new MulS();
		s.runServer();
	}

	class WinListener extends WindowAdapter {
		public void windowClosing(WindowEvent e) {
			System.exit(0);
		}
	}
}