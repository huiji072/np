package test;

import java.net.*;

public class ReadFieldOfDatagram {

	public static void main(String[] args) {

		String s = "This is a sample data";
		byte[] data = s.getBytes();
		String retrivedData;
		
		try {
			InetAddress ia = InetAddress.getByName("www.ssc.ac.kr");
			int port = 7;
			DatagramPacket dp = new DatagramPacket(data, data.length, ia, port);
			System.out.println("이 패킷의 목적지 주소는 " + dp.getAddress() + 
					", 포트번호는 " + dp.getPort());
			System.out.println("패킷에 포함된 바이트 데이터 수는 " + dp.getLength());
			retrivedData = new String(dp.getData(), dp.getOffset(), dp.getLength());
			System.out.println("데이터그램으로부터 읽혀진 데이터는 " + retrivedData);
		}catch(UnknownHostException e) {
			System.out.println(e);
		}

	}

}
