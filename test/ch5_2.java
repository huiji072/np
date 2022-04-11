package test;

import java.awt.*;
import java.awt.event.*;
import java.net.*;

//������ �̸����� ����Ǿ� �ִ� ���ϵ�κ��� ������ �̸��� �о�鿩 ȣ��Ʈ�� �Ҵ�� ��� �ּҿ�
//ȣ��Ʈ�� ��ǥ IP�ּ��� Ŭ���� ������ �ι�° Panel TextArea�� ���
public class ch5_2 extends Frame implements ActionListener{

	TextField hostname;
	Button getinfor;
	TextArea display;

	public static void main(String[] args) {
		ch5_2 host = new ch5_2("InetAddress Ŭ����");
		host.setVisible(true);
	}

	public ch5_2(String str) {
		super(str);
		addWindowListener(new WinListener());
		setLayout(new BorderLayout());
		
		Panel inputpanel = new Panel();
		inputpanel.setLayout(new BorderLayout());
		inputpanel.add("North", new Label("ȣ��Ʈ �̸�:")); //������ �̸�
		hostname = new TextField("", 30);
		
		getinfor = new Button("ȣ��Ʈ ���� ���");
		inputpanel.add("Center", hostname);
		inputpanel.add("South", getinfor);
		
		getinfor.addActionListener(this); //�̺�Ʈ ���
		add("North", inputpanel); //�г��� �����ӿ� ����
		
		Panel outputpanel = new Panel(); //�ι�° �г�
		outputpanel.setLayout(new BorderLayout());
		
		display = new TextArea("", 24, 40);
		display.setEditable(false);
		outputpanel.add("North", new Label("���ͳ� �ּ�"));
		outputpanel.add("Center", display);
		add("Center",outputpanel);
		setSize(270, 150);
		
	}



	public void actionPerformed(ActionEvent e) {

		String name = hostname.getText(); //�Էµ� ȣ��Ʈ �̸��� ���Ѵ�.
		try {
			InetAddress inet = InetAddress.getByName(name); //InetAddress ��ü����
			String ip = inet.getHostName() + "\n"; //ȣ��Ʈ�� �̸��� ���Ѵ�.
			display.append(ip);
			ip = inet.getHostAddress()+"\n"; //ȣ��Ʈ�� IP �ּҸ� ���Ѵ�.
			display.append(ip);

			//ȣ��Ʈ�� �Ҵ�� ����ּ�
			InetAddress[] addresses = InetAddress.getAllByName(name);
			System.out.println("ȣ��Ʈ�� �Ҵ�� ��� �ּ�");
			for(int i = 0 ; i <addresses.length;i++) {
				display.append(String.valueOf(addresses[i])+"\n");
			}
			display.append("Ŭ���� ���� " + ipClass(addresses[0].getAddress()) + "\n"); //Ŭ���� ����
			

		}catch(UnknownHostException ue) {
			String ip = name + ": �ش� ȣ��Ʈ�� �����ϴ�.";
			display.append(ip);
		}
	}
	
	class WinListener extends WindowAdapter{
		public void windowClosing(WindowEvent we) {
			System.exit(0);
		}
	}
	
	static char ipClass(byte[] ip) {
		int highByte = 0xff & ip[0];
		return(highByte<128) ? 'A' : (highByte < 192) ? 'B' : (highByte<224) ? 'C' : 
			(highByte<240) ? 'D' : 'E';
	
}
}
