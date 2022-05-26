package ch14;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class HttpURlConn extends Frame implements ActionListener{
	
	private final String USER_AGENT = "Mozilla/5.0";
	private TextField enter;
	private TextArea display;
	
	public HttpURlConn() {
		super("�������� ���� ���");
		setLayout(new BorderLayout());
		enter = new TextField("URL�� �Է��ϼ���");
		enter.addActionListener(this);
		add(enter, BorderLayout.NORTH);
		display = new TextArea("", 0, 0, TextArea.SCROLLBARS_VERTICAL_ONLY);
		display.setEditable(false);
		add (display, BorderLayout.CENTER);
		
		setSize(350, 450);
		setVisible(true);
		addWindowListener(new WinListener());
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		URL url;
		URLConnection urlconn;
		HttpURLConnection httpconn;
		InputStream is;
		BufferedReader input;
		String line;
		StringBuffer buffer = new StringBuffer();
		String location = e.getActionCommand(); 
		try {
			url = new URL(location); //����
			urlconn = url.openConnection();
			httpconn = (HttpURLConnection)url.openConnection();
			String headertype = urlconn.getContentType();
			is = urlconn.getInputStream();
			input = new BufferedReader(new InputStreamReader(is));
			line = "���Ÿ�� : " + headertype + "\n";
			buffer.append(line);
			line = "�����ڵ� : " + httpconn.getResponseCode() + "\n";
			buffer.append(line);
			line = "���䱸�� : " + httpconn.getResponseMessage() + "\n";
			buffer.append(line);
			while((line=input.readLine()) != null) {
				buffer.append(line).append('\n');
			}
			display.setText(buffer.toString());
			input.close();
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch (IOException e2) {
			e2.printStackTrace();
		} catch (Exception e3) {
			e3.printStackTrace();
		}
		
	}
	public static void main(String[] args) throws Exception{
		HttpURlConn http = new HttpURlConn();
	}
	
	class WinListener extends WindowAdapter{
		public void windowClosing(WindowEvent we) {
			System.exit(0);
		}
	}

}
