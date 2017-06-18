//Michael Cho
//CSC 236-64
//Lab 1.3

public class WorkerDemo2{
	public static void main(String [] args)throws InvalidEmployeeNumber, InvalidPayRate, InvalidShift{

	//Employee 1 pass paramaters to constructor
	ProductionWorker2 pw = new ProductionWorker2("Day",16.50,"John Smith","123-A","11-15-2005");
	System.out.println(pw.toString());
	System.out.println();

	//Employee 2 use sets to change existing parameters
	pw.setName("Joan Jones");
	pw.setEmployeeNumber("222-L");
	pw.setHireDate("12-12-2005");
	pw.setShift("Night");
	pw.setPayRate(18.50);
	System.out.println(pw.toString());
	System.out.println();

	}
}