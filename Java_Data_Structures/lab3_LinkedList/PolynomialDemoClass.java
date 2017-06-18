//Michael Cho
//CSC 236-64
//Lab 3.1

public class PolynomialDemoClass {
	public static void main(String [] args){

		PolynomialDataStrucClass p1 = new PolynomialDataStrucClass();
		p1.addPolyNodeFirst(4,3);
		p1.addPolyNodeLast(3,2);
		p1.addPolyNodeLast(-5,0);
		System.out.println("p1: " + p1);

		PolynomialDataStrucClass p2 = new PolynomialDataStrucClass();
		p2.addPolyNodeFirst(3,5);
		p2.addPolyNodeLast(4,4);
		p2.addPolyNodeLast(1,3);
		p2.addPolyNodeLast(-4,2);
		p2.addPolyNodeLast(4,1);
		p2.addPolyNodeLast(2,0);
		System.out.println("p2: " + p2);

		System.out.println("p1 + p2: " + p1.addPolynomials(p2, true));

		PolynomialDataStrucClass p3 = new PolynomialDataStrucClass();
		p3.addPolyNodeFirst(-5,0);
		p3.addPolyNodeLast(3,2);
		p3.addPolyNodeLast(4,3);
		System.out.println("p3: " + p3);

		PolynomialDataStrucClass p4 = new PolynomialDataStrucClass();
		p4.addPolyNodeFirst(-4,0);
		p4.addPolyNodeLast(4,3);
		p4.addPolyNodeLast(5,4);
		System.out.println("p4: " + p4);

		System.out.println("p3 + p4: " + p3.addPolynomials(p4, false));

	}
}