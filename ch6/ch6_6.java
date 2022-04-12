package ch6;

import java.io.*;
import java.net.*;

public class ch6_6 {

	public static void main(String[] args) {

		String line;
		URL u;
		if(args.length > 0) {
			try {
				u = new URL(args[0]); //args[0]은 URL이며 객체를 생성함
				try {
					InputStream ls = u.openStream(); //입력 스트림 객체 생성
					Reader isr = new InputStreamReader(ls);
					BufferedReader br = new BufferedReader(isr);
					while((line = br.readLine()) != null)//URL파일을 한 줄씩 읽는다.
						System.out.println(line);
				}catch(Exception e) {
					System.out.println(e);
				}
			}catch(MalformedURLException e) {
				System.out.println(args[0] + "은 URL 형식이 아닙니다.");
			}
			
		}

	}

}
