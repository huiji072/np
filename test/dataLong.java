package test;

import java.io.*;
import java.net.InetAddress;

public class dataLong {

	public static void main(String[] args) {

		try {
			long dataLong;
			FileInputStream fin = new FileInputStream("Exam_01.txt");
			DataInputStream dis = new DataInputStream(fin);
			dataLong = dis.readLong();
			InetAddress ia = InetAddress.getLocalHost()
			System.out.println(dataLong);
		}catch(IOException e) {
			System.out.println(e);
		}

	}

}
