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
	   protected TextField input, input2; //메
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
	      frame = new Frame("멀티캐스트 채팅 서버 [호스트 : "+group.getHostAddress()+" , "+port+"]");
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
	         listener.start(); // 스레드를 시작한다.(run() 메소드 실행)
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
	         
	         input.setText(""); // 텍스트 필드의 내용을 지운다.
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
		            //서버 값 받기
		            socket.receive(incoming);
		            String message = new String(incoming.getData(), 0, incoming.getLength(), "UTF8");
		            //클라이언트에 출력
		            output.append(message+"\n");
				}
			}catch(IOException e) {
				e.printStackTrace();
			}

		}
		
	public static void main(String[] args) throws IOException {
	      if((args.length != 1) || (args[0].indexOf(":") < 0)) // 멀티캐스트주소:포트번호 형태로 입력을 해야함.
	          throw new IllegalArgumentException("잘못된 멀티캐스트 주소입니다.");
	       int idx = args[0].indexOf(":");
	       InetAddress group = InetAddress.getByName(args[0].substring(0, idx));
	       int port = Integer.parseInt(args[0].substring(idx+1));
	       Sender s = new Sender(group, port);
	      s.start();
	}



}