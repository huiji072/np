package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;

//루프백어드레스
//예제 5.6에 추가하여 로컬호스트의 루프백 주소를 출력할 수 있도록 하시오 
//원격호스트가 여러 개의 주소를 갖는 경우 모든 주소를 함께 출력하시오
//abstract입력된 원격 호스트의 IP 주소와
//메소드 'static InetAddress getByNameAddress(byte[] addr)를 이용하여
//새로운 InetAddress객체를 생성한 후 두 객체가 같은 지 비교하여 그 결과를 출력하시오
public class ch5_1 {
	static InetAddress machine, machine2;
	public static void main(String[] args) {
		
		String hostname;
		BufferedReader br;
		printLoacalAddress(); //로컬호스트의 이름 및 IP 주소 출력
		br = new BufferedReader(new InputStreamReader(System.in));
		try {
			do {
				System.out.println("호스트 이름 및 IP 주소를 입력하세요");
				if((hostname = br.readLine()) != null)
					printRemoteAddress(hostname); // 원격호스트의 주소 출력
			}while(hostname != null);
			System.out.println("프로그램을 종료합니다.");
		}catch(IOException ex) {
			System.out.println("입력 에러!");
		}
	}


	static void printLoacalAddress() {
		
		try {
			InetAddress myself = InetAddress.getLocalHost();
			System.out.println("로컬 호스트 이름 " + myself.getHostName());
			System.out.println("로컬 IP 주소 " + myself.getHostAddress());
			System.out.println("로컬 호스트 class : " + ipClass(myself.getAddress()));
			System.out.println("로컬 호스트 InetAddress : " + myself.toString());
			System.out.println("로컬 호스트의 루프백 주소 : " + myself.getLoopbackAddress()); //로컬호스트의 루프백주소
		}catch(UnknownHostException ex) {
			System.out.println(ex);
		}
	}

	static void printRemoteAddress(String hostname) {
			
			try {
				machine = InetAddress.getByName(hostname);
//				System.out.println(machine.getAddress());
				byte[] m2 = new byte[] {(byte) 210,119,(byte) 132,43}; //"www.sc.ac.kr"
				machine2 = InetAddress.getByAddress(m2);
				System.out.println("호스트를 찾고 있습니다. " + hostname + "....");
				System.out.println("원격 호스트 이름 : " + machine.getHostName());
				System.out.println("원격 IP 주소 " + machine.getHostAddress());
				System.out.println("원격 호스트 class : " + ipClass(machine.getAddress()));
				System.out.println("원격 호스트 InetAddress : " + machine.toString());
//				원격호스트의 모든 주소
				InetAddress[] addresses = InetAddress.getAllByName(hostname);
				for(int i = 0;i<addresses.length;i++) {
					System.out.println("원격 호스트의 모든 주소 " + addresses[i]);
				}
				if(machine2.equals(machine)) {
					System.out.println("두 객체가 같습니다.");
				}else {
					System.out.println("두 객체가 다릅니다.");
				}
				
			}catch(UnknownHostException ex) {
				System.out.println(ex);
			}
			
		}

		
	static char ipClass(byte[] ip) {
		int highByte = 0xff & ip[0];
		return(highByte<128) ? 'A' : (highByte < 192) ? 'B' : (highByte<224) ? 'C' : 
			(highByte<240) ? 'D' : 'E';
		
	}

}
