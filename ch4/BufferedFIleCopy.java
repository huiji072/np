package ch4;

import java.io.*;

public class BufferedFIleCopy {

	public static void main(String[] args) {

		String data;
		
		FileInputStream fin;
		InputStreamReader isr;
		BufferedReader br;
		
		FileOutputStream fout;
		OutputStreamWriter osw;
		BufferedWriter bw;
		
		try {
			fin = new FileInputStream("test.txt");
			isr = new InputStreamReader(fin, "KSC5601");
			br = new BufferedReader(isr);
			
			fout = new FileOutputStream("result.txt");
			osw = new OutputStreamWriter(fout, "KSC5601");
			bw = new BufferedWriter(osw);
			
			while((data = br.readLine()) != null) {
				bw.write(data + "\r\n");
			}
			bw.flush();
			bw.close();
		}catch(IOException e) {
			System.out.println(e);
		}
		
		
		

	}

}
