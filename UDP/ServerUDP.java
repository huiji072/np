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
		super("서버");
		
		enter = new TextField("메시지를 입력하세요");
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
				
				display.append("\n 수신된 패킷" + 
				"\n 클라이언트 주소 " + receivePacket.getAddress() +
				"\n클라리언트 포트번호 " + receivePacket.getPort() +
				"\n메시지 길이 : " + receivePacket.getLength() +
				"\n메시지 : " + new String(receivePacket.getData())
				);
				
				display.append("\n\n클라이언트로 다시 전송(Echo data)...");
				
//				sendPacket = new DatagramPacket(receivePacket.getData(),
//						receivePacket.getLength(),
//						receivePacket.getAddress(),
//						receivePacket.getPort()
//						);
//				
//				socket.send(sendPacket);

				display.append("패킷 전송 완료\n\n");
			}catch(IOException io) {
				display.append(io.toString() + "\n");
				io.printStackTrace();
			}
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		
		// TODO Auto-generated method stub
		try {
			display.append("\n송신 메시지 : " + e.getActionCommand() + "\n");
			String s = e.getActionCommand();
			byte data[] = s.getBytes();
			
			sendPacket = new DatagramPacket(data, data.length, InetAddress.getLocalHost(), 4000);
			socket.send(sendPacket);
			display.append("패킷전송완료\n\n");
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
