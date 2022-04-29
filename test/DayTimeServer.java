package test;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class DayTimeServer {
	
	public final static int daytimeport = 13;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		ServerSocket theServer;
		Socket theSocket = null;
		BufferedWriter writer;
		
		try {
			//13번 포트에서 클라이언트의 접속요청을 기다리는 서버소켓 객체를 생성한다.
			theServer = new ServerSocket(daytimeport); 
			
			while(true) {
				try {
					//클라이언트의 접속요청을 기다리고 클라이언트의 소켓과 연결된 서버측의 소캣을 생성
					theSocket = theServer.accept();
					
					//클라이언트에 데이터를 전송할 객체 생성
					OutputStream os = theSocket.getOutputStream();
					writer = new BufferedWriter(new OutputStreamWriter(os));
					
					//날짜를 구한다.
					Date now = new Date();
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
