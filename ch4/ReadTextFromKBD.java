package ch4;

import java.io.*;

public class ReadTextFromKBD {

	public static void main(String[] args) throws IOException {

		String text;
		InputStreamReader isr;
		BufferedReader br;
		FileOutputStream fos;
		OutputStreamWriter osw;
		BufferedWriter bw;
		
		isr = new InputStreamReader(System.in);
		br = new BufferedReader(isr);
		
		fos = new FileOutputStream("test.txt");
		osw = new OutputStreamWriter(fos);
		bw = new BufferedWriter(osw);
		
		while((text = br.readLine()) != null) {
			System.out.println(text);
			bw.write(text+"\r\n");
		}
		bw.flush();
		bw.close();

	}

}
