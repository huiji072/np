package extendsTest;

public class test2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Person a = new Person();
		Person b = new Student();
		Student c = new Student();
		a.study();
		b.study();
		c.study();
		

	}

}

class Person{
	void study() {
		System.out.println("�λ�����");
	}
}

class Student extends Person{
	void study() {
		System.out.println("�б�����");
	}
}
