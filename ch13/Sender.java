package ch13;

import java.awt.BorderLayout;
import java.awt.Frame;
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

public class Sender implements Runnable, WindowListener, ActionListener
{
	   protected InetAddress group;
	   protected int port;
	   protected Frame frame;
	   protected TextField input, input2; //��
	   protected TextArea output;
	   protected Thread listener;
	   protected MulticastSocket socket;
	   protected DatagramPacket outgoing, incoming;
	   
	   public Sender(InetAddress group, int port){
	      this.group = group;
	      this.port = port;
	      initAWT();
	   }
	   protected void initAWT(){
	      frame = new Frame("��Ƽĳ��Ʈ ä�� ���� [ȣ��Ʈ : "+group.getHostAddress()+" , "+port+"]");
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
	   protected void initNet() throws IOException{
	      socket = new MulticastSocket();
	      socket.setTimeToLive(1);
	      outgoing = new DatagramPacket(new byte[1], 1, group, port);
	      incoming = new DatagramPacket(new byte[65508], 65508);
	   }
	   @SuppressWarnings("deprecation")
	public synchronized void stop() throws IOException{
	      frame.setVisible(false);
	      if(listener != null){
	         listener.interrupt();
	         listener = null;

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
	   public void actionPerformed(ActionEvent ae){
	      try{
	         byte[] utf = ae.getActionCommand().getBytes("UTF8");
	         outgoing.setData(utf);
	         outgoing.setLength(utf.length);
	         socket.send(outgoing);
	         
	         input.setText(""); // �ؽ�Ʈ �ʵ��� ������ �����.
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

		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				while(true) {
					incoming.setLength(incoming.getData().length);
		            //���� �� �ޱ�
		            socket.receive(incoming);
		            String message = new String(incoming.getData(), 0, incoming.getLength(), "UTF8");
		            //Ŭ���̾�Ʈ�� ���
		            output.append(message+"\n");
				}
			}catch(IOException e) {
				e.printStackTrace();
			}

		}
		
	public static void main(String[] args) throws IOException {
	      if((args.length != 1) || (args[0].indexOf(":") < 0)) // ��Ƽĳ��Ʈ�ּ�:��Ʈ��ȣ ���·� �Է��� �ؾ���.
	          throw new IllegalArgumentException("�߸��� ��Ƽĳ��Ʈ �ּ��Դϴ�.");
	       int idx = args[0].indexOf(":");
	       InetAddress group = InetAddress.getByName(args[0].substring(0, idx));
	       int port = Integer.parseInt(args[0].substring(idx+1));
	       Sender s = new Sender(group, port);
	      s.start();
	}



}