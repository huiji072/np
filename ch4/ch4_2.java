package ch4;

import java.io.*;

public class ch4_2 {

	public static void main(String[] args) throws IOException {

		String text = "�ѱ� ���� �����Դϴ�.";
		FileWriter fw = new FileWriter("test2.txt");
		fw.write(text, 0, text.length());
		fw.flush();
		fw.close();
		
	}

}
