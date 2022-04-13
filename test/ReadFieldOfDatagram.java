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
			System.out.println("�� ��Ŷ�� ������ �ּҴ� " + dp.getAddress() + 
					", ��Ʈ��ȣ�� " + dp.getPort());
			System.out.println("��Ŷ�� ���Ե� ����Ʈ ������ ���� " + dp.getLength());
			retrivedData = new String(dp.getData(), dp.getOffset(), dp.getLength());
			System.out.println("�����ͱ׷����κ��� ������ �����ʹ� " + retrivedData);
		}catch(UnknownHostException e) {
			System.out.println(e);
		}

	}

}
