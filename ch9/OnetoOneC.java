//package ch9;
//import java.awt.*;
//import java.awt.event.*;
//import java.io.*;
//import java.net.*;
//
//
//public class OneToOneC extends Frame implements ActionListener{
//   
//   TextArea display;
//   TextField text;
//   Label lword;
//   BufferedWriter output;
//   BufferedReader input;
//   Socket client;
//   String clientdata = "";
//   Button reconn;
//   
//   public OneToOneC() {
//      super("Ŭ���̾�Ʈ");
//      display = new TextArea("", 0, 0, TextArea.SCROLLBARS_VERTICAL_ONLY);
//      display.setEditable(false);
//      add(display, BorderLayout.CENTER);
//      
//      Panel pword = new Panel(new BorderLayout());
//      lword = new Label("��ȭ��");
//      text = new TextField(30);
//      reconn = new Button("������");
//      text.addActionListener(this);
//      reconn.addActionListener(this);
//      pword.add(lword, BorderLayout.WEST);
//      pword.add(text, BorderLayout.CENTER);
//      pword.add(reconn, BorderLayout.EAST);
//      add(pword, BorderLayout.SOUTH);
//      
//      addWindowListener(new WinListener());
//      setSize(300, 200);
//      setVisible(true);
//      
//   }
//   
//   public void runClient() {   
//      try {
//         client = new Socket(InetAddress.getLocalHost(), 5000);
//         
//         String server = client.getInetAddress().getHostAddress();
//         display.append("\n���� " + server + "�� ����Ǿ����ϴ�.\n");
//         
//         input = new BufferedReader(new InputStreamReader(client.getInputStream()));
//         output = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
//            
//
//         while(true) {
//            String serverdata = input.readLine();
//            if(serverdata == null) {
//               display.append("�������� ������ �ߴ�");
//               output.flush();
//               break;
//            }else {
//               display.append("\n �����޽��� "+serverdata);
//               output.flush();
//            }
//         }
//         client.close();
//         client = null;
//
//      }catch(IOException e) {
//         e.printStackTrace();
//      }
//   }
//   
//   public void actionPerformed(ActionEvent ae) {
//      //Ŭ���̾�Ʈ �� ���, ������ ������
//      
//      if((ae.getSource() == reconn) && (client != null)) {
//         return;
//      }
//      if((ae.getSource()==reconn) && (client == null) ) {
//         ClientThread reconn = new ClientThread();
//         reconn.start();
//         return;
//      }
//      if(client == null) {
//         display.append("����� ������ �����ϴ�.");
//         display.setText("");
//         return;
//      }
//      clientdata = text.getText();
//      try {
//         display.append("\nŬ���̾�Ʈ : " + clientdata);
//         output.write(clientdata + "\r\n");
//         output.flush();
//         text.setText("");
//         
//         if(clientdata.equals("quit")) {
//            display.append("����� ������ �����ϴ�.");
//            client.close();
//            client = null;
//         }
//      }catch(IOException e) {
//         e.printStackTrace();
//      }
//
//   }
//
//   public static void main(String[] args) {
//      // TODO Auto-generated method stub
//      OneToOneC c = new OneToOneC();
//      c.runClient();
//   }
//   
//   class WinListener extends WindowAdapter{
//      public void windowClosing(WindowEvent e) {
//         try {
//            if(client!=null)
//               client.close();
//         }catch(IOException e2) {
//            System.out.println(e2);
//         }
//         System.exit(0);
//      }
//   }
//   class ClientThread extends Thread{
//      public void run() {
//         runClient();
//      }
//   }
//}