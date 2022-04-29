package test;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class DayTimeServer {
	
	public final static int daytimeport = 13;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		ServerSocket theServer;
		Socket theSocket = null;
		BufferedWriter writer;
		
		try {
			//13�� ��Ʈ���� Ŭ���̾�Ʈ�� ���ӿ�û�� ��ٸ��� �������� ��ü�� �����Ѵ�.
			theServer = new ServerSocket(daytimeport); 
			
			while(true) {
				try {
					//Ŭ���̾�Ʈ�� ���ӿ�û�� ��ٸ��� Ŭ���̾�Ʈ�� ���ϰ� ����� �������� ��Ĺ�� ����
					theSocket = theServer.accept();
					
					//Ŭ���̾�Ʈ�� �����͸� ������ ��ü ����
					OutputStream os = theSocket.getOutputStream();
					writer = new BufferedWriter(new OutputStreamWriter(os));
					
					//��¥�� ���Ѵ�.
					Date now = new Date();
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
