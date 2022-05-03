package TCP_IP_Server;

import java.io.*;
import java.net.*;

public class EchoServer {

	public static void main(String[] args) {
		
		ServerSocket theServer;
		Socket theSocket = null;
		InputStream is;
		BufferedReader reader;
		OutputStream os;
		BufferedWriter writer;
		String theLine;
		
		try {
			//7�� ��Ʈ���� Ŭ���̾�Ʈ�� ���� ��û�� ��ٸ��� �������� ��ü�� ����
			theServer = new ServerSocket(7);
			//��ٸ��� Ŭ���̾�Ʈ�� ���ϰ� ����� ���� ���� ����(theSocket)�� ����
			theSocket = theServer.accept();
			
			//Ŭ���̾�Ʈ�� ������ �����͸� ���� InputStream ��ü�� ����
			is = theSocket.getInputStream();
			//Ŭ���̾�Ʈ�ѿ� ������ �����͸� ���� BufferedReader ��ü�� ����
			reader = new BufferedReader(new InputStreamReader(is));
			//Ŭ���̾�Ʈ�� �����͸� ������ OutputSTream ��ü�� ����
			os = theSocket.getOutputStream();
			//Ŭ���̾�Ʈ�� �����͸� �����ϴ� BufferedWirter ��ü�� �����Ѵ�.
			writer = new BufferedWriter(new OutputStreamWriter(os));
			
			while((theLine = reader.readLine()) != null) {// Ŭ���̾�Ʈ�� �����͸� ����
				System.out.println(theLine); //���� �����͸� ȭ��� ���
				writer.write(theLine+'\r'+'\n'); //Ŭ���̾�Ʈ�� �����͸� ������
				writer.flush(); //Ŭ���̾�Ʈ�� �����͸� ������
			}
			
		}catch(UnknownHostException e) {
			System.err.println(e);
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