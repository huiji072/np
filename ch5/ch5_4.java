package ch5;

import java.io.*;
import java.net.*;

public class ch5_4 {

	public static void main(String[] args) {

		String hostname;
		String hostname2 = "www.naver.com";
		InetAddress addr2;
		BufferedReader br;
		br = new BufferedReader(new InputStreamReader(System.in));

		System.out.println("호스트이름 또는 IP주소를 입력하세요.");
		try {
			if((hostname = br.readLine()) != null){
				InetAddress addr = InetAddress.getByName(hostname);
				addr2 = InetAddress.getByName(hostname2);
//				equals
				if(addr.equals(addr2)) {
					System.out.println("두 개의 IP주소가 같습니다.");
				}else {
					System.out.println("두 개의 IP주소가 다릅니다.");
				}
				
//				hashCode
				System.out.println("hashCode : " + addr.hashCode());
				
//				toString
				System.out.println("호스트이름/Ip주소 : " + addr.toString());
			}
		}catch(UnknownHostException e) {
			System.out.println(e);
		}catch(IOException e) {
			System.out.println(e);
		}
		
	}

}
