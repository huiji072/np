package ch13;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class Receiver  implements Runnable, WindowListener, ActionListener
{
	   protected InetAddress group;
	   protected int port;
	   protected Frame frame;
	   protected TextField input;
	   protected TextArea output;
	   protected Thread listener;
	   protected MulticastSocket socket;
	   protected DatagramPacket outgoing, incoming;
	   public Receiver(InetAddress group, int port){
	      this.group = group;
	      this.port = port;
	      initAWT();
	   }
	   protected void initAWT(){
	      frame = new Frame("��Ƽĳ��Ʈ ä�� Ŭ���̾�Ʈ[ȣ��Ʈ : "+group.getHostAddress()+" , "+port+"]");
	      frame.addWindowListener(this);
	      output = new TextArea();
	      output.setEditable(false);
	      input = new TextField();
	      input.addActionListener(this);
	      frame.setLayout(new BorderLayout());
	      frame.add(output, "Center");
	      frame.add(input, "South");
	      frame.pack();
	   }
	   public synchronized void start() throws IOException{
	      if(listener == null){
	         initNet();
	         listener = new Thread(this);
	         listener.start(); // �����带 �����Ѵ�.(run() �޼ҵ� ����)
	         frame.setVisible(true);
	      }
	   }
	   @SuppressWarnings("deprecation")
	protected void initNet() throws IOException{
	      socket = new MulticastSocket(port);
	      socket.setTimeToLive(1);
	      socket.joinGroup(group); //�׷� ����
	      outgoing = new DatagramPacket(new byte[1], 1, group, port);
	      incoming = new DatagramPacket(new byte[65508], 65508);
	   }
	   public synchronized void stop() throws IOException{
	      frame.setVisible(false);
	      if(listener != null){
	         listener.interrupt();
	         listener = null;
	         try{
	            socket.leaveGroup(group); //�׷� Ż��
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
	   
	   //send
	   public void windowClosed(WindowEvent we){}
	   public void windowIconified(WindowEvent we){}
	   public void windowDeiconified(WindowEvent we){}
	   public void windowActivated(WindowEvent we){}
	   public void windowDeactivated(WindowEvent we){}
	   public void actionPerformed(ActionEvent ae) {}
	   
	   protected synchronized void handleIOException(IOException e){
	         try{
	            stop();
	         }catch(IOException ie){  
	            System.out.println(ie);
	         }
	   }
	   
	   //receive
	   //receive
	   public void run(){
	      try{
	         while(!Thread.interrupted()){
	            incoming.setLength(incoming.getData().length);
	            //���� �� �ޱ�
	            socket.receive(incoming);
	            String message = new String(incoming.getData(), 0, incoming.getLength(), "UTF8");
	            //Ŭ���̾�Ʈ�� ���
	            output.append(message+"\n");
	            //������ ������

	         }
	      }catch(IOException e){
	         handleIOException(e);
	      }
	   }
	   
	   
	   public static void main(String args[]) throws IOException{
	      if((args.length != 1) || (args[0].indexOf(":") < 0)) // ��Ƽĳ��Ʈ�ּ�:��Ʈ��ȣ ���·� �Է��� �ؾ���.
	         throw new IllegalArgumentException("�߸��� ��Ƽĳ��Ʈ �ּ��Դϴ�.");
	      int idx = args[0].indexOf(":");
	      InetAddress group = InetAddress.getByName(args[0].substring(0, idx));
	      int port = Integer.parseInt(args[0].substring(idx+1));
	      Receiver r = new Receiver(group, port);
	      r.start();
	   }

	} 