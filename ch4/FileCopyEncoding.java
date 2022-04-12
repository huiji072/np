package ch4;

import java.io.*;

public class FileCopyEncoding{

	public static void main(String[] args) throws IOException{

		int numberRead;
		char[] buffer = new char[16];
		if(args.length != 4) {
			System.out.println("입력인코딩 입력파일이름 출력인코딩 출력파일이름을 지정하세요!!");
			return;		
			}
		FileInputStream fileIn = new FileInputStream(args[1]);
		FileOutputStream fileOut = new FileOutputStream(args[3]);
		InputStreamReader isr = new InputStreamReader(fileIn, args[0]);
		OutputStreamWriter osr = new OutputStreamWriter(fileOut, args[2]);
		
		while((numberRead = isr.read(buffer)) > -1)
			osr.write(buffer, 0, numberRead);
		osr.close();
		isr.close();
	}

}
