package test;

import java.io.IOException;
import java.net.*;

public class CreateServerSocket {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ServerSocket theServer = null;
		int port;
		if(args.length == 1) {
			port = Integer.parseInt(args[0]);
		}else {
			System.out.println("명령어 라인에서 서버의 포트번호를 입력하세요.");
			return;
		}
		
		try {
			theServer = new ServerSocket(port); //서버 소켓 객체를 생성한다.
			System.out.println(port + "에 바인드된 서버 소켓 객체를 생성하였습니다.");
			
			//이 부분에서 클라이언트의 접속을 기다리고 통신을 수행한다.
			
			theServer.close();
			
		}catch(IOException e) {
			System.out.println(e);
		}
	}

}
