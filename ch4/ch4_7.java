package ch4;

import java.io.*;

public class ch4_7 {

	public static void main(String[] args) throws IOException{
		
		String text = "�ູ�� �ð� ��������";
		FileOutputStream fos = new FileOutputStream("test7.txt"); //����Ʈ��½�Ʈ��
		OutputStreamWriter osw = new OutputStreamWriter(fos, "KSC5601"); //��������½�Ʈ��
		
		osw.write(text, 0, text.length());
		osw.flush();
		osw.close();

	}

}
