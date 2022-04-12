package ch6;

import java.net.*;

public class ch6_5 {

	public static void main(String[] args) {
		
		for(int i = 0 ; i < args.length ; i++) {
			try {
				URL u = new URL(args[i]);
				System.out.println("u의 URL은 " + u);
				System.out.println("u의 프로토콜은 " + u.getProtocol());
				System.out.println("u의 호스트은 " + u.getHost());
				System.out.println("u의 포트번호은 " + u.getPort());
				System.out.println("u의 파일이름은 " + u.getFile());
				System.out.println("u의 파일 내 참조위치는 " + u.getRef());
			}catch(MalformedURLException e) {
				System.out.println(args[i] + "는 URL형식이 아닙니다.");
			}
		}

	}

}
