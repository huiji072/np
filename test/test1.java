package test;

import java.io.*;

public class test1 {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

			int data;
			char[] buffer = new char[80];
			File file = new File("Exam_01.txt");
			FileReader fr = new FileReader(file);
			FileWriter fw = new FileWriter("Exam_02_1.txt");
			BufferedReader br = new BufferedReader(fr);
			while((data = br.read(buffer)) > -1) {
				fw.write(buffer,0,data);
			}
			fw.flush();
			fw.close();
		

	}

}
