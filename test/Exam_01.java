package test;

import java.io.*;

public final class Exam_01 {

	public static void main(String[] args) throws IOException{
		// TODO Auto-generated method stub
		FileOutputStream fout = new FileOutputStream("Exam_01.txt");
		
		func_one(System.in, fout);

	}
	

	public static void func_one(InputStream in, FileOutputStream fout) throws IOException{
		int i;
		byte[] buffer = new byte[256];
		BufferedOutputStream bos = new BufferedOutputStream(fout);
		while((i = in.read(buffer)) > -1) {
			bos.write(buffer,0,i);
		}
		bos.flush();
	}

}
