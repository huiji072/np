package ch3;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class FileTest extends Frame implements ActionListener{
	
	TextField input;
	TextArea output;
	
	public FileTest() {
		super("File 클래스 테스트");
		
		input = new TextField("파일 명을 입력하시오");
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
			output.setText(file.getName() + "이 존재한다.\n" +
		(file.isFile() ? "파일이다.\n" : "파일이 아니다.\n") +
		(file.isDirectory() ? "디렉토리이다.\n" : "디렉토리가 아니다.\n") +
		(file.isAbsolute() ? "절대경로이다.\n" : "절대경로가 아니다.\n") +
		"마지막 수정날짜는 : " + file.lastModified() +
		"\n파일의 길이는 : " + file.length() + 
		"\n파일의 경로는 : " + file.getPath() +
		"\n절대경로는 : " + file.getAbsolutePath() + 
		"\n상위 디렉토리는 : " + file.getParent()
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
			output.setText(e.getActionCommand() + "은 존재하지 않는다.");
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
