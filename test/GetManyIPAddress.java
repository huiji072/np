package test;

import java.net.*;

public class GetManyIPAddress {

	public static void main(String[] args) {

		if(args.length != 1) {
			System.out.println("호스트 이름을 입력하세요");
			return;
		}
		
		try {
			InetAddress[] ia = InetAddress.getAllByName(args[0]);
			
			for(int i = 0 ; i < ia.length ; i++) {
				System.out.println(ia[i]);
			}
			
		}catch(UnknownHostException e) {
			System.out.println(e);
		}

	}

}
