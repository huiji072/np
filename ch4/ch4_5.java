package ch4;

import java.io.*;

public class ch4_5 {

	public static void main(String[] args) throws IOException{

		String text = "BufferedWriter 클래스를 이용해서 저장한 파일입니다.";
		FileWriter fw = new FileWriter("test5.txt");
		BufferedWriter bw = new BufferedWriter(fw);
		
		bw.write(text, 0, text.length());
		bw.flush();
		bw.close();

	}

}
