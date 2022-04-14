package test;

import java.awt.*;
import java.awt.event.*;
import java.net.*;

public class GetHostInfor extends Frame implements ActionListener{
	
	static Panel p1, p2;
	static Label hostname, address;
	static TextField host;
	static Button enter;
	static TextArea infor;
	
	public GetHostInfor(String str) {
		super(str);
		
		setLayout(new BorderLayout());
		addWindowListener(new WinListener());
		p1 = new Panel();
		p1.setLayout(new BorderLayout());
		hostname = new Label("호스트 이름");
		p1.add("North", hostname);
		host = new TextField("",30);
		p1.add("Center", host);
		enter = new Button("호스트 정보 얻기");
		p1.add("South", enter);
		enter.addActionListener(this);
		add("North", p1);
		
		p2 = new Panel();
		p2.setLayout(new BorderLayout());
		address = new Label("인터넷 주소");
		p2.add("North", address);
		infor = new TextArea("", 24, 30);
		infor.setEditable(false);
		p2.add("Center", infor);
		add("South", p2);
		setSize(300, 200);
		
		
	}

	public static void main(String[] args) {
		GetHostInfor ghi = new GetHostInfor("ia");
		ghi.setVisible(true);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		
		String name = host.getText();
		
		try {
			InetAddress ia = InetAddress.getByName(name);
			String ip = ia.getHostName();
			infor.append(ip);
			infor.append(ia.getHostAddress());
		}catch(UnknownHostException e2) {
			System.out.println(e2);
		}
		
		
	}
	
	class WinListener extends WindowAdapter{
		public void windowClosing(WindowEvent we) {
			System.exit(0);
		}
	}

}
