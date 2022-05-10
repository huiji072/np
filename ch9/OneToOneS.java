//package ch9;
//
//import java.awt.*;
//import java.awt.event.*;
//import java.io.*;
//import java.net.*;
//import java.util.*;
//import java.util.List;
//
//public class OneToOneS extends Frame implements ActionListener{
//   
//   TextArea display;
//   TextField text;
//   Label lword;
//   Socket connection;
//   BufferedWriter output;
//   BufferedReader input;
//   String clienddata="";
//   String serverdata = "";
//   ServerSocket server;
//   List <ServerThread> list;
//   
//   public OneToOneS() {
//      super("서버");
//      display = new TextArea("", 0, 0, TextArea.SCROLLBARS_VERTICAL_ONLY);
//      display.setEditable(false);
//      add(display, BorderLayout.CENTER);
//      
//      
//      Panel pword = new Panel(new BorderLayout());
//      lword = new Label("대화말");
//      text = new TextField(30);
//      text.addActionListener(this);
//      pword.add(lword, BorderLayout.WEST);
//      pword.add(text, BorderLayout.CENTER);
//      add(pword, BorderLayout.SOUTH);
//      
//      addWindowListener(new WinListener());
//      setSize(300, 200);
//      setVisible(true);
//   }
//   
//   public void runServer() {
//      ServerSocket server;
//      try {
////         list = new ArrayList<ServerThread>();
//         server = new ServerSocket(5000, 100);
//         try {
//            while(true) {
//               ServerThread SThread = null;
//               connection = server.accept();//접속요청이 올 때까지 기다린다.
//               SThread = new ServerThread(connection, display);
//               SThread.start();
//               OutputStream os = connection.getOutputStream();
//               OutputStreamWriter osw = new OutputStreamWriter(os); //클라이언트에 대화말 전송
//               output = new BufferedWriter(osw);
//            }
//         }catch(IOException e) {
//            server.close();
//            server=null;
//         }
//      }catch(IOException e) {
//         e.printStackTrace();
//      }
//   }
//   
//   public void actionPerformed(ActionEvent ae) {
//
//      if(connection == null) {
//         display.append("\n연결된 클라이언트가 없습니다.");
//         text.setText("");
//         return;
//      }
//      
//      serverdata = text.getText();
//   
//      try {
//         display.append("\n서버 : " + serverdata); //o
//         output.write(serverdata + "\r\n");
//         System.out.println(serverdata);
//         output.flush();
//         text.setText("");
//         
//         if(serverdata.equals("quit")){
//            connection.close();   
//            connection=null;
//         }
//      }catch(IOException e) {
//         e.printStackTrace();
//      }
//   }
//
//   public static void main(String[] args) {
//      // TODO Auto-generated method stub
//      OneToOneS s = new OneToOneS();
//      s.runServer();
//
//   }
//   
//   class WinListener extends WindowAdapter{
//      public void windowClosing(WindowEvent e) {
//         System.exit(0);
//      }
//   }
//
//}
//
//class ServerThread extends Thread{
//   BufferedWriter output;
//   BufferedReader input;
//   Socket socket;
//   OneToOneS sv;
//   TextArea display;
//   
//   ServerThread(Socket connection, TextArea ta){
//      socket = connection;
//      display = ta;
//   }
//
//   public void run() {
//      try {
//         String clientIp = socket.getInetAddress().getHostAddress();
//         display.append("\n 클라이언트 " + clientIp + "가 연결되었습니다.\n");
//         
//         InputStream is = socket.getInputStream();
//         InputStreamReader isr = new InputStreamReader(is);
//         input = new BufferedReader(isr); //서버가 전송한 대화말 수신
//         OutputStream os = socket.getOutputStream();
//         OutputStreamWriter osw = new OutputStreamWriter(os); //클라이언트에 대화말 전송
//         output = new BufferedWriter(osw);
//         
//         while(true) {
//            String clientdata = input.readLine();
//            if(clientdata == null) {
//               display.append("\n 클라이언트와의 접속이 중단되었습니다.");
//               output.flush();
//               break;
//            }else {
//               display.append("\n클라이언트 메시지 :  " + clientdata);
//               output.flush();
//            }
//         }
//         socket.close();
//         socket=null;
//      }catch(IOException e) {
//         System.out.println(e);
//      }
//
//   }
//}