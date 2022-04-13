package ch3;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class FileTest extends Frame implements ActionListener{
	
	TextField input;
	TextArea output;
	
	public FileTest() {
		super("File Ŭ���� �׽�Ʈ");
		
		input = new TextField("���� ���� �Է��Ͻÿ�");
		input.addActionListener(this);
		output = new TextArea();
		
		add(input, BorderLayout.NORTH);
		add(output, BorderLayout.CENTER);
		
		addWindowListener(new WinListener());
		
		setSize(500,500);
		setVisible(true);


	}

	@Override
	public void actionPerformed(ActionEvent e) {
		File file = new File(e.getActionCommand());
		
		if(file.exists()) {
			output.setText(file.getName() + "�� �����Ѵ�.\n" +
		(file.isFile() ? "�����̴�.\n" : "������ �ƴϴ�.\n") +
		(file.isDirectory() ? "���丮�̴�.\n" : "���丮�� �ƴϴ�.\n") +
		(file.isAbsolute() ? "�������̴�.\n" : "�����ΰ� �ƴϴ�.\n") +
		"������ ������¥�� : " + file.lastModified() +
		"\n������ ���̴� : " + file.length() + 
		"\n������ ��δ� : " + file.getPath() +
		"\n�����δ� : " + file.getAbsolutePath() + 
		"\n���� ���丮�� : " + file.getParent()
					);
		}
		
		if(file.isFile()) {
			try {
				RandomAccessFile r = new RandomAccessFile(file, "r");
				StringBuffer sb = new StringBuffer();
				String text;
				
				if((text = r.readLine()) != null) {
					sb.append(text + "\n");
				}
				output.append(sb.toString());
			}catch(IOException e2) {
				System.out.println(e2);
			}
			
		}else if(file.isDirectory()) {
			String dir[] = file.list();
			for(int i = 0 ; i < dir.length ; i++) {
				output.append(dir[i] + "\n");
			}
		}else {
			output.setText(e.getActionCommand() + "�� �������� �ʴ´�.");
		}
		
	}
	
	class WinListener extends WindowAdapter{
		public void windowClosing(WindowEvent we) {
			System.exit(0);
		}
	}
	
	public static void main(String[] args) {
		FileTest f = new FileTest();
	}

}
