package ch4;

import java.io.*;

public class ch4_3 {

	public static void main(String[] args) throws IOException {
		
		int numberRead;
		String data;
		char[] buffer = new char[80];
		FileReader fr = new FileReader("test2.txt");
		
		while((numberRead = fr.read(buffer)) > -1) {
			System.out.println(buffer);
		}
		fr.close();

	}

}
