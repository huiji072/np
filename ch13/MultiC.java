package ch13;

import java.io.*;
import java.net.*;
import java.util.StringTokenizer;
import java.awt.*;
import java.awt.event.*;

public class MultiC extends Frame implements ActionListener, KeyListener {
	 protected InetAddress group;
	   protected int port;
	   protected MulticastSocket socket;
	   protected DatagramPacket outgoing, incoming;
   TextArea display;
   TextField wtext, ltext;
   Label mlbl, wlbl, loglbl;
   BufferedWriter output;
   BufferedReader input;
   Socket client;
   StringBuffer clientdata;
   String serverdata;
   String ID;
   Button logout;
   private static final String SEPARATOR = "|";
   private static final int REQ_LOGON = 1001;
   private static final int REQ_SENDWORDS = 1021;
   private static final int REQ_LOGOUT = 1002;
	private static final int REQ_LOGON_OVERLAP = 1003;
	
   public MultiC(InetAddress group, int port){
      super("Ŭ���̾�Ʈ");
      this.group = group;
      this.port = port;
      initAWT();
   }
   
   protected void initAWT() {

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
	      logout = new Button("�α׾ƿ�"); //�α׾ƿ� ��ư
			logout.addActionListener(this);
			logout.setVisible(false); //�α׾ƿ� ��ư�� �ʱ⿡ �Ⱥ��̰� �Ѵ�.
			plabel.add(logout, BorderLayout.CENTER);
	      plabel.add(loglbl, BorderLayout.WEST);
	      plabel.add(ltext, BorderLayout.EAST);
	      ptotal.add(plabel, BorderLayout.SOUTH);

	      add(ptotal, BorderLayout.SOUTH);

	      addWindowListener(new WinListener());
	      setSize(300,250);
	      setVisible(true);
   }
	
   protected void initNet() throws IOException{
	      socket = new MulticastSocket();
	      socket.setTimeToLive(1);
	      socket.joinGroup(group); //�׷� ����
	      outgoing = new DatagramPacket(new byte[1], 1, group, port);
	      incoming = new DatagramPacket(new byte[65508], 65508);
	   }
   
   public void runClient() {
      try {
    	  initNet();
         client = new Socket(InetAddress.getLocalHost(), 5000);
         mlbl.setText("����� �����̸� : " + client.getInetAddress().getHostName());
         input = new BufferedReader(new InputStreamReader(client.getInputStream()));
         output = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
         clientdata = new StringBuffer(2048);
         mlbl.setText("���� �Ϸ� ����� ���̵� �Է��ϼ���.");
         StringTokenizer st;
			while(true) {
				try {
					//�������� ���� ������ �ޱ�
					serverdata = input.readLine();
		           System.out.println(serverdata);
					st = new StringTokenizer(serverdata, " ");
				
					if(serverdata == "�ߺ��� ID�Դϴ�.") {
						mlbl.setText("�ߺ��� ID �Դϴ�!!!");
						ID = null;
						ltext.setVisible(true); 
						loglbl.setVisible(true);
						logout.setVisible(false); //�α׾ƿ� ��ư�� ���̰� �Ѵ�.
					}else{
						mlbl.setText(ID + "(��)�� �α��� �Ͽ����ϴ�.");
						ltext.setVisible(false); //�α׿� �Է�â�� �������
						loglbl.setVisible(false);
						logout.setVisible(true); //�α׾ƿ� ��ư�� ���̰� �Ѵ�.
					}
					display.append(serverdata + "\r\n");
//					output.flush();
					break;
				}catch(IOException e) {
					e.printStackTrace();
				}
					
			
		}
      } catch(IOException e) {
         e.printStackTrace();
      }
   }
		
   public void actionPerformed(ActionEvent ae){
      if(ID == null) {
         ID = ltext.getText();
         mlbl.setText(ID + "(��)�� �α��� �Ͽ����ϴ�.");
         try {
            clientdata.setLength(0);
            clientdata.append(REQ_LOGON);
            clientdata.append(SEPARATOR);
            clientdata.append(ID);
            output.write(clientdata.toString()+"\r\n");
            output.flush();
            //������ �� ���� 1001|ID
            byte[] utf = clientdata.toString().getBytes("UTF8");
            outgoing.setData(utf);
            outgoing.setLength(utf.length);
            socket.send(outgoing);
            ltext.setVisible(false);
         } catch(Exception e) {
            e.printStackTrace();
         }
      }
      
		if(ae.getSource().equals(logout)) { //�α׾ƿ� ��ư�� ������
			mlbl.setText("");		
			ltext.setVisible(true); 
			loglbl.setVisible(true); //�α��� �Է�â ����
			logout.setVisible(false); //�α׾ƿ� ��ư �Ⱥ���
			ID = ltext.getText();
			mlbl.setText(ID + "(��)�� �α׾ƿ� �Ͽ����ϴ�.");
			try {
				ltext.setText("");
				clientdata.setLength(0);
				clientdata.append(REQ_LOGOUT);
				clientdata.append(SEPARATOR);
				clientdata.append(ID);
				output.write(clientdata.toString() + "\r\n");
				output.flush();
				ID = null;
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
   }
	
   public static void main(String args[]) throws IOException {
	   if((args.length != 1) || (args[0].indexOf(":") < 0)) // ��Ƽĳ��Ʈ�ּ�:��Ʈ��ȣ ���·� �Է��� �ؾ���.
	         throw new IllegalArgumentException("�߸��� ��Ƽĳ��Ʈ �ּ��Դϴ�.");
	      int idx = args[0].indexOf(":");
	      InetAddress group = InetAddress.getByName(args[0].substring(0, idx));
	      int port = Integer.parseInt(args[0].substring(idx+1));
      MultiC c = new MultiC(group, port);
      c.runClient();
   }
		
   class WinListener extends WindowAdapter {
	   public void windowClosing(WindowEvent e) {		
			ID = ltext.getText();
			mlbl.setText(ID + "(��)�� �α׾ƿ� �Ͽ����ϴ�.");
			try {
				clientdata.setLength(0);
				clientdata.append(REQ_LOGOUT);
				clientdata.append(SEPARATOR);
				clientdata.append(ID);
				output.write(clientdata.toString() + "\r\n");
				output.flush();
				ID = null;
			}catch(IOException e2) {
				e2.printStackTrace();
			}
			System.exit(0);
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
               output.write(clientdata.toString()+"\r\n");
               output.flush();
               //������ �� ���� 100x|ID|message
               byte[] utf = clientdata.toString().getBytes("UTF8");
               outgoing.setData(utf);
               outgoing.setLength(utf.length);
               socket.send(outgoing);
               wtext.setText("");
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