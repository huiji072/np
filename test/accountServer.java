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
			server = new ServerSocket(5000); //5000�� ��Ʈ�� ����
			while(true) {
				try {
					connection = server.accept();
					reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
					writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));

					System.out.println("test!!!");
					String clientdata = reader.readLine(); //Ŭ���̾�Ʈ ���� �о��.
					System.out.println(clientdata + "\r\n"); //���
					
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
