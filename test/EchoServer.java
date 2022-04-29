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
			//7�� ��Ʈ���� Ŭ���̾�Ʈ�� ���� ��û�� ��ٸ���.
			theServer = new ServerSocket(7);
			//Ŭ���̾�Ʈ�� ���ӿ�û�� ��ٸ��� Ŭ���̾�Ʈ�� ���ϰ� ����� ���� ���� ����
			theSocket = theServer.accept();
			
			//Ŭ���̾�Ʈ�� ������ �����͸� �д´�.
			is = theSocket.getInputStream();
			reader = new BufferedReader(new InputStreamReader(is));
			
			//Ŭ���̾�Ʈ�� �����͸� �����Ѵ�.
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
