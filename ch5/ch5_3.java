package ch5;

import java.net.*;

public class ch5_3 {

	public static void main(String[] args) {

		try {
			InetAddress local = InetAddress.getLocalHost();
			byte[] address = local.getAddress(); //IP주소가 바이트 배열 저장됨
			System.out.println("사용 중인 호스트의 IP 주소는 ");
			
			for(int i = 0 ; i < address.length ; i++) {
				int unsigned = address[i]<0 ? address[i]+256 : address[i];
				System.out.print(unsigned+".");
			}
			System.out.println();
		}catch(UnknownHostException e) {
			System.out.println("로컬호스트의 IP주소를 알 수 없습니다.");
		}
	}

}
