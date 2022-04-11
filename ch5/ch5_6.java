package ch5;

import java.awt.*;
import java.awt.event.*;
import java.net.*;

public class ch5_6 extends Frame implements ActionListener{

	TextField hostname; //호스트 이름을 입력받는 필드
	Button getinfor; //입력된 호스트에 관한 IP 정보를 읽는 버튼
	TextArea display; //구해진 IP에 관한 정보를 출력하는 필드
	
	public static void main(String[] args) {

	ch5_6 host = new ch5_6("InetAddress 클래스");
	host.setVisible(true);

	}
	
	public ch5_6(String str) {
		super(str);
		addWindowListener(new WinListener());
		setLayout(new BorderLayout());
		Panel inputpanel = new Panel(); // 첫번째 패널
		inputpanel.setLayout(new BorderLayout());
		inputpanel.add("North", new Label("호스트 이름"));
		
		hostname = new TextField("", 30);
		getinfor = new Button("호스트 정보 얻기");
		inputpanel.add("Center", hostname);
		inputpanel.add("South", getinfor);
		
		getinfor.addActionListener(this); //이벤트등록
		add("North", inputpanel);
		
		Panel outputpanel = new Panel(); //두번째 패널
		outputpanel.setLayout(new BorderLayout());
		display = new TextArea("", 24, 40);
		display.setEditable(false);
		outputpanel.add("North", new Label("인터넷주소"));
		outputpanel.add("Center", display);
		add("Center", outputpanel);
		setSize(270, 200);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String name = hostname.getText(); //입력된 호스트 이름을 구한다.
		try {
			InetAddress inet = InetAddress.getByName(name); //InetAddress 객체 생성
			String ip = inet.getHostName()+ "\n"; //호스트 이름
			display.append(ip);
			ip = inet.getHostAddress()+"\n";
			display.append(ip); //호스트의 IP 주소
			
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
