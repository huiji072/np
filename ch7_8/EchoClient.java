package ch7_8;

import java.io.*;
import java.net.*;

public class EchoClient {

	public static void main(String[] args) {

		Socket theSocket = null;
		String host;
		InputStream is;
		BufferedReader reader, userInput;
		OutputStream os;
		BufferedWriter writer;
		String theLine;
		
		if(args.length > 0) {
			host = args[0]; //원격 호스트를 입력받음
		}else {
			host = "localhost"; //로컬호스트를 원격호스트로 사용
		}
		
		try {
			theSocket = new Socket(host, 7); //echo 서버에 접속
			is = theSocket.getInputStream();
			reader = new BufferedReader(new InputStreamReader(is));
			userInput = new BufferedReader(new InputStreamReader(System.in));
			os = theSocket.getOutputStream();
			writer = new BufferedWriter(new OutputStreamWriter(os));
			System.out.println("전송할 문장을 입력하십시오.");
			
			while(true) {
				theLine = userInput.readLine(); //데이터를 입력
				if(theLine.equals("quit")) break; //프로그램 종료
				writer.write(theLine + '\r' + '\n');
				writer.flush(); // 서버에 데이터 전송
				System.out.println(reader.readLine()); //다시 수신해서 화면에 출력
			}
		}catch(UnknownHostException e) {
			System.err.println(args[0] + "호스트를 찾을 수 없습니다. ");
		}catch(IOException e) {
			System.err.println(e);
		}finally {
			try {
				theSocket.close(); //소켓을 닫는다.
			}catch(IOException e) {
				System.out.println(e);
			}
		}

	}

}