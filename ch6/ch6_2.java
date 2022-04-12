package ch6;

import java.net.*;
//�μ��� ������ ��������, ȣ��Ʈ�̸� �� �����̸��� �����Ͽ� URL��ü�� �����ϴ� ��
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
			System.out.println("������ URL�� ã�� �� �����ϴ�.");
		}

	}

}
