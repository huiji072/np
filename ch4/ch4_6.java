package ch4;

import java.io.*;

public class ch4_6 {

	public static void main(String[] args) {

		String data;
		if(args.length != 1)
			System.err.println("�Է������� �����Ͻʽÿ�");
	
		try {
			FileReader infile = new FileReader(args[0]);
			BufferedReader br = new BufferedReader(infile);
			
			while((data = br.readLine()) != null) {
				System.out.println(data);
			}
		}catch(IOException e) {
			System.err.println(e);
		}

	}

}
