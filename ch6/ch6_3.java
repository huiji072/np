package ch6;

import java.net.*;
//첫번째 파일에 대한 URL를 생성한 후, 다른 파일들에 대한 URL를 생성하기 위해서는
//파일들의 이름만 바꾸어 가면서 생성자 메소드를 실행하면 된다.
public class ch6_3 {

	public static void main(String[] args) {

		URL u1, u2;
		try {
			u1 = new URL("http://www.ssc.ac.kr/index.html");
			u2 = new URL(u1, "haksa.html");
			System.out.println("u1 객체의 URL은 " + u1);
			System.out.println("u2 객체의 URL은 " + u2);
		}catch(MalformedURLException e) {
			System.out.println(e);
		}

	}

}
