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
		System.out.println("인생공부");
	}
}

class Student extends Person{
	void study() {
		System.out.println("학교공부");
	}
}
