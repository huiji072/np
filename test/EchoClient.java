package test;

import java.io.*;
import java.net.*;

public class EchoClient {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Socket theSocket = null;
		String host;
		InputStream is;
		BufferedReader reader, userInput;
		OutputStream os;
		BufferedWriter writer;
		String theLine;
		
		if(args.length > 0) {
			host = args[0];
		}else {
			host = "localhost";
		}
		
		try {
			//echoServer에 접속
			theSocket = new Socket(host, 7);
			
			is = theSocket.getInputStream();
			reader = new BufferedReader(new InputStreamReader(is));
			
			//보낼 데이터
			userInput = new BufferedReader(new InputStreamReader(System.in));
			
			os = theSocket.getOutputStream();
			writer = new BufferedWriter(new OutputStreamWriter(os));
			
			System.out.println("전송할 문장을 입력하십시오 ");
			
			while(true) {
				theLine = userInput.readLine(); //데이터 입력받음
				if(theLine.equals("quit")) break;
				
				//server로 데이터 전송
				writer.write(theLine+"\r\n");
				writer.flush();
				System.out.println(reader.readLine()); //다시 수신, 화면 출력
			}
		}catch(UnknownHostException e) {
			System.err.println(args[0] + " 호스트를 찾을 수 없습니다.");
		}catch(IOException e) {
			System.err.println(e);
		}finally {
			try {
				theSocket.close();
			}catch(IOException e) {
				System.out.println(e);
			}
		}

	}

}
