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
      super("Ŭ���̾�Ʈ");
      this.group = group;
      this.port = port;
      initAWT();
   }
   
   public void initAWT() {

	      mlbl = new Label("ä�� ���¸� �����ݴϴ�.");
	      add(mlbl, BorderLayout.NORTH);

	      display = new TextArea("", 0, 0, TextArea.SCROLLBARS_VERTICAL_ONLY);
	      display.setEditable(false);
	      add(display, BorderLayout.CENTER);

	      Panel ptotal = new Panel(new BorderLayout());
	 
	      Panel pword = new Panel(new BorderLayout());
	      wlbl = new Label("��ȭ��");
	      wtext = new TextField(26); //������ �����͸� �Է��ϴ� �ʵ�
	      wtext.addKeyListener(this); //�Էµ� �����͸� �۽��ϱ� ���� �̺�Ʈ ����
	      pword.add(wlbl, BorderLayout.WEST);
	      pword.add(wtext, BorderLayout.EAST);
	      ptotal.add(pword, BorderLayout.CENTER);

	      Panel plabel = new Panel(new BorderLayout());
	      loglbl = new Label("�α׿�");
	      ltext = new TextField(26); //������ �����͸� �Է��ϴ� �ʵ�
	      ltext.addActionListener(this); //�Էµ� �����͸� �۽��ϱ� ���� �̺�Ʈ ����
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
         mlbl.setText("��Ƽĳ��Ʈ ä�� [ȣ��Ʈ : "+group.getHostAddress()+" , "+port+"]");
//         input = new BufferedReader(new InputStreamReader(client.getInputStream()));
//         output = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
         clientdata = new StringBuffer(2048);
         mlbl.setText("���� �Ϸ� ����� ���̵� �Է��ϼ���.");
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
	            socket.leaveGroup(group); //�׷� Ż��
	         }finally{
	            socket.close();
	            System.exit(0);
	         }
	      
	   }
		
   //datagramPacket���� �� ����
   public void actionPerformed(ActionEvent ae){
      if(ID == null) {
         ID = ltext.getText();
         mlbl.setText(ID + "(��)�� �α��� �Ͽ����ϴ�.");
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
	   if((args.length != 1) || (args[0].indexOf(":") < 0)) // ��Ƽĳ��Ʈ�ּ�:��Ʈ��ȣ ���·� �Է��� �ؾ���.
	         throw new IllegalArgumentException("�߸��� ��Ƽĳ��Ʈ �ּ��Դϴ�.");
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
            mlbl.setText("�ٽ� �α��� �ϼ���!!!");
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