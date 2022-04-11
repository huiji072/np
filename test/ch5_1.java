package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;

//�������巹��
//���� 5.6�� �߰��Ͽ� ����ȣ��Ʈ�� ������ �ּҸ� ����� �� �ֵ��� �Ͻÿ� 
//����ȣ��Ʈ�� ���� ���� �ּҸ� ���� ��� ��� �ּҸ� �Բ� ����Ͻÿ�
//abstract�Էµ� ���� ȣ��Ʈ�� IP �ּҿ�
//�޼ҵ� 'static InetAddress getByNameAddress(byte[] addr)�� �̿��Ͽ�
//���ο� InetAddress��ü�� ������ �� �� ��ü�� ���� �� ���Ͽ� �� ����� ����Ͻÿ�
public class ch5_1 {
	static InetAddress machine, machine2;
	public static void main(String[] args) {
		
		String hostname;
		BufferedReader br;
		printLoacalAddress(); //����ȣ��Ʈ�� �̸� �� IP �ּ� ���
		br = new BufferedReader(new InputStreamReader(System.in));
		try {
			do {
				System.out.println("ȣ��Ʈ �̸� �� IP �ּҸ� �Է��ϼ���");
				if((hostname = br.readLine()) != null)
					printRemoteAddress(hostname); // ����ȣ��Ʈ�� �ּ� ���
			}while(hostname != null);
			System.out.println("���α׷��� �����մϴ�.");
		}catch(IOException ex) {
			System.out.println("�Է� ����!");
		}
	}


	static void printLoacalAddress() {
		
		try {
			InetAddress myself = InetAddress.getLocalHost();
			System.out.println("���� ȣ��Ʈ �̸� " + myself.getHostName());
			System.out.println("���� IP �ּ� " + myself.getHostAddress());
			System.out.println("���� ȣ��Ʈ class : " + ipClass(myself.getAddress()));
			System.out.println("���� ȣ��Ʈ InetAddress : " + myself.toString());
			System.out.println("���� ȣ��Ʈ�� ������ �ּ� : " + myself.getLoopbackAddress()); //����ȣ��Ʈ�� �������ּ�
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
				System.out.println("ȣ��Ʈ�� ã�� �ֽ��ϴ�. " + hostname + "....");
				System.out.println("���� ȣ��Ʈ �̸� : " + machine.getHostName());
				System.out.println("���� IP �ּ� " + machine.getHostAddress());
				System.out.println("���� ȣ��Ʈ class : " + ipClass(machine.getAddress()));
				System.out.println("���� ȣ��Ʈ InetAddress : " + machine.toString());
//				����ȣ��Ʈ�� ��� �ּ�
				InetAddress[] addresses = InetAddress.getAllByName(hostname);
				for(int i = 0;i<addresses.length;i++) {
					System.out.println("���� ȣ��Ʈ�� ��� �ּ� " + addresses[i]);
				}
				if(machine2.equals(machine)) {
					System.out.println("�� ��ü�� �����ϴ�.");
				}else {
					System.out.println("�� ��ü�� �ٸ��ϴ�.");
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
