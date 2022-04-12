package ch6;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class ReadServerFile extends Frame implements ActionListener{
	
	private TextField enter;
	private TextArea contents;
	public ReadServerFile() {
		super("호스트 파일 읽기");
		 setLayout(new BorderLayout());
		 
		 enter = new TextField("URL를 입력하세요!");
		 enter.addActionListener(this);
		 add(enter, BorderLayout.NORTH);
		 contents = new TextArea("", 0, 0, TextArea.SCROLLBARS_VERTICAL_ONLY);
		 add(contents, BorderLayout.CENTER);
		 
		 addWindowListener(new WinListener());
		 setSize(350,150);
		 setVisible(true);
	}
	
	public static void main(String[] args) {
		ReadServerFile read = new ReadServerFile();
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		URL url;
		InputStream is;
		BufferedReader input;
		String line;
		StringBuffer buffer = new StringBuffer();
		String location = e.getActionCommand(); //텍스트 필드에 입력된 URL를 구함
		
		try {
			url = new URL(location);
			is = url.openStream(); //location(호스트)와 연결시키는 InputStream 객체 생성
			input = new BufferedReader(new InputStreamReader(is));
			
			contents.setText("파일을 읽는 중입니다...");
			
			while((line = input.readLine()) != null)
				buffer.append(line).append('\n');
			contents.setText(buffer.toString());
			input.close();
		}catch(MalformedURLException e1) {
			contents.setText("URL 형식이 잘못되었습니다.");
		}catch(IOException e2) {
			contents.setText(e2.toString());
		}catch(Exception e3) {
			contents.setText("호스트 컴퓨터의 파일만을 열 수 있습니다.");
		}
		
		
	}
	
	class WinListener extends WindowAdapter{
		public void windowClosing(WindowEvent we) {
			System.exit(0);
		}
	}

}
