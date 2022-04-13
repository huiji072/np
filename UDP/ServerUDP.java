package UDP;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.*;

public class ServerUDP extends Frame{
	
	private TextArea display;
	private DatagramPacket sendPacket, receivePacket;
	private DatagramSocket socket;
	
	public ServerUDP() {
		super("����");
		display = new TextArea();
		add(display, BorderLayout.CENTER);
		addWindowListener(new WinListener());
		setSize(400, 300);
		setVisible(true);
		
		try {
			socket = new DatagramSocket(5000);
			
		}catch(SocketException se) {
			se.printStackTrace();
			System.exit(1);;
		}
	}
	
	public void waitForPacket() {
		while(true) {
			try {
				byte data[] = new byte[100];
				receivePacket = new DatagramPacket(data, data.length);
				socket.receive(receivePacket);
				display.append("\n ���ŵ� ��Ŷ" + 
				"\n Ŭ���̾�Ʈ �ּ� " + receivePacket.getAddress() +
				"\nŬ�󸮾�Ʈ ��Ʈ��ȣ " + receivePacket.getPort() +
				"\n�޽��� ���� : " + receivePacket.getLength() +
				"\n�޽��� : " + new String(receivePacket.getData())
				);
				display.append("\n\nŬ���̾�Ʈ�� �ٽ� ����(Echo data)...");
				sendPacket = new DatagramPacket(receivePacket.getData(),
						receivePacket.getLength(),
						receivePacket.getAddress(),
						receivePacket.getPort()
						);
				socket.send(sendPacket);
				display.append("��Ŷ ���� �Ϸ�\n\n");
			}catch(IOException io) {
				display.append(io.toString() + "\n");
				io.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		ServerUDP s = new ServerUDP();	
		s.waitForPacket();

	}
	class WinListener extends WindowAdapter{
		public void WindowClosing(WindowEvent e) {
			System.exit(0);
		}
	}

}
