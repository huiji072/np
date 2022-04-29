package ch7_8;

import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;

public class WriteRandomFile extends Frame implements ActionListener{
	
	private TextField accountField, nameField, balanceField;
	private Button enter, find, delete, clear; //입력 조회 삭제 close
	private RandomAccessFile output;
	private Record data;
	Socket theSocket;
	int port = 5000;
	String host = "localhost";
	InputStream is;
	BufferedReader reader;
	OutputStream os;
	BufferedWriter writer;
	String theLine;
	
	public WriteRandomFile() {
		super("파일쓰기");
		data = new Record();
	
		try {
			output = new RandomAccessFile("customer.txt", "rw");
		}catch(IOException e) {
			System.err.println(e.toString());
			System.exit(1);
		}
		
		setSize(300,150);
		setLayout(new GridLayout(5, 2));
		
		add(new Label("구좌번호 "));
		accountField = new TextField(100);
		add(accountField);
		
		add(new Label("이름"));
		nameField = new TextField(20);
		add(nameField);
		
		add(new Label("잔고"));
		balanceField = new TextField(20);
		add(balanceField);
		
		enter = new Button("입력");
		enter.addActionListener(this);
		add(enter);
		
		find = new Button("조회");
		find.addActionListener(this);
		add(find);
		
		delete = new Button("삭제");
		delete.addActionListener(this);
		add(delete);
		
		clear = new Button("clear");
		clear.addActionListener(this);
		add(clear);
		
		addWindowListener(new WinListener());
		setVisible(true);
	
		
	}
	
	public void addRecord() {
		int accountNo = 0;
		Double d;
		
		if( !accountField.getText().equals("")) {
			try {
				
				//서버에 접속
				theSocket = new Socket(host, port);
				
				is = theSocket.getInputStream();
				reader = new BufferedReader(new InputStreamReader(is));
				
				os = theSocket.getOutputStream();
				writer = new BufferedWriter(new OutputStreamWriter(os));

				accountNo = Integer.parseInt(accountField.getText());
					
				if(accountNo > 0 && accountNo <= 100) {
					data.setAccount(accountNo);
					data.setName(nameField.getText());
					d = new Double(balanceField.getText());
					data.setBalance(d.doubleValue());

					output.seek((long) (accountNo-1) * Record.size()); 
					data.write(output);
					
					while(true) {
						writer.write(accountNo+"\r"+nameField.getText()+"\r" + d.doubleValue()+"\r\n");
						writer.flush();
//						System.out.println(accountNo+"\r\n");
					}
					
				}else {
					try {
						Exception e = new Exception("account is 100 or less.\n");
						throw e;
					}catch(Exception e1) {
						System.err.println(e1.getMessage());
					}
				}
				


				
			}catch(NumberFormatException nfe) {
				System.err.println("숫자를 입력하세요");
			}catch(IOException io) {
				System.err.println("파일 쓰기 에러\n" + io.toString());
				System.exit(1);
			}
			
		}
	}
	
	public void findRecord() {
		int findAccountNo = Integer.parseInt(accountField.getText());
		
		try {
			while(true) {
				output.seek((long) (findAccountNo-1) * Record.size()); 
				data.read(output);
				if(findAccountNo == data.getAccount()) {
					accountField.setText("" + data.getAccount());
                    nameField.setText(data.getname());
                    balanceField.setText("" + data.getBalance());	
                    return;
				}
			}
		}catch(Exception e) {
			System.err.println(e.toString());
		}
		
	}
	
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
	
	public void clearRecord() {
		accountField.setText("");
		nameField.setText("");
		balanceField.setText("");
	}
	
	class WinListener extends WindowAdapter{
		public void windowClosing(WindowEvent we) {
			System.exit(0);
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == enter) {
			addRecord();
		}else if(e.getSource() == find){
			findRecord();
		}else if(e.getSource() == delete) {
			deleteRecord();
		}else if(e.getSource() == clear) {
			clearRecord();
		}
	}

	public static void main(String[] args) {
		WriteRandomFile client = new WriteRandomFile();
		
		
	}

}

class Record{
	private int account;
	private String name;
	private double balance;
//	Socket theSocket;
//	int port = 5000;
//	String host = "localhost";
//	InputStream is;
//	BufferedReader reader;
//	OutputStream os;
//	BufferedWriter writer;
//	String theLine;

	
	//RandomAccessFile로부터 한 레코드를 읽는다.
	public void read(RandomAccessFile file) throws IOException{
		account = file.readInt(); 
		char namearray[] = new char[15];
		for(int i = 0 ; i < namearray.length ; i++)
			namearray[i] = file.readChar();
		name = new String(namearray);
		balance = file.readDouble();
		
	
		
	}
	
	//RandoeAccessFile에 한 레코드를 저장한다.
	public void write(RandomAccessFile file) throws IOException{
		StringBuffer buf;
		file.writeInt(account);
		if(name != null)
			buf = new StringBuffer(name);
		else
			buf = new StringBuffer(15);
		buf.setLength(15);
		file.writeChars(buf.toString());
		file.writeDouble(balance);

		
	}
	
	public void setAccount(int a) {account = a;}
	public int getAccount() {return account;}
	public void setName(String f) {name = f;}
	public String getname() {return name;}
	public void setBalance(double b) {balance = b;}
	public double getBalance() {return balance;}
	public static int size() {return 42;}
	
	}
