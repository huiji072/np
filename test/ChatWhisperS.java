//package test;
//
//import java.io.*;
//import java.net.*;
//import java.util.*;
//import java.util.List;
//import java.awt.*;
//import java.awt.event.*;
//
//public class ChatWhisperS extends Frame {
//	TextArea display;
//	Label info;
//	List<ServerThread> list;
//    Hashtable hash;
//    public ServerThread SThread;
//    
//    public ChatWhisperS() {
//        super("서버");
//        info = new Label();
//        add(info, BorderLayout.CENTER);
//        display = new TextArea("", 0, 0, TextArea.SCROLLBARS_VERTICAL_ONLY);
//        display.setEditable(false);
//        add(display, BorderLayout.SOUTH);
//        addWindowListener(new WinListener());
//        setSize(300,250);
//        setVisible(true);
//     }
//    
//    public void runServer() {
//    	ServerSocket server;
//    	Socket sock;
//    	ServerThread SThread;
//    	
//    	try {
//    		server = new ServerSocket(5000, 100); //5000번 포트에 100개 연결가능
//    		hash = new Hashtable();
//    		list = new ArrayList<ServerThread>();
//    		
//    		try {
//    			while(true) {
//    				sock = server.accept();
//    				SThread = new ServerThread(this, sock, display, info);
//    				SThread.start();
//    				info.setText(sock.getInetAddress().getHostName() +
//    						"서버는 클라이언트와 연결됨");
//    			}
//    		}catch(IOException io) {
//    			server.close();
//    			io.printStackTrace();
//    		}
//    	}catch(IOException io) {
//    		io.printStackTrace();
//    	}
//    	
//    }
//
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		ChatWhisperS s = new ChatWhisperS();
//	    s.runServer();
//	}
//	
//	class WinListener extends WindowAdapter {
//	      public void windowClosing(WindowEvent e) {
//	         System.exit(0);
//	      }
//	   }
//
//}
//
//class ServerThread extends Thread{
//	Socket sock;
//	BufferedWriter output;
//	BufferedReader input;
//	TextArea display;
//	Label info;
//	TextField text;
//	String clientdata;
//	String serverdata = "";
//	ChatWhisperS cs;
//	
//	private static final String SEPARATOR = "|";
//	private static final int REQ_LOGON = 1001;
//	private static final int REQ_SENDWORDS = 1021;
//	private static final int REQ_WISPERSEND = 1022;
//	
//	public ServerThread(ChatWhisperS c, Socket s, TextArea ta, Label l) {
//		 sock = s;
//	     display = ta;
//	     info = l;
//	     cs = c;
//	      try {
//	          input = new BufferedReader(new InputStreamReader(sock.getInputStream()));
//	          output = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
//	       } catch(IOException ioe) {
//	          ioe.printStackTrace();
//	       }
//	       
//	}
//	
//	public void run() {
//		try {
//			cs.list.add(this);
//			while((clientdata = input.readLine()) != null) { //클라이언트가 보낸 값은 clientdata에 저장
//				StringTokenizer st = new StringTokenizer(clientdata, SEPARATOR);
//				int command = Integer.parseInt(st.nextToken()); //ID
//				int Lcnt = cs.list.size();
//				switch(command) {
//					case REQ_LOGON : {
//						String ID = st.nextToken();
//						display.append("클라이언트가 " + ID + "(으)로 로그인 하였습니다.\r\n");
//						cs.hash.put(ID, this); //해쉬테이블에 아이디와 스레드를 저장
//						break;
//					}
//					
//					case REQ_SENDWORDS : {
//						String ID = st.nextToken();
//						String message = st.nextToken();
//						display.append(ID + " : " + message + "\r\n"); //클라이언트에서 보낸 값 서버에 출력
//						for(int i = 0 ; i < Lcnt ; i++) {
//							ServerThread SThread = (ServerThread)cs.list.get(i);
//							SThread.output.write(ID + " : " + message + "\r\n");
//							SThread.output.flush();
//						}
//						break;
//					}
//					
//					case REQ_WISPERSEND : {
//		                  String ID = st.nextToken();
//		                  String WID = st.nextToken();
//		                  String message = st.nextToken();
//		                  display.append(ID + " -> " + WID + " : " + message + "\r\n");
//		                  ServerThread SThread = (ServerThread)cs.hash.get(ID);
//		        // 해쉬테이블에서 귓속말 메시지를 전송한 클라이언트의 스레드를 구함
//		                  SThread.output.write(ID + " -> " + WID + " : " + message + "\r\n");
//		        // 귓속말 메시지를 전송한 클라이언트에 전송함
//		                  SThread.output.flush();
//		                  SThread = (ServerThread)cs.hash.get(WID);
//		        // 해쉬테이블에서 귓속말 메시지를 수신할 클라이언트의 스레드를 구함
//		                  SThread.output.write(ID + " : " + message + "\r\n");
//		        // 귓속말 메시지를 수신할 클라이언트에 전송함
//		                  SThread.output.flush();
//		                  break;
//		               }
//		            }
//		         }
//		      } catch(IOException e) {
//		         e.printStackTrace();
//		      }
//		      cs.list.remove(this);
//		      try{
//		         sock.close();
//		      }catch(IOException ea){
//		         ea.printStackTrace();
//		      }
//		   }
//		}