package ch6;

import java.net.*;
//인수로 별도로 프로토콜, 호스트이름 및 파일이름을 전달하여 URL객체를 생성하는 것
public class ch6_2 {

	public static void main(String[] args) {
		
		URL webURL, ftpURL, ldapURL;
		try {
			webURL = new URL("http", "www.ssc.ac.kr", "/index.html");
			System.out.println(webURL);
			
			ftpURL = new URL("ftp", "www.ssc.ac.kr", "/public");
			System.out.println(ftpURL);
			
			ldapURL = new URL("ldap", "www.ssc.ac.kr", "/");
			System.out.println(ldapURL);
			
		}catch(MalformedURLException e) {
			System.out.println("지정된 URL를 찾을 수 없습니다.");
		}

	}

}
