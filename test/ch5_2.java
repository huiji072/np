package test;

import java.awt.*;
import java.awt.event.*;
import java.net.*;

//도메인 이름들이 저장되어 있는 파일들로부터 도메인 이름을 읽어들여 호스트에 할당된 모든 주소와
//호스트의 대표 IP주소의 클래스 유형을 두번째 Panel TextArea에 출력
public class ch5_2 extends Frame implements ActionListener{

	TextField hostname;
	Button getinfor;
	TextArea display;

	public static void main(String[] args) {
		ch5_2 host = new ch5_2("InetAddress 클래스");
		host.setVisible(true);
	}

	public ch5_2(String str) {
		super(str);
		addWindowListener(new WinListener());
		setLayout(new BorderLayout());
		
		Panel inputpanel = new Panel();
		inputpanel.setLayout(new BorderLayout());
		inputpanel.add("North", new Label("호스트 이름:")); //도메인 이름
		hostname = new TextField("", 30);
		
		getinfor = new Button("호스트 정보 얻기");
		inputpanel.add("Center", hostname);
		inputpanel.add("South", getinfor);
		
		getinfor.addActionListener(this); //이벤트 등록
		add("North", inputpanel); //패널을 프레임에 부착
		
		Panel outputpanel = new Panel(); //두번째 패널
		outputpanel.setLayout(new BorderLayout());
		
		display = new TextArea("", 24, 40);
		display.setEditable(false);
		outputpanel.add("North", new Label("인터넷 주소"));
		outputpanel.add("Center", display);
		add("Center",outputpanel);
		setSize(270, 150);
		
	}



	public void actionPerformed(ActionEvent e) {

		String name = hostname.getText(); //입력된 호스트 이름을 구한다.
		try {
			InetAddress inet = InetAddress.getByName(name); //InetAddress 객체생성
			String ip = inet.getHostName() + "\n"; //호스트의 이름을 구한다.
			display.append(ip);
			ip = inet.getHostAddress()+"\n"; //호스트의 IP 주소를 구한다.
			display.append(ip);

			//호스트에 할당된 모든주소
			InetAddress[] addresses = InetAddress.getAllByName(name);
			System.out.println("호스트에 할당된 모든 주소");
			for(int i = 0 ; i <addresses.length;i++) {
				display.append(String.valueOf(addresses[i])+"\n");
			}
			display.append("클래스 유형 " + ipClass(addresses[0].getAddress()) + "\n"); //클래스 유형
			

		}catch(UnknownHostException ue) {
			String ip = name + ": 해당 호스트가 없습니다.";
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
