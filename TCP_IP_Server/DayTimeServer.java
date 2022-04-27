package TCP_IP_Server;

import java.io.*;
import java.net.*;
import java.util.Date;

public class DayTimeServer {
	
	public final static int daytimeport = 13;

	public static void main(String[] args) {

		ServerSocket theServer;
		Socket theSocket = null;
		BufferedWriter writer;
		
		try {
			//13번 포트에서 클라이언트의 접속 요청을 기다리는 서버소켓을 생성한다.
			theServer = new ServerSocket(daytimeport);
			
			while(true) {
				try {
					/*클라이언트의 접속요청을 기다리고 
					클라이언트의 소켓과 연결된 서버 측의 소켓(theSocket)을 생성한다. */
					theSocket = theServer.accept();
					//클라이언트에 데이터를 전송할 OutputStream 객체를 생성한다.
					OutputStream os = theSocket.getOutputStream();
					//클라이언트에 데이터를 전송하는 BufferedWriter 객체를 생성한다.
					writer = new BufferedWriter(new OutputStreamWriter(os));
					
					//날짜를 구한다.
					Date now = new Date();
					//날짜를 전송한다.
					writer.write(now.toString()+"\r\n");
					writer.flush();
					theSocket.close();
				}catch(IOException e) {
					System.out.println(e);
				}finally {
					try {
						if(theSocket != null) theSocket.close();
					}catch(IOException e) {
						System.out.println(e);
					}
				}
			}
			
		}catch(IOException e) {
			System.out.println(e);
		}

	}

}
