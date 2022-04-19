package UDP;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.*;

public class ServerUDP extends Frame implements ActionListener{
	private TextField enter;
	private TextArea display;
	private DatagramPacket sendPacket, receivePacket;
	private DatagramSocket socket;
	
	public ServerUDP() {
		super("����");
		
		enter = new TextField("�޽����� �Է��ϼ���");
		enter.addActionListener(this);
		add(enter, BorderLayout.NORTH);
		
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
				
//				sendPacket = new DatagramPacket(receivePacket.getData(),
//						receivePacket.getLength(),
//						receivePacket.getAddress(),
//						receivePacket.getPort()
//						);
//				
//				socket.send(sendPacket);

				display.append("��Ŷ ���� �Ϸ�\n\n");
			}catch(IOException io) {
				display.append(io.toString() + "\n");
				io.printStackTrace();
			}
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		
		// TODO Auto-generated method stub
		try {
			display.append("\n�۽� �޽��� : " + e.getActionCommand() + "\n");
			String s = e.getActionCommand();
			byte data[] = s.getBytes();
			
			sendPacket = new DatagramPacket(data, data.length, InetAddress.getLocalHost(), 4000);
			socket.send(sendPacket);
			display.append("��Ŷ���ۿϷ�\n\n");
		}catch(IOException e2) {
			display.append(e2.toString() + "\n");
			e2.printStackTrace();
		}
		
	}

	public static void main(String[] args) {
		ServerUDP s = new ServerUDP();	
		s.waitForPacket();

	}
	   class WinListener extends WindowAdapter{
		      public void windowClosing(WindowEvent e){
		         System.exit(0);
		      }
		   }

}
