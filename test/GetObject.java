package test;

import java.io.*;
import java.net.*;

public class GetObject {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		URL u;
		String line, urlstring;
		BufferedReader br, reader;
		br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("url�� �Է��Ͻʽÿ�");
		
		try {
			urlstring = br.readLine();
			u = new URL(urlstring);
			Object o = u.getContent();
			if(o instanceof InputStream) {
				InputStream is = (InputStream) o;
				reader = new BufferedReader(new InputStreamReader(is));
				while((line = reader.readLine()) != null)
					System.out.println(line);
			}else if(o instanceof Image) {
				//
			}
			System.out.println("��ȯ�� ��ü��" + o.getClass().getName());
		}

	}

}
