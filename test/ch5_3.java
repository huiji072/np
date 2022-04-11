package test;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.ImageProducer;
import java.io.*;
import java.net.*;

import javax.sound.sampled.*;

//TextArea�� �ϳ� �߰�
//ù��° TextArea���� protocol, host name, ip�ּ�, port no, file name, hash code �� ���� ȣ��Ʈ�� ������ ����Ѵ�.
//�ι�° TextArea���� URL�� ����Ű�� 	 ���� �ؽ�Ʈ�̸� ������ �о�� ��� ����Ѵ�.abstract�̹���, �����, ���� ��ü�� ��쿡�� �� ������ ��Ÿ���ÿ�.

public class ch5_3 extends Frame implements ActionListener{
	
	protected String protocol;
	protected String host;
	protected int port;
	protected String file;
	
	private TextField enter;
	private TextArea contents1, contents2; //TextArea �ϳ� �߰�
	public ch5_3() {
		super("ȣ��Ʈ ���� �б�");
		setLayout(new BorderLayout());
		
		enter = new TextField("URL�� �Է��ϼ���!");
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
			is = url.openStream(); //location(ȣ��Ʈ)�� �����Ű�� InputStream ��ü����, URL�� ����Ű�� ���� ȣ��Ʈ�� ����
			input = new BufferedReader(new InputStreamReader(is));
			contents1.setText("������ �д� ���Դϴ�.... \n");
			contents2.setText("");
			Object o = url.getContent(); //url�� �����ϴ� ������ ��ü�� �д´�.
			URLConnection u = url.openConnection();
			//���� ������ �ؽ�Ʈ����� ���
			if(o instanceof InputStream) {
				while((line = input.readLine()) != null)
					buffer.append(line).append('\n');
				contents2.setText("���� ���� : " + buffer.toString());
			}else if(u.getContentType().contains("video/mp4")){
				contents2.setText("��������\n");
			}else if(u.getContentType().contains("audio")){
				contents2.setText("���������\n");
			}else if(o.toString().contains("image")){				
				contents2.setText("�̹�������\n");
			}
//			https://docs.oracle.com/javaee/6/api/javax/mail/Part.html
			
//			����ȣ��Ʈ�� protocol, host name, ip�ּ�, port no, file name, hash code
			contents1.append("protocol : " + url.getProtocol() + "\n");
			contents1.append("host name : " + url.getHost() + "\n");
			contents1.append("port no : " + url.getPort() + "\n");
			contents1.append("file name : "+url.getFile() + "\n");
			contents1.append("hash code : "  + url.hashCode() + "\n");
			input.close();
		}catch(MalformedURLException mal) {
			contents1.setText("URL ������ �߸��Ǿ����ϴ�.  + \"\\n\"");
		}catch(IOException io) {
			contents1.setText(io.toString());
		}catch(Exception ex) {
			contents1.setText("ȣ��Ʈ ��ǻ���� ���ϸ��� �� �� �ֽ��ϴ�. + \"\\n\"");
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
