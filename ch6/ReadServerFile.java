package ch6;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class ReadServerFile extends Frame implements ActionListener{
	
	private TextField enter;
	private TextArea contents;
	public ReadServerFile() {
		super("ȣ��Ʈ ���� �б�");
		 setLayout(new BorderLayout());
		 
		 enter = new TextField("URL�� �Է��ϼ���!");
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
		String location = e.getActionCommand(); //�ؽ�Ʈ �ʵ忡 �Էµ� URL�� ����
		
		try {
			url = new URL(location);
			is = url.openStream(); //location(ȣ��Ʈ)�� �����Ű�� InputStream ��ü ����
			input = new BufferedReader(new InputStreamReader(is));
			
			contents.setText("������ �д� ���Դϴ�...");
			
			while((line = input.readLine()) != null)
				buffer.append(line).append('\n');
			contents.setText(buffer.toString());
			input.close();
		}catch(MalformedURLException e1) {
			contents.setText("URL ������ �߸��Ǿ����ϴ�.");
		}catch(IOException e2) {
			contents.setText(e2.toString());
		}catch(Exception e3) {
			contents.setText("ȣ��Ʈ ��ǻ���� ���ϸ��� �� �� �ֽ��ϴ�.");
		}
		
		
	}
	
	class WinListener extends WindowAdapter{
		public void windowClosing(WindowEvent we) {
			System.exit(0);
		}
	}

}
