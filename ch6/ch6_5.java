package ch6;

import java.net.*;

public class ch6_5 {

	public static void main(String[] args) {
		
		for(int i = 0 ; i < args.length ; i++) {
			try {
				URL u = new URL(args[i]);
				System.out.println("u�� URL�� " + u);
				System.out.println("u�� ���������� " + u.getProtocol());
				System.out.println("u�� ȣ��Ʈ�� " + u.getHost());
				System.out.println("u�� ��Ʈ��ȣ�� " + u.getPort());
				System.out.println("u�� �����̸��� " + u.getFile());
				System.out.println("u�� ���� �� ������ġ�� " + u.getRef());
			}catch(MalformedURLException e) {
				System.out.println(args[i] + "�� URL������ �ƴմϴ�.");
			}
		}

	}

}
