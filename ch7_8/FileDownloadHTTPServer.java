package ch7_8;

import java.io.*;
import java.net.*;
import java.util.*;

public class FileDownloadHTTPServer {

	public static void main(String[] args) {
 		int port;
		String contenttype = "text/plain";
		
		try {			
			try {
				port = Integer.parseInt(args[0]);
				if(port < 1 || port > 65535)
					port = 30000;
			}catch(Exception e) {
				port = 30000;
			}
			
			ServerSocket server = new ServerSocket(port);
			
			while(true) {
				Socket connection = null;
				FileDownload client = null;
				
				try {
					connection = server.accept(); //Ŭ���̾�Ʈ�� ������ ��ٸ���.
					client = new FileDownload(connection, contenttype);
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
	String contenttype, stringHeader;
	Socket connection;
	BufferedOutputStream out;
	BufferedInputStream in;
	int b;
	byte[] data;
		
	public FileDownload(Socket connection, String MIMEType) throws UnsupportedEncodingException{
	      this.connection = connection;
//	      this.content = data;
//	      this.port = port;
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
			
			//args ��������� ��
			String[] args = new String(request).split("/");
			args = args[1].split(" ");
			System.out.println("�ּ� : " + args[0]);
			//���� try������
			if(args[0].endsWith(".html") || args[0].endsWith(".htm")) {
				contenttype = "text/html";
				
			}
			
			FileInputStream in = new FileInputStream(args[0]);
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			
			while((b = in.read()) != -1)
				out.write(b); //�迭 ���ۿ� �����Ѵ�.
			data = out.toByteArray(); //����Ʈ �����ͷ� ��ȯ�Ѵ�.
			this.content = out.toByteArray();
			this.stringHeader = "HTTP 1.0 200 OK\r\n" + "Server: OneFile 1.0\r\n" + "Content-length: " + this.content.length + "\r\n" + "Content-type: " + this.contenttype + "\r\n\r\n";
			this.header = this.stringHeader.getBytes("ASCII");
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