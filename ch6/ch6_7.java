package ch6;

import java.awt.*;
import java.io.*;
import java.net.*;

public class ch6_7 {

	public static void main(String[] args) {
		
		URL u;
		String line, urlstring;
		BufferedReader br, reader;
		br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("URL�� �Է��Ͻʽÿ�!");
		
		try {
			urlstring = br.readLine(); //URL�� �Է¹޴´�.
			u = new URL(urlstring); //u URL ��ü�� �����Ѵ�.
			Object o = u.getContent(); //URL�� �����ϴ� ������ ��ü�� �д´�.
			if(o instanceof InputStream) {
				InputStream is = (InputStream) o; //InputStream ��ü�� ��ȯ�Ѵ�.
				reader = new BufferedReader(new InputStreamReader(is));
				while((line = reader.readLine()) != null) {//�ؽ�Ʈ������ �д´�.
					System.out.println(line);
				}
			}else if(o instanceof Image) {
				//�̹��� ������ �д´�.
			}
			else {
				//����� �� ��Ÿ������ �д´�.
			}
			System.out.println("��ȯ�� ��ü�� " + o.getClass().getName());
		}catch(MalformedURLException e) {
			System.err.println(args[0] + "�� URL ������ �ƴմϴ�.");
		}catch(IOException e) {
			System.out.println(e);
		}
	}

}
