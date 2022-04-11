package ch5;

import java.awt.*;
import java.awt.event.*;
import java.net.*;

public class ch5_6 extends Frame implements ActionListener{

	TextField hostname; //ȣ��Ʈ �̸��� �Է¹޴� �ʵ�
	Button getinfor; //�Էµ� ȣ��Ʈ�� ���� IP ������ �д� ��ư
	TextArea display; //������ IP�� ���� ������ ����ϴ� �ʵ�
	
	public static void main(String[] args) {

	ch5_6 host = new ch5_6("InetAddress Ŭ����");
	host.setVisible(true);

	}
	
	public ch5_6(String str) {
		super(str);
		addWindowListener(new WinListener());
		setLayout(new BorderLayout());
		Panel inputpanel = new Panel(); // ù��° �г�
		inputpanel.setLayout(new BorderLayout());
		inputpanel.add("North", new Label("ȣ��Ʈ �̸�"));
		
		hostname = new TextField("", 30);
		getinfor = new Button("ȣ��Ʈ ���� ���");
		inputpanel.add("Center", hostname);
		inputpanel.add("South", getinfor);
		
		getinfor.addActionListener(this); //�̺�Ʈ���
		add("North", inputpanel);
		
		Panel outputpanel = new Panel(); //�ι�° �г�
		outputpanel.setLayout(new BorderLayout());
		display = new TextArea("", 24, 40);
		display.setEditable(false);
		outputpanel.add("North", new Label("���ͳ��ּ�"));
		outputpanel.add("Center", display);
		add("Center", outputpanel);
		setSize(270, 200);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String name = hostname.getText(); //�Էµ� ȣ��Ʈ �̸��� ���Ѵ�.
		try {
			InetAddress inet = InetAddress.getByName(name); //InetAddress ��ü ����
			String ip = inet.getHostName()+ "\n"; //ȣ��Ʈ �̸�
			display.append(ip);
			ip = inet.getHostAddress()+"\n";
			display.append(ip); //ȣ��Ʈ�� IP �ּ�
			
		}catch(UnknownHostException e2) {
			System.out.println(e);
		}
	}
	
	class WinListener extends WindowAdapter{
		public void windowClosing(WindowEvent we) {
			System.exit(0);
		}
	}
}
