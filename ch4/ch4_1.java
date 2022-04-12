package ch4;

import java.io.*;

public class ch4_1 {

	public static void main(String[] args) {

		Writer w = null;
		Reader r;
		FileWriter fw1, fw2, fw3, fw4;
		FileReader fr;
		String filename = "test.txt";
		boolean append = true;
		File file = new File("test2.txt");
		FileDescriptor fd = new FileDescriptor();
		
		try {
			fw1 = new FileWriter(filename);
			fw2 = new FileWriter(filename, append);
			fw3 = new FileWriter(file);
			fw4 = new FileWriter(fd);
			
			fw1.write(filename);
			fw2.write(filename);
			
			
		}catch(IOException e) {
			System.out.println(e);
		}
		


	}

}
