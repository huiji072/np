package test;

import java.io.*;
import java.net.*;

public class FileDownloadHTTPServer {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		byte[] content;
		byte[] header;
		int b, port;
		byte[] data;
		String contenttype = "text/plain";
		
		try {
			if(args[0].endsWith(".html") || args[0].endsWith(".htm")) {
				contenttype = "text/html";
			}
			
			//이걸 이제 웹페이지에서 받아와야 함
			FileInputStream in = new FileInputStream(args[0]);
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			
			//파일 읽음
			while((b=in.read()) != -1) {
				out.write(b);
			}
			data = out.toByteArray();
			
			try {
				port = Integer.parseInt(args[1]);
				
				if(port<1 || port>65535)
					port = 80;
			}catch(Exception e) {
				port = 80;
			}
			
			ServerSocket server = new ServerSocket(port);
			
			while(true) {
				Socket connection = null;
				FileDownload client = null;
				
				try {
					//클라이언트의 접속을 기다린다.
					connection = server.accept();
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
	Socket connection;
	BufferedOutputStream out;
	BufferedInputStream in;
	
	public FileDownload(Socket connection, String data, String MIMEType, int port) throws UnsupportedEncodingException{
		this(connection, data.getBytes("ASCII"), MIMEType, port);
	}
	
	public FileDownload(Socket connection, byte[] data, String MIMEType, int port) throws UnsupportedEncodingException{
		this.connection = connection;
		this.content = data;
		this.port = port;
		String header = "HTTP 1.0 200 OK\r\n" + "Server: OneFile 1.0\r\n"+"Content-length:"
		+ this.content.length +"\r\n"+"Content-type: " + "MIMEType" + "\r\n\r\n";
		this.header = header.getBytes("ASCII");
	}
	
	public void run() {
		try {
			out = new BufferedOutputStream(connection.getOutputStream());
			in = new BufferedInputStream(connection.getInputStream());
			
			//클라이언트에서 보낸 메시지 중에서 첫번째 줄을 읽는다.
			StringBuffer request = new StringBuffer(80);
			
			while(true) {
				int c = in.read();
				if(c=='\r' || c=='\n' || c==-1)
					break;
				request.append((char)c);
			}
			//클라이언트의 요청 메시지로서 [GET/HTTP/1.1]을 출력한다.
			System.out.println(request.toString());
			if(request.toString().indexOf("HTTP") != -1 ) {
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
