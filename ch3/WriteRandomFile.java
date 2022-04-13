package ch3;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class WriteRandomFile extends Frame implements ActionListener{
	
	private TextField accountField, nameField, balanceField;
	private Button enter, done;
	private RandomAccessFile output;
	private Record data;
	
	public WriteRandomFile() {
		super("파일쓰기");
		data = new Record();
		try {
			output = new RandomAccessFile("customer.txt", "rw");
		}catch(IOException e) {
			System.err.println(e.toString());
			System.exit(1);
		}
		setSize(300, 150);
		setLayout(new GridLayout(4, 2));
		
		add(new Label("구좌번호"));
		accountField = new TextField();
		
		add(new Label("이름"));
		nameField = new TextField();
		
		add(new Label("잔고"));
		balanceField = new TextField();
		
		enter = new Button("입력");
		enter.addActionListener(this);
		add(enter);
		
		done = new Button("종료");
		done.addActionListener(this);
		add(done);
		
		setVisible(true);
	}
	
	public void addRecord() {
		int accountNo = 0;
		Double d;
		if(!accountField.getText().equals("")) {
			try {
				accountNo = Integer.parseInt(accountField.getText());
				if(accountNo > 0 && accountNo <= 100) {
					data.setAccount(accountNo);
					data.setName(nameField.getText());
					d = new Double(balanceField.getText());
					data.setBalance(d.doubleValue());
					output.seek((long) (accountNo-1)*Record.size());
					data.write(output);
				}
				
				accountField.setText("");
				nameField.setText("");
				balanceField.setText("");
			}catch(NumberFormatException nfe) {
				System.err.println("숫자를 입력하세요 ");
			}catch(IOException io) {
				System.err.println("파일쓰기 에러 \n" + io.toString());
				System.exit(1);
			}
		}
	}

	public static void main(String[] args) {

		new WriteRandomFile();

	}


	@Override
	public void actionPerformed(ActionEvent e) {
		addRecord();
		if(e.getSource() ==done) {
			try {
				output.close();
			}catch(IOException e2) {
				System.out.println(e2);
			}
			System.exit(0);
		}
		
	}
	
}

class Record{
	private int account;
	private String name;
	private double balance;
	
	public void read(RandomAccessFile file) throws IOException{
		account = file.readInt();
		char namearray[] = new char[15];
		for(int i = 0 ; i < namearray.length ; i++)
			namearray[i] = file.readChar();
		name = new String(namearray);
		balance = file.readDouble();
	}
	
	public void write(RandomAccessFile file) throws IOException{
		StringBuffer buf;
		file.writeInt(account);
		if(name != null) {
			buf = new StringBuffer(name);
		}else {
			buf = new StringBuffer(15);
		}
		buf.setLength(15);
		file.writeChars(buf.toString());
		file.writeDouble(balance);
	}
}
