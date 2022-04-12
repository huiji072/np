package ch4;

import java.io.*;

public class ch4_7 {

	public static void main(String[] args) throws IOException{
		
		String text = "행복한 시간 보내세요";
		FileOutputStream fos = new FileOutputStream("test7.txt"); //바이트출력스트림
		OutputStreamWriter osw = new OutputStreamWriter(fos, "KSC5601"); //문자입출력스트림
		
		osw.write(text, 0, text.length());
		osw.flush();
		osw.close();

	}

}
