package ch4;

import java.io.*;

public class ch4_4 {

	public static void main(String[] args) throws IOException {

		int num;
		char[] buffer = new char[80];
		FileReader fr = new FileReader("test2.txt");
		FileWriter fw = new FileWriter("copy.txt");

		while((num = fr.read(buffer)) > -1) {
			fw.write(buffer, 0, buffer.length);
		}
		fr.close();
		fw.close();

	}

}
