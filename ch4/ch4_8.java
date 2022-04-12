package ch4;

import java.io.*;

public class ch4_8 {

	public static void main(String[] args) throws IOException {
		int bytesRead;
		char[] buffer = new char[128];
		FileInputStream fis = new FileInputStream("test7.txt");
		InputStreamReader isr = new InputStreamReader(fis, "KSC5601");
		
		while((bytesRead = isr.read(buffer,0,buffer.length)) != -1)
			System.out.println(buffer);
	}

}
