package ch6;

import java.io.*;
import java.net.*;

public class ch6_6 {

	public static void main(String[] args) {

		String line;
		URL u;
		if(args.length > 0) {
			try {
				u = new URL(args[0]); //args[0]�� URL�̸� ��ü�� ������
				try {
					InputStream ls = u.openStream(); //�Է� ��Ʈ�� ��ü ����
					Reader isr = new InputStreamReader(ls);
					BufferedReader br = new BufferedReader(isr);
					while((line = br.readLine()) != null)//URL������ �� �پ� �д´�.
						System.out.println(line);
				}catch(Exception e) {
					System.out.println(e);
				}
			}catch(MalformedURLException e) {
				System.out.println(args[0] + "�� URL ������ �ƴմϴ�.");
			}
			
		}

	}

}
