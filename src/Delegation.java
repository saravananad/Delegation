public class Delegation {
	public static void main(String args[]) {
		C111 c111 = new C111();
		System.out.println(c111.m111());

		C112 c112 = new C112();
		System.out.println(c112.m112());
		
		D111 d111 = new D111();
		System.out.println(d111.m111());

		D112 d112 = new D112();
		System.out.println(d112.m112());
	}
}

 class C1 {
	int a1 = 1;

	public int m1() {
		return a1 + p1(100) + q1(100);
	}

	public int p1(int m) {
		return m;
	}
	
	public int q1(int m) {
		return m;
	}
}

 class C11 extends C1 {
	int a11 = 11;

	public int m11() {
		return m1() + q1(200);
	}

	public int p1(int m) {
		return m * a1;
	}

	public int q1(int m) {
		return m + a11;
	}
}

class C111 extends C11 {
	int a111 = 111;

	public int m111() {
		return m1() + m11() + a111;
	}
	
	public int p1(int m) {
		return m * a1 * a11;
	}
}

class C112 extends C11 {
	int a112 = 112;

	public int m112() {
		return m1() + m11() + a112;
	}

	public int p1(int m) {
		return m * a1 * a11 * a112;
		
	}
}


// -------SIMULATING CLASS INHERITANCE BY DELEGATION ---------

/********************************** Interface Definitions **********************************/

interface I1 {
	
	public int m1();
	public int p1(int m);
	public int q1(int m);
	public int getA1();
	
}

interface I11 extends I1 {
	
	public int m11();
	public int getA11();

}

interface I111 extends I11 {

	public int m111();
	public int getA111();

}

interface I112 extends I11 {

	public int m112();
	public int getA112();

}

/************************************ Class Definitions ************************************/

class D1 implements I1 {
	
	protected int a1 = 1;
	I11 subclassInstance = null;
	
	public D1() {}
	
	public D1(I11 instance) {
		subclassInstance = instance;
	}

	@Override
	public int m1() {
		int p1 = subclassInstance == null ? p1(100) : subclassInstance.p1(100);
		int q1 = subclassInstance == null ? q1(100) : subclassInstance.q1(100);
		return a1 + p1 + q1;
	}

	@Override
	public int p1(int m) {
		return m;
	}
	
	@Override
	public int q1(int m) {
		return m;
	}

	@Override
	public int getA1() {
		return a1;
	}
}

class D11 implements I11 {

	protected int a11 = 11;
	I1 parentInstance = null;
	I11 subclassInstance = null;
	
	public D11() {
		parentInstance = new D1(this);
	}
	
	public D11(I11 instance) {
		subclassInstance = instance;
		parentInstance = new D1(this);
	}
	
	@Override
	public int m1() {
		return parentInstance.m1();
	}
	
	@Override
	public int m11() {
		return m1() + q1(200);
	}

	@Override
	public int p1(int m) {
		if(subclassInstance != null) {
			return subclassInstance.p1(m);
		} else {  
			return m * getA1();
		
		}
	}

	@Override
	public int q1(int m) {
		return m + a11;
	}

	@Override
	public int getA1() {
		return parentInstance.getA1();
	}

	@Override
	public int getA11() {
		return a11;
	}
}

 
class D111 implements I111 { 
	protected int a111 = 111;
	I11 parentInstance = null;
	
	public D111() {
		parentInstance = new D11(this);
	}

	@Override
	public int m1() {
		return parentInstance.m1();
	}
	
	@Override
	public int m11() {
		return parentInstance.m11();
	}
	
	@Override
	public int m111() {
		return m1() + m11() + a111;
	}
	
	@Override
	public int p1(int m) {
		return m * getA1() * getA11();
	}

	@Override
	public int q1(int m) {
		return parentInstance.q1(m);
	}

	@Override
	public int getA1() {
		return parentInstance.getA1();
	}
	
	@Override
	public int getA11() {
		return parentInstance.getA11();
	}

	@Override
	public int getA111() {
		return a111;
	}

}

class D112 implements I112 {
	protected int a112 = 112;
	I11 parentInstance = null;
	
	public D112() {
		parentInstance = new D11(this);
	}

	@Override
	public int m112() {
		return m1() + m11() + a112;
	}

	@Override
	public int p1(int m) {
		return m * getA1() * getA11() * a112;
	}

	@Override
	public int m11() {
		return parentInstance.m11();
	}

	@Override
	public int m1() {
		return parentInstance.m1();
	}

	@Override
	public int q1(int m) {
		return parentInstance.q1(m);
	}

	@Override
	public int getA1() {
		return parentInstance.getA1();
	}
	
	@Override
	public int getA11() {
		return parentInstance.getA11();
	}

	@Override
	public int getA112() {
		return a112;
	}

}