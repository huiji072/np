package test;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.event.*;

public class WriteRandomFile extends Frame implements ActionListener
{
   private TextField accountField, nameField, balanceField;
   private Button enter, find, delete, clear; //�Է� ��ȸ ���� close
   private RandomAccessFile output;
   private Record data;
   Socket client;
   BufferedReader reader;
   BufferedWriter writer;
   ArrayList<String> list = new ArrayList<>();
   
   public WriteRandomFile(){
      super( "���Ͼ���" );
      data = new Record();
      try {
         output = new RandomAccessFile( "customer.txt", "rw" );
      } catch ( IOException e ) {
         System.err.println( e.toString() );
         System.exit( 1 );
      }
      setSize( 300, 150 );
      setLayout( new GridLayout( 5, 2 ) );
      add( new Label( "���¹�ȣ" ) );
      accountField = new TextField();
      add( accountField );
      add( new Label( "�̸�" ) );
      nameField = new TextField( 20 );
      add( nameField );      
      add( new Label( "�ܰ�" ) );
      balanceField = new TextField( 20 );
      add( balanceField );

      enter = new Button("�Է�");
      enter.addActionListener(this);
      add(enter);
		
	  find = new Button("��ȸ");
	  find.addActionListener(this);
	  add(find);
		
	  delete = new Button("����");
	  delete.addActionListener(this);
	  add(delete);
		
	  clear = new Button("clear");
	  clear.addActionListener(this);
	  add(clear);
      
      setVisible( true );  
      addWindowListener(new WinListener());
   }
   
//   public void runClient() {
//
//   }
   
   //�Է�
   public void addRecord(){
      int accountNo = 0;
      Double d;
      if ( ! accountField.getText().equals( "" ) ) {
         try {
            accountNo = Integer.parseInt( accountField.getText() );
            if ( accountNo > 0 && accountNo <= 100 ) {
               data.setAccount( accountNo );
               data.setName( nameField.getText());
               d = new Double ( balanceField.getText());
               data.setBalance( d.doubleValue() );
               output.seek((long) ( accountNo-1 ) * Record.size() );
               data.write( output );
              // list.add("\r\n" + accountField.getText() +" "+nameField.getText() + " "+ balanceField.getText());

            }
            // �ؽ�Ʈ�ʵ��� ������ �����.
            accountField.setText( "" );
            nameField.setText( "" );
            balanceField.setText( "" );
         } catch ( NumberFormatException nfe ) {
            System.err.println("���ڸ� �Է��ϼ���" );
         } catch ( IOException io ) {
            System.err.println("���Ͼ��� ����\n" + io.toString() );
            System.exit( 1 );
         }
      }
   }
   
   //��ȸ
   public void findRecord() {
	   int findAccountNo = Integer.parseInt(accountField.getText());
		
		try {
			while(true) {
				output.seek((long) (findAccountNo-1) * Record.size()); 
				data.read(output);
				if(findAccountNo == data.getAccount()) {
					accountField.setText("" + data.getAccount());
                   nameField.setText(data.getName());
                   balanceField.setText("" + data.getBalance());	
                   return;
				}
			}
		}catch(Exception e) {
			System.err.println(e.toString());
		}
   }
   
   //����
	public void deleteRecord() {
		int findAccountNo = Integer.parseInt(accountField.getText());
		
		try {
			while(true) {
				output.seek((long) (findAccountNo-1) * Record.size()); 
				data.read(output);
				if(findAccountNo == data.getAccount()) {
					accountField = null;
					nameField = null;
					balanceField = null;
                    return;
				}
			}
		}catch(Exception e) {
			System.err.println(e.toString());
		}
	}
	
	//clear
	public void clearRecord() {
		accountField.setText("");
		nameField.setText("");
		balanceField.setText("");
	}
   
   public void actionPerformed( ActionEvent e ){
	   if(e.getSource() == enter) { //�Է�
			addRecord();
		}else if(e.getSource() == find){ //��ȸ
			findRecord();
		}else if(e.getSource() == delete) { //����
			deleteRecord();
		}else if(e.getSource() == clear) { //clear
			clearRecord();
		}
   }
   
   public static void main( String args[] ) {
      new WriteRandomFile();
   }
   
   class WinListener extends WindowAdapter{
	   public void windowClosing(WindowEvent we) {
		   System.exit(0);
	   }
   }
}

class Record
{
	
   private int account;
   private String name;
   private double balance;
   Socket client;
   BufferedReader reader;
   BufferedWriter writer;
   List<String> list = new ArrayList<>();

   // RandomAccessFile�κ��� �� ���ڵ带 �д´�.
   public void read(RandomAccessFile file) throws IOException {
      account = file.readInt(); // file�κ��� ���¹�ȣ�� �д´�
      char namearray[] = new char[15];
      for(int i = 0; i < namearray.length; i++ )
         namearray[i] = file.readChar();
      name = new String(namearray);
      balance = file.readDouble();
   }
   // RandomAccessFile�� �� ���ڵ带 �����Ѵ�.
   public void write(RandomAccessFile file) throws IOException {
	   try {
		   client = new Socket(InetAddress.getLocalHost(), 5000); //5000�� ��Ʈ�� ����
		   reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
		   writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
		   
		   list.add(getAccount() +" "+ getName() + " " + getBalance());
           
           System.out.println(list + "\r\n");
           writer.write(list+"\r\n");
           writer.flush();
           
	   }catch(IOException e){
		   e.printStackTrace();
	   }
	   
      StringBuffer buf;
      file.writeInt( account ); // file�� ���¹�ȣ�� �����Ѵ�.
      if (name != null) 
         buf = new StringBuffer(name);
      else 
         buf = new StringBuffer(15);
      buf.setLength(15); // �̸��� �����ϴ� �޸� ũ�⸦ 15�� ����
      file.writeChars(buf.toString());
      file.writeDouble( balance );
   }
   public void setAccount(int a) { account = a; } // ���¹�ȣ�� �����Ѵ�
   public int getAccount() { return account; } // ���¹�ȣ�� ��ȯ�Ѵ�.
   public void setName(String f) { name = f; } 
   public String getName() { return name; }
   public void setBalance(double b) { balance = b; }
   public double getBalance() { return balance; }
   public static int size() { return 42; } // �� ���ڵ��� ����
}
