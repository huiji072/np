package TCP_IP_Server;

import java.io.IOException;
import java.net.*;

public class LookForServerPorts {

	public static void main(String[] args) {
		ServerSocket theServer = null;
		
		for(int i = 1024 ; i < 65535 ; i++) {
			
			try {
				theServer = new ServerSocket(i); //서버 소켓 객체를 생성한다.
				theServer.close();
			}catch(IOException e) {
				System.out.println(i + " 번째 포트는 특정서버가 사용합니다.");
			}
			
		}

	}

}
