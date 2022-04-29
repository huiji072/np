package test;

import java.io.*;
import java.net.*;

public class EchoServer {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		ServerSocket theServer;
		Socket theSocket = null;
		InputStream is;
		BufferedReader reader;
		OutputStream os;
		BufferedWriter writer;
		String theLine;
		
		try {
			//7번 포트에서 클라이언트의 접속 요청을 기다린다.
			theServer = new ServerSocket(7);
			//클라이언트의 접속요청을 기다리고 클라이언트의 소켓과 연결된 서버 소켓 생성
			theSocket = theServer.accept();
			
			//클라이언트가 전송한 데이터를 읽는다.
			is = theSocket.getInputStream();
			reader = new BufferedReader(new InputStreamReader(is));
			
			//클라이언트에 데이터를 전송한다.
			os = theSocket.getOutputStream();
			writer = new BufferedWriter(new OutputStreamWriter(os));
			
			while((theLine = reader.readLine()) != null) {
				System.out.println(theLine);
				writer.write(theLine+"\r\n");
				writer.flush();
			}
		}catch(IOException e) {
			System.err.println(e);
		}finally {
			if(theSocket != null) {
				try {
					theSocket.close();
				}catch(IOException e) {
					System.out.println(e);
				}
			}
		}

	}

}
