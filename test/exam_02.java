package test;

import java.io.*;

public class exam_02 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try {

			int data;
			char[] buffer = new char[80];
			File file = new File("Exam_01.txt");
			FileReader fin =  new FileReader(file);
			FileWriter fw = new FileWriter("Exam_02_1.txt");
			
			BufferedReader br = new BufferedReader(fin);
			while((data=br.read())>-1) {
				fw.write(data);
			}
			fin.close();
			fw.close();
		}catch(Exception e) {
			System.out.println(e);
		}

	}

}
