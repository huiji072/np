package ch6;

import java.io.*;
import java.net.*;
//URL��ü�� �����ϰ� ������ ��ü�� ȭ�鿡 ���
public class ch6_1 {

	public static void main(String[] args) {

		URL webURL, ftpURL, ldapURL;
		try {
			webURL = new URL("http://www.ssc.ac.kr/index.html");
			System.out.println(webURL);
			
			ftpURL = new URL("ftp://www.ssc.ac.kr/public/");
			System.out.println(ftpURL);
			
			ldapURL = new URL("ldap://www.ssc.ac.kr/"); //���ܸ� �߻���Ų��.
			System.out.println(ldapURL);
		}catch(MalformedURLException e) {
			System.out.println("������ URL�� ã�� �� �����ϴ�.");
		}
		
	}

}
