package test;

import java.net.*;

public class ReadFileOfDatagram {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String s = "tihs is sampele data";
		byte[] data = s.getBytes();
		String receivedData;
		
		try {
			InetAddress ia = InetAddress.getByName("www.ssc.ac.kr");
			int port = 7;
			DatagramPacket dp = new DatagramPacket(data, data.length, ia, port);
			System.out.println("�� ��Ŷ�� ������ �ּҴ�" + dp.getAddress() + ", ��Ʈ��ȣ��" + dp.getPort());
			System.out.println("��Ŷ�� ���Ե� ����Ʈ ������ ���� " + dp.getLength());
			receivedData = new String(dp.getData(), dp.getOffset(), dp.getLength());
			System.out.println("�����ͱ׷����κ��� ������ �����ʹ�" + receivedData);
		}catch(UnknownHostException e) {
			System.out.println(e);
		}

	}

}
