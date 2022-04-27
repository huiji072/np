package ch7_8;

import java.io.*;
import java.net.*;
import java.util.*;

public class FileDownloadHTTPServer {

	public static void main(String[] args) {
//		byte[] content;
//		byte[] header;
 		int b, port;
 		byte[] data = null;
		String contenttype = "text/plain";
		
		try {

			
			try {
				port = Integer.parseInt(args[1]);
				if(port < 1 || port > 65535)
					port = 80;
			}catch(Exception e) {
				port = 80;
			}
			
			ServerSocket server = new ServerSocket(port);
			
			while(true) {
				Socket connection = null;
				FileDownload client = null;
				
				try {
					connection = server.accept(); //Ŭ���̾�Ʈ�� ������ ��ٸ���.
					client = new FileDownload(connection, data, contenttype, port);
					client.start();
				}catch(IOException e) {
					System.out.println(e);
				}
			}
		}catch(ArrayIndexOutOfBoundsException e) {
			System.out.println(e);
		}catch(Exception e) {
			System.out.println(e);
		}

	}

}

class FileDownload extends Thread{
	private byte[] content;
	private byte[] header;
	private int port;
	String contenttype;
	Socket connection;
	BufferedOutputStream out;
	BufferedInputStream in;
	int b;
	byte[] data;
		
	public FileDownload(Socket connection, byte[] data, String MIMEType, int port) throws UnsupportedEncodingException{
	      this.connection = connection;
	      this.content = data;
	      this.port = port;
	      this.contenttype = MIMEType;
		
	}
	
	public void run() {
		try {
			out = new BufferedOutputStream(connection.getOutputStream());
			in = new BufferedInputStream(connection.getInputStream());
			
			//Ŭ���̾�Ʈ���� ���� �޽��� �߿��� ù��° ���� �д´�.
			StringBuffer request = new StringBuffer(80);
			
			while(true) {
				int c = in.read();
				if(c=='\r' || c=='\n' || c==-1)
					break;
				request.append((char)c);
			}
			
			//Ŭ���̾�Ʈ�� ��û �޽����μ� [GET/HTTP/1.1] ���
			System.out.println(request.toString());
			
			String[] s = new String(request).split("/");
			System.out.println(s[0]);
			//���� try������
			if(s[0].endsWith(".html") || s[0].endsWith(".htm")) {
				contenttype = "text/html";
			}
			
			FileInputStream in = new FileInputStream(s[0]);
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			
			while((b = in.read()) != -1)
				out.write(b); //�迭 ���ۿ� �����Ѵ�.
			data = out.toByteArray(); //����Ʈ �����ͷ� ��ȯ�Ѵ�.
			
			if(request.toString().indexOf("HTTP/") != -1) {
				out.write(this.header);
			}
			
			out.write(this.content);
			out.flush();
		}catch(IOException e) {
			System.out.println(e);
		}finally {
			try {
				if(connection != null)
					connection.close();
			}catch(IOException e) {
				System.out.println(e);
			}
		}
	}
}