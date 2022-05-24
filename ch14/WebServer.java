package ch14;

import java.io.*;
import java.net.*;

public class WebServer {
	
	public WebServer(byte[] data) {
		System.out.println("Webserver Started");
		
		try(ServerSocket serverSocket = new ServerSocket(5000)){
			while(true) {
				System.out.println("Waiting for client request");
				Socket remote = serverSocket.accept();
				System.out.println("Connection made");
				new Thread(new ClientHandler(remote, data)).start();
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		int b, port;
		byte[] data = null;
		
		try {
			FileInputStream in = new FileInputStream(args[0]);
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			while((b = in.read()) != -1) {
				out.write(b); //배열 버퍼에 저장
			}
			data = out.toByteArray(); //바이트 데이터로 변환
		}catch(IOException e) {
			System.err.println("FileNotFound");
		}
		new WebServer(data);
	}

}
