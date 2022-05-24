package ch14;

import java.io.*;
import java.net.*;
import java.util.StringTokenizer;

public class ClientHandler implements Runnable{
	
	private final Socket socket;
	private byte[] data;
	
	public ClientHandler(Socket socket, byte[] data) {
		this.socket = socket;
		this.data = data;
	}
	
	@Override
	public void run() {
		System.out.println("\nClientHandler Started for " + this.socket);
		handleRequest(this.socket);
		System.out.println("ClientHandler Terminated for " + this.socket + "\n");
	}
	
	public void handleRequest(Socket socket) {
		try(BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));){
			String headerLine = in.readLine();
			StringTokenizer tokenizer = new StringTokenizer(headerLine);
			String httpMethod = tokenizer.nextToken();
			
			if(httpMethod.equals("GET")) {
				System.out.println("Get method processed");
				sendResponse(socket, 200, data);
			}else {
				System.out.println("The HTTP method is not recognized");
				sendResponse(socket, 405, "Method Not Allowed".getBytes());
			}
		}catch(Exception e) {
			e.printStackTrace();
		}				
	}
	
	public void sendResponse(Socket socket, int statusCode, byte[] data) {
		String statusLine;
		String serverHeader = "Server: WebServer\r\n";
		String contentTypeHeader = "Contend-Type: text/html\r\n";
		
		try(DataOutputStream out = new DataOutputStream(socket.getOutputStream());){
			if(statusCode == 200) {
				statusLine = "HTTP/1.0 200 OK" + "\r\n";
				String contentLengthHeader = "Content-Length: " + data.length + "\r\n";
				
				out.writeBytes(statusLine);
				out.writeBytes(serverHeader);
				out.writeBytes(contentTypeHeader);
				out.writeBytes(contentLengthHeader);
				out.writeBytes("\r\n");
				out.write(data);
			}else if(statusCode == 405) {
				statusLine = "HTTP/1.0 405 Method Not Allowed" + "\r\n";
				out.writeBytes(statusLine);
				out.writeBytes("\r\n");
			}else {
				statusLine = "HTTp/1.0 404 Not Found" + "\r\n";
				out.writeBytes(statusLine);
				out.writeBytes("\r\n");
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
}
