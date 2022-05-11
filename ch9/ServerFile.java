package ch9;

import java.io.*;
import java.net.*;

public class ServerFile {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ServerSocket theServer= null;
		Socket theSocket = null;
		int port = 5000;
		InputStream is;
		BufferedReader reader;
		OutputStream os;
		BufferedWriter writer;
		String s;	
		String str;
		

		
		try {	
			//서버 소켓 생성
			theServer = new ServerSocket(port);
			System.out.println("클라이언트의 접속 요청을 기다립니다.");
			theSocket = theServer.accept();
			
			//클라이언트가 전송할 데이터를 읽을 객체
			is = theSocket.getInputStream();
			reader = new BufferedReader(new InputStreamReader(is));
			
			//클라이언트에 데이터를 전송할 객체
			os = theSocket.getOutputStream();
			writer = new BufferedWriter(new OutputStreamWriter(os));

			//클라이언트의 데이터 수신
			while((str = reader.readLine()) != null) {
				System.out.println(str);
				writer.write(str+'\r'+'\n'); //클라이언트에 데이터 재전송
				writer.flush();
			}
			theSocket.close();
		}catch(IOException e) {
			System.out.println(e);
		}finally {
			if(theSocket != null) {
				try {
					theSocket.close();
				}catch(IOException e) {}
			}
		}

	}

}