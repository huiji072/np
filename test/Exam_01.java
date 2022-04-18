package test;

import java.io.*;

public final class Exam_01 {

	public static void main(String[] args) throws IOException{
		// TODO Auto-generated method stub
		FileOutputStream fout = new FileOutputStream("Exam_01.txt");
		BufferedOutputStream bos = new BufferedOutputStream(fout);
		func_one(System.in, bos);

	}

	public static void func_one(InputStream in, BufferedOutputStream bos) throws IOException{
		int i;
		byte[] buffer = new byte[256];
		
		while((i = in.read(buffer)) > -1) {
			bos.write(buffer);
		}
		bos.flush();
	}

}
