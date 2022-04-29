package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class DayTimeClient {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Socket theSocket;
		String host;
		InputStream is;
		BufferedReader reader;
		
		if(args.length>0) {
			host = args[0]; //���� ȣ��Ʈ�� �Է¹���
		}else {
			host = "localhost"; //����ȣ��Ʈ�� ����ȣ��Ʈ�� ���
		}
		
		try {
			theSocket = new Socket(host, 13); //daytime ������ ����!!
			
			is = theSocket.getInputStream();
			reader = new BufferedReader(new InputStreamReader(is));
			
			//��¥�� �д´�.
			String theTime = reader.readLine(); 
			System.out.println("ȣ��Ʈ�� �ð��� " + theTime + " �̴�.");
		}catch(UnknownHostException e) {
			System.err.println(args[0] + " ȣ��Ʈ�� ã�� �� �����ϴ�.");
		}catch(IOException e) {
			System.err.println(e);
		}

	}

}
