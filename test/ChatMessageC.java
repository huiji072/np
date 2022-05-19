package test;

import java.io.*;
import java.net.*;
import java.util.StringTokenizer;
import java.awt.*;
import java.awt.event.*;

public class ChatMessageC extends Frame implements ActionListener, KeyListener {
	   protected InetAddress group;
	   protected int port;
   TextArea display;
   TextField wtext, ltext;
   Label mlbl, wlbl, loglbl;
   BufferedWriter output;
   BufferedReader input;
   Socket client;
   StringBuffer clientdata;
   String serverdata;
   String ID;
   protected MulticastSocket socket;
   protected DatagramPacket outgoing, incoming;
   
   private static final String SEPARATOR = "|";
   private static final int REQ_LOGON = 1001;
   private static final int REQ_SENDWORDS = 1021;
	
   public ChatMessageC(InetAddress group, int port){
      super("클라이언트");
      this.group = group;
      this.port = port;
      initAWT();
   }
   
   public void initAWT() {

	      mlbl = new Label("채팅 상태를 보여줍니다.");
	      add(mlbl, BorderLayout.NORTH);

	      display = new TextArea("", 0, 0, TextArea.SCROLLBARS_VERTICAL_ONLY);
	      display.setEditable(false);
	      add(display, BorderLayout.CENTER);

	      Panel ptotal = new Panel(new BorderLayout());
	 
	      Panel pword = new Panel(new BorderLayout());
	      wlbl = new Label("대화말");
	      wtext = new TextField(26); //전송할 데이터를 입력하는 필드
	      wtext.addKeyListener(this); //입력된 데이터를 송신하기 위한 이벤트 연결
	      pword.add(wlbl, BorderLayout.WEST);
	      pword.add(wtext, BorderLayout.EAST);
	      ptotal.add(pword, BorderLayout.CENTER);

	      Panel plabel = new Panel(new BorderLayout());
	      loglbl = new Label("로그온");
	      ltext = new TextField(26); //전송할 데이터를 입력하는 필드
	      ltext.addActionListener(this); //입력된 데이터를 송신하기 위한 이벤트 연결
	      plabel.add(loglbl, BorderLayout.WEST);
	      plabel.add(ltext, BorderLayout.EAST);
	      ptotal.add(plabel, BorderLayout.SOUTH);

	      add(ptotal, BorderLayout.SOUTH);

	      addWindowListener(new WinListener());
	      setSize(300,250);
	      setVisible(true);
   }
   
   protected void initNet() throws IOException{
	   socket = new MulticastSocket(port);
       socket.setTimeToLive(1);
       socket.joinGroup(group);
	   outgoing = new DatagramPacket(new byte[1], 1, group, port);
	   incoming = new DatagramPacket(new byte[65508], 65508);
   }
	
   public void runClient() {
      try {
//         client = new Socket(InetAddress.getLocalHost(), 5000);
    	  initNet();
         mlbl.setText("멀티캐스트 채팅 [호스트 : "+group.getHostAddress()+" , "+port+"]");
//         input = new BufferedReader(new InputStreamReader(client.getInputStream()));
//         output = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
         clientdata = new StringBuffer(2048);
         mlbl.setText("접속 완료 사용할 아이디를 입력하세요.");
         while(true) {
//            serverdata = input.readLine();
        	 incoming.setLength(incoming.getData().length);
        	  socket.receive(incoming);
        	  String message = new String(incoming.getData(), 0, incoming.getLength(), "UTF8");
	            display.append(message+"\n");
//            display.append(serverdata+"\r\n");
         }
      } catch(IOException e) {
         e.printStackTrace();
      }
   }
   
   public synchronized void stop() throws IOException{

	         try{
	            socket.leaveGroup(group); //그룹 탈퇴
	         }finally{
	            socket.close();
	            System.exit(0);
	         }
	      
	   }
		
   //datagramPacket으로 값 전송
   public void actionPerformed(ActionEvent ae){
      if(ID == null) {
         ID = ltext.getText();
         mlbl.setText(ID + "(으)로 로그인 하였습니다.");
         try {
            clientdata.setLength(0);
            clientdata.append(REQ_LOGON);
            clientdata.append(SEPARATOR);
            clientdata.append(ID);
//            output.write(clientdata.toString()+"\r\n");
            byte[] utf = clientdata.toString().getBytes("UTF8");
            outgoing.setData(utf);
            outgoing.setLength(utf.length);
            socket.send(outgoing);
//            output.flush();
            ltext.setVisible(false);
         } catch(Exception e) {
            e.printStackTrace();
//            handleIOException(e);
         }
      }
   }
   protected synchronized void handleIOException(IOException e){
       try{
          stop();
       }catch(IOException ie){  
          System.out.println(ie);
       }
 }
   
   public static void main(String args[]) throws IOException {
	   if((args.length != 1) || (args[0].indexOf(":") < 0)) // 멀티캐스트주소:포트번호 형태로 입력을 해야함.
	         throw new IllegalArgumentException("잘못된 멀티캐스트 주소입니다.");
	      int idx = args[0].indexOf(":");
	      InetAddress group = InetAddress.getByName(args[0].substring(0, idx));
	      int port = Integer.parseInt(args[0].substring(idx+1));
      ChatMessageC c = new ChatMessageC(group, port);
      c.runClient();
   }
		
   class WinListener extends WindowAdapter {
      public void windowClosing(WindowEvent e){
         System.exit(0);
         try {
			stop();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
      }
   }

   public void keyPressed(KeyEvent ke) {
      if(ke.getKeyChar() == KeyEvent.VK_ENTER) {
         String message = new String();
         message = wtext.getText();
         if (ID == null) {
            mlbl.setText("다시 로그인 하세요!!!");
            wtext.setText("");
         } else {
            try {
               clientdata.setLength(0);
               clientdata.append(REQ_SENDWORDS);
               clientdata.append(SEPARATOR);
               clientdata.append(ID);
               clientdata.append(SEPARATOR);
               clientdata.append(message);
//               output.write(clientdata.toString()+"\r\n");
//               output.flush();
               System.out.println("client : " + clientdata);
               byte[] utf = clientdata.toString().getBytes("UTF8");
               outgoing.setData(utf);
               outgoing.setLength(utf.length);
               socket.send(outgoing);
               wtext.setText("");
//               socket.close();
            } catch (IOException e) {
               e.printStackTrace();
            }
         }
      }
   }

   public void keyReleased(KeyEvent ke) {
   }

   public void keyTyped(KeyEvent ke) {
   }
}