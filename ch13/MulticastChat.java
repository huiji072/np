package ch13;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MulticastChat implements Runnable, WindowListener, ActionListener
{
   protected InetAddress group;
   protected int port;
   protected Frame frame;
   protected Panel panel1, panel2, panel3, panel4, panel5;
   protected Label lb1, lb2, id;
   protected TextField input, id2;
   protected TextArea output, output2;
   protected Button btn;
   protected Thread listener;
   protected MulticastSocket socket;
   protected DatagramPacket outgoing, incoming;
   String ID;
   
   public MulticastChat(InetAddress group, int port){
      this.group = group;
      this.port = port;
      initAWT();
   }
   protected void initAWT(){
      frame = new Frame("멀티캐스트 채팅 [호스트 : "+group.getHostAddress()+" , "+port+"]");
      frame.addWindowListener(this);
      
      panel1 = new Panel(new BorderLayout());
      
      lb1 = new Label("송신 메세지 내용");
      output = new TextArea();
      output.setEditable(false);
      panel1.add(lb1, BorderLayout.NORTH);
      panel1.add(output, BorderLayout.SOUTH);
      
      panel2 = new Panel(new BorderLayout());
      lb2 = new Label("수신 메세지 내용");
      output2 = new TextArea();
      output2.setEditable(false);
      panel2.add(lb2, BorderLayout.CENTER);
      panel2.add(output2, BorderLayout.SOUTH);
      
      panel3 = new Panel(new BorderLayout());
      panel3.add(panel1, BorderLayout.NORTH);
      panel3.add(panel2, BorderLayout.SOUTH);
      
      panel4 = new Panel(new BorderLayout());
      id = new Label("ID");
      id2 = new TextField();
      btn = new Button("확인");
      btn.addActionListener(this);
      panel4.add(id, BorderLayout.WEST);
      panel4.add(id2, BorderLayout.CENTER);
      panel4.add(btn, BorderLayout.EAST);
      
      panel5 = new Panel(new BorderLayout());
      input = new TextField();
      input.addActionListener(this);
      input.setEditable(false);
      panel5.add(panel4, BorderLayout.NORTH);
      panel5.add(input, BorderLayout.SOUTH);
      
      frame.setLayout(new BorderLayout());
      frame.add(panel3, "Center");
      
      frame.add(panel5, "South");
      frame.pack();
   }
   public synchronized void start() throws IOException{
      if(listener == null){
         initNet();
         listener = new Thread(this);
         listener.start(); // 스레드를 시작한다.(run() 메소드 실행)
         frame.setVisible(true);
      }
   }
   protected void initNet() throws IOException{
      socket = new MulticastSocket(port);
      socket.setTimeToLive(1);
      socket.joinGroup(group);
      outgoing = new DatagramPacket(new byte[1], 1, group, port);
      incoming = new DatagramPacket(new byte[65508], 65508);
   }
   public synchronized void stop() throws IOException{
      frame.setVisible(false);
      if(listener != null){
         listener.interrupt();
         listener = null;
         try{
            socket.leaveGroup(group);
         }finally{
            socket.close();
            System.exit(0);
         }
      }
   }
   public void windowOpened(WindowEvent we){
      input.requestFocus();
   }
   public void windowClosing(WindowEvent we){
      try{
         stop();
      }catch(IOException e){
         System.out.println(e);
      }
   }
   public void windowClosed(WindowEvent we){}
   public void windowIconified(WindowEvent we){}
   public void windowDeiconified(WindowEvent we){}
   public void windowActivated(WindowEvent we){}
   public void windowDeactivated(WindowEvent we){}
   
public void actionPerformed(ActionEvent ae){
	   
      try{
    	  if(ae.getActionCommand().equals("확인"))
    	  {
    		  if(id2 != null)
              {
        		  ID = id2.getText();
        		  
        		  if(ID.equals("") == true)
    				   return;
        		  input.setText(ID + " : ");
        		  id2.setEnabled(false);
        		  input.setEditable(true);
              }
    	  }
    	  else
    	  {
    		  byte[] utf = ae.getActionCommand().getBytes("UTF8");   
    	      outgoing.setData(utf);
    	      outgoing.setLength(utf.length);
    	      socket.send(outgoing);
    	      output.append(input.getText() + "\r\n");
    	         
    	      input.setText(ID + " : "); // 텍스트 필드의 내용을 지운다.
    	  }
      }catch(IOException e){
         System.out.println(e);
         handleIOException(e);
      }
   }
   protected synchronized void handleIOException(IOException e){
         try{
            stop();
         }catch(IOException ie){   
            System.out.println(ie);
         }
   }
   public void run(){
      try{
         while(!Thread.interrupted()){
            incoming.setLength(incoming.getData().length);
            socket.receive(incoming);
            String message = new String(incoming.getData(), 0, incoming.getLength(), "UTF8");
            output2.append(message+"\n");
         }
      }catch(IOException e){
         handleIOException(e);
      }
   }
   public static void main(String args[]) throws IOException{
      if((args.length != 1) || (args[0].indexOf(":") < 0)) // 멀티캐스트주소:포트번호 형태로 입력을 해야함.
         throw new IllegalArgumentException("잘못된 멀티캐스트 주소입니다.");
      int idx = args[0].indexOf(":");
      InetAddress group = InetAddress.getByName(args[0].substring(0, idx));
      int port = Integer.parseInt(args[0].substring(idx+1));
      MulticastChat chat = new MulticastChat(group, port);
      chat.start();
   }
}