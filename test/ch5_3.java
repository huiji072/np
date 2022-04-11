package test;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.ImageProducer;
import java.io.*;
import java.net.*;

import javax.sound.sampled.*;

//TextArea를 하나 추가
//첫번째 TextArea에는 protocol, host name, ip주소, port no, file name, hash code 등 원격 호스트의 정보를 출력한다.
//두번째 TextArea에는 URL이 가리키는 	 따라 텍스트이면 내용을 읽어와 모두 출력한다.abstract이미지, 오디오, 비디오 객체인 경우에는 그 유형만 나타내시오.

public class ch5_3 extends Frame implements ActionListener{
	
	protected String protocol;
	protected String host;
	protected int port;
	protected String file;
	
	private TextField enter;
	private TextArea contents1, contents2; //TextArea 하나 추가
	public ch5_3() {
		super("호스트 파일 읽기");
		setLayout(new BorderLayout());
		
		enter = new TextField("URL를 입력하세요!");
		enter.addActionListener(this);
		add(enter, BorderLayout.NORTH);
		
		contents1 = new TextArea("", 0, 0);
		add(contents1, BorderLayout.CENTER);
		contents2 = new TextArea("", 0, 0);
		add(contents2, BorderLayout.SOUTH);
		
		addWindowListener(new WinListener());
		setSize(450, 450);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		URL url;
		InputStream is;
		BufferedReader input;
		String line;
		StringBuffer buffer = new StringBuffer();
		String location = e.getActionCommand();
		try {
			url = new URL(location);
			is = url.openStream(); //location(호스트)과 연결시키는 InputStream 객체생성, URL이 가리키는 원격 호스트에 연결
			input = new BufferedReader(new InputStreamReader(is));
			contents1.setText("파일을 읽는 중입니다.... \n");
			contents2.setText("");
			Object o = url.getContent(); //url이 지정하는 파일을 객체로 읽는다.
			URLConnection u = url.openConnection();
			//읽은 파일을 텍스트에리어에 출력
			if(o instanceof InputStream) {
				while((line = input.readLine()) != null)
					buffer.append(line).append('\n');
				contents2.setText("파일 내용 : " + buffer.toString());
			}else if(u.getContentType().contains("video/mp4")){
				contents2.setText("비디오파일\n");
			}else if(u.getContentType().contains("audio")){
				contents2.setText("오디오파일\n");
			}else if(o.toString().contains("image")){				
				contents2.setText("이미지파일\n");
			}
//			https://docs.oracle.com/javaee/6/api/javax/mail/Part.html
			
//			원격호스트의 protocol, host name, ip주소, port no, file name, hash code
			contents1.append("protocol : " + url.getProtocol() + "\n");
			contents1.append("host name : " + url.getHost() + "\n");
			contents1.append("port no : " + url.getPort() + "\n");
			contents1.append("file name : "+url.getFile() + "\n");
			contents1.append("hash code : "  + url.hashCode() + "\n");
			input.close();
		}catch(MalformedURLException mal) {
			contents1.setText("URL 형식이 잘못되었습니다.  + \"\\n\"");
		}catch(IOException io) {
			contents1.setText(io.toString());
		}catch(Exception ex) {
			contents1.setText("호스트 컴퓨터의 파일만을 열 수 있습니다. + \"\\n\"");
		}	
	}
	
	public static void main(String[] args) {
		ch5_3 read = new ch5_3();

	}
	
	class WinListener extends WindowAdapter{
		public void windowClosing(WindowEvent we) {
			System.exit(0);
		}
	}

}
