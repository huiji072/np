package ch4;

import java.io.*;

public class AttachLineNumber {

	public static void main(String[] args) {
		String buf;
		FileInputStream fin = null;
		FileOutputStream fout = null;
		
		if(args.length != 2) {
			System.out.println("소스파일 및 대상파일을 지정하십시오. ");
			System.exit(1);
		}
		try {
			fin = new FileInputStream(args[0]);
			fout = new FileOutputStream(args[1]);
		}catch(Exception e) {
			System.out.println(e);
			System.exit(1);
		}
		BufferedReader read = new BufferedReader(new InputStreamReader(fin));
		PrintStream write = new PrintStream(fout);
		int num = 1;
		while(true) {
			try {
				buf = read.readLine();
				if(buf == null) break;
			}catch(IOException e) {
				System.out.println(e);
				break;
			}
			buf = num + " : " + buf;
			write.print(buf);
			num++;
		}
		try {
			fin.close();
			fout.close();
		}catch(IOException e) {
			System.out.println(e);
		}

	}

}
