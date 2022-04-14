package UDP;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.*;

public class ClientUDP extends Frame implements ActionListener{
	
	private TextField enter;
	private TextArea display;
	private DatagramPacket sendPacket, receivePacket;
	private DatagramSocket socket;
	
	public ClientUDP() {
		super("Ŭ���̾�Ʈ");
		enter = new TextField("�޽����� �Է��ϼ���");
		enter.addActionListener(this);
		add(enter, BorderLayout.NORTH);
		display = new TextArea();
		add(display, BorderLayout.CENTER);
		addWindowListener(new WinListener());
		setSize(400, 300);
		setVisible(true);
		
		try {
			socket = new DatagramSocket(4000);
		}catch(SocketException se) {
			se.printStackTrace();
			System.exit(1);
		}
	}
	
	public void waitForPackets() {
		byte[] buffer = new byte[8192];

		while(true) {
			try {

				byte data[] = new byte[100];
				receivePacket = new DatagramPacket(data, data.length);
				socket.receive(receivePacket);
				display.append("\n ���ŵ� ��Ŷ" +
				"\n �����ּ� : " + receivePacket.getAddress() +
				"\n���� ��Ʈ��ȣ : " + receivePacket.getPort() +
				"\n�޽��� ���� : " + receivePacket.getLength() +
				"\n�޽��� : " + new String(receivePacket.getData())
						);
			
		}catch(IOException e) {
			display.append(e.toString() + "\n");
			e.printStackTrace();
		}
	}
	}
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ClientUDP c = new ClientUDP();
		c.waitForPackets();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		// TODO Auto-generated method stub
		try {
			display.append("\n�۽� �޽��� : " + e.getActionCommand() + "\n");
			String s = e.getActionCommand();
			byte data[] = s.getBytes();
			sendPacket = new DatagramPacket(data, data.length, InetAddress.getLocalHost(), 5000);
			socket.send(sendPacket);
			display.append("��Ŷ���ۿϷ�\n\n");
		}catch(IOException e2) {
			display.append(e2.toString() + "\n");
			e2.printStackTrace();
		}
		
	}
	class WinListener extends WindowAdapter{
		public void windowClosing(WindowEvent e) {
			System.exit(0);
		}
	}

}
