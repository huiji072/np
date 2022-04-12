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
		System.out.println("URL를 입력하십시오!");
		
		try {
			urlstring = br.readLine(); //URL을 입력받는다.
			u = new URL(urlstring); //u URL 객체를 생성한다.
			Object o = u.getContent(); //URL이 지정하는 파일을 객체로 읽는다.
			if(o instanceof InputStream) {
				InputStream is = (InputStream) o; //InputStream 객체를 변환한다.
				reader = new BufferedReader(new InputStreamReader(is));
				while((line = reader.readLine()) != null) {//텍스트파일을 읽는다.
					System.out.println(line);
				}
			}else if(o instanceof Image) {
				//이미지 파일을 읽는다.
			}
			else {
				//오디오 및 기타파일을 읽는다.
			}
			System.out.println("반환된 객체는 " + o.getClass().getName());
		}catch(MalformedURLException e) {
			System.err.println(args[0] + "는 URL 형식이 아닙니다.");
		}catch(IOException e) {
			System.out.println(e);
		}
	}

}
