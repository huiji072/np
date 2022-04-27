package TCP_IP_Server;

import java.io.*;
import java.net.*;
import java.util.Date;

public class DayTimeServer {
	
	public final static int daytimeport = 13;

	public static void main(String[] args) {

		ServerSocket theServer;
		Socket theSocket = null;
		BufferedWriter writer;
		
		try {
			//13�� ��Ʈ���� Ŭ���̾�Ʈ�� ���� ��û�� ��ٸ��� ���������� �����Ѵ�.
			theServer = new ServerSocket(daytimeport);
			
			while(true) {
				try {
					/*Ŭ���̾�Ʈ�� ���ӿ�û�� ��ٸ��� 
					Ŭ���̾�Ʈ�� ���ϰ� ����� ���� ���� ����(theSocket)�� �����Ѵ�. */
					theSocket = theServer.accept();
					//Ŭ���̾�Ʈ�� �����͸� ������ OutputStream ��ü�� �����Ѵ�.
					OutputStream os = theSocket.getOutputStream();
					//Ŭ���̾�Ʈ�� �����͸� �����ϴ� BufferedWriter ��ü�� �����Ѵ�.
					writer = new BufferedWriter(new OutputStreamWriter(os));
					
					//��¥�� ���Ѵ�.
					Date now = new Date();
					//��¥�� �����Ѵ�.
					writer.write(now.toString()+"\r\n");
					writer.flush();
					theSocket.close();
				}catch(IOException e) {
					System.out.println(e);
				}finally {
					try {
						if(theSocket != null) theSocket.close();
					}catch(IOException e) {
						System.out.println(e);
					}
				}
			}
			
		}catch(IOException e) {
			System.out.println(e);
		}

	}

}
