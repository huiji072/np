package test;

import java.io.*;
import java.net.*;

public class accountServer {

	public static void main(String[] args) {
		Socket connection = null;
		BufferedWriter writer;
		BufferedReader reader;
		ServerSocket server;
		
		try {
			server = new ServerSocket(5000); //5000번 포트에 접속
			while(true) {
				try {
					connection = server.accept();
					reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
					writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));

					System.out.println("test!!!");
					String clientdata = reader.readLine(); //클라이언트 값을 읽어옴.
					System.out.println(clientdata + "\r\n"); //출력
					
				}catch(IOException e) {
					e.printStackTrace();
				}finally {
					try {
						if(connection != null) connection.close();
					}catch(IOException e){
						e.printStackTrace();
					}
				}
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

}
