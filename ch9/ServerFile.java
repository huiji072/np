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
			//���� ���� ����
			theServer = new ServerSocket(port);
			System.out.println("Ŭ���̾�Ʈ�� ���� ��û�� ��ٸ��ϴ�.");
			theSocket = theServer.accept();
			
			//Ŭ���̾�Ʈ�� ������ �����͸� ���� ��ü
			is = theSocket.getInputStream();
			reader = new BufferedReader(new InputStreamReader(is));
			
			//Ŭ���̾�Ʈ�� �����͸� ������ ��ü
			os = theSocket.getOutputStream();
			writer = new BufferedWriter(new OutputStreamWriter(os));

			//Ŭ���̾�Ʈ�� ������ ����
			while((str = reader.readLine()) != null) {
				System.out.println(str);
				writer.write(str+'\r'+'\n'); //Ŭ���̾�Ʈ�� ������ ������
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