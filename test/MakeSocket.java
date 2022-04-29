package test;

import java.io.IOException;
import java.net.*;

public class MakeSocket {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Socket theSocket;
		String host = "www.ssc.ac.kr";
		int port = 7;
		
		try {
			System.out.println(host + " 호스트의 포트번호 " + port + " 와 접속하고 있습니다.");
			theSocket = new Socket(host, port);
			System.out.println("접속이 완료되었습니다.");
			
			//이 부분에서 서버와 통신을 수행할 수 있다.
			
			theSocket.close();
		}catch(UnknownHostException e) {
			System.out.println("지정된 호스트가 없습니다.");
		}catch(IOException e) {
			System.out.println("소켓이 열리지 않는다.");
		}

	}

}
