package extendsTest;

public class test1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SuperClass s = new SubClass();
	}

}
class SuperClass{ 
	SuperClass( ){ 
		System.out.print("Super"); 
		}
}
class SubClass extends SuperClass{ 
	SubClass( ){ 
		System.out.print("Sub"); 
		}
}