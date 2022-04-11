package ch5;

import java.net.*;

public class ch5_1 {

	public static void main(String[] args) {

		if(args.length != 1) {
			System.out.println("호스트 이름을 입력하세요!");
			return;
		}
		
//		문자열 호스트주소/IP주소 
		try {
			InetAddress address1 = InetAddress.getByName(args[0]);
			System.out.println(address1);
		}catch(UnknownHostException e) {
			System.out.println("지정된 호스트를 찾을 수 없습니다.");
		}
		
//		인수로 지정한 호스트 이름에 해당하는 모든 InetAddress 배열 객체
		try {
			InetAddress[] address2 = InetAddress.getAllByName(args[0]);
			for(int i = 0 ; i < address2.length ; i++) {
				System.out.println(address2[i]);
			}
		}catch(UnknownHostException e) {
			System.out.println("지정된 호스트를 찾을 수 없습니다.");
		}
		
//		현재 프로그램이 실행되고 있는 로컬컴퓨터의 InetAddress 객체를 반환한다.
		try {
			InetAddress address3 = InetAddress.getLocalHost();
			System.out.println(address3);
		}catch(UnknownHostException e) {
			System.out.println("지정된 호스트를 찾을 수 없습니다.");
		}
	}

}
