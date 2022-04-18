package test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.*;

public class ReadFileOfDatagram {

	private static InputStream is;
	private static FileOutputStream FileOutputStream;

	public static void main(String[] args) throws IOException {
		

		Exam_01 ex = new Exam_01();
		ex.func_once(is, FileOutputStream);
		
		

	}

}
