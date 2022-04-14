package test;

import java.net.*;

public class GetIPAddress {

	public static void main(String[] args) {

		//멍령어 라인에서 호스트 이름 입력;
		if(args.length != 1) {
			System.out.println("호스트 이름을 입력하세요");
			return;
		}
		//호스트이름/주소 출력
		
		try {
			InetAddress ia = InetAddress.getByName(args[0]);
			System.out.println(ia);
		}catch(UnknownHostException e) {
			System.out.println(e);
		}

	}

}
