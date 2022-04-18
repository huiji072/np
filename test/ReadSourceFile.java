package test;

import java.io.*;
import java.net.*;

public class ReadSourceFile {

	public static void main(String[] args) {

		String line;
		URL u;
		
		if(args.length > 0) {
			try {
				u = new URL(args[0]);
				try {
					InputStream is = u.openStream();
					Reader isr = new InputStreamReader(is);
					BufferedReader br = new BufferedReader(isr);
					while((line = br.readLine()) != null) {
						System.out.print(line);
					}
				}catch(Exception e) {
					System.out.println(e);
				}
			}catch(MalformedURLException e) {
				System.out.println(e);
			}
		}
		

	}

}
