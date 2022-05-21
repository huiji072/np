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
	String m = "�ּҿ�û";
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
		super("Ŭ���̾�Ʈ");
		
		mlbl = new Label("��Ƽĳ��Ʈ ä�� ������ ������ ��û�մϴ�!");
		add(mlbl, BorderLayout.NORTH);

		display = new TextArea("", 0, 0, TextArea.SCROLLBARS_VERTICAL_ONLY);
		display.setEditable(false);
		add(display, BorderLayout.CENTER);

		ptotal = new Panel(new BorderLayout());

		pword = new Panel(new BorderLayout());
		wlbl = new Label("��ȭ��");
		wtext = new TextField(30);
		wtext.addKeyListener(this);
		pword.add(wlbl, BorderLayout.WEST);
		pword.add(wtext, BorderLayout.CENTER);
		ptotal.add(pword, BorderLayout.CENTER);

		plabel = new Panel(new BorderLayout());
		loglbl = new Label("�α׿�");
		ltext = new TextField(30);
		//ltext.addActionListener(this); //�Էµ� �����͸� �۽��ϱ� ���� �̺�Ʈ ����
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
					clientdata.append("�α��� �Ͽ����ϴ�.\r\n");
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

					MulticastSocket mssocket = new MulticastSocket(port); //��Ƽĳ��Ʈ �׷� ����
					mssocket.joinGroup(group);

					CThread = new ClientThread(mssocket);
					CThread.start();

					ltext.setVisible(false);
					plabel.remove(logon); //�α׿� ���� ��
					
					logout = new Button("�α׾ƿ�"); //�α׾ƿ� ��ư �߰�
					logout.addActionListener(this);
					plabel.add(logout, BorderLayout.CENTER);
					mlbl.setText(ID + "(��)�� �α��� �Ͽ����ϴ�.");
					plabel.validate();
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				mlbl.setText("�ٽ� �α��� �ϼ���!!!");
			}

		} else if (ae.getSource() == logout) {
			mlbl = new Label("��Ƽĳ��Ʈ ä�� ������ ������ ��û�մϴ�!");
			logout.setVisible(false);
			try {
				ltext.setText("");
				clientdata.setLength(0);
				clientdata.append(REQ_LOGOUT);
				clientdata.append(SEPARATOR);
				clientdata.append(ID);
				clientdata.append(SEPARATOR);
				clientdata.append("��(��) �α׾ƿ��Ͽ����ϴ�.");

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

	class WinListener extends WindowAdapter { // â�� �����µ� �α׾ƿ��� �� ���¸� ����������.
		public void windowClosing(WindowEvent e) {
			try {
				ltext.setText("");
				clientdata.setLength(0);
				clientdata.append(REQ_LOGOUT);
				clientdata.append(SEPARATOR);
				clientdata.append(ID);
				clientdata.append(SEPARATOR);
				clientdata.append("��(��) �α׾ƿ��Ͽ����ϴ�.");
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
				mlbl.setText("�ٽ� �α��� �ϼ���!!!");
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