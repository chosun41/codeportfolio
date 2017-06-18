//Michael Cho
//CSC 236-64
//Lab 1.2,1.3

import java.text.DecimalFormat;

//ProductionWorker2 extends Employee to call its parameters in overloaded constructor
//tilized by CreateWorker method in main
public class ProductionWorker2 extends Employee{

	private String Shift;
	private double PayRate;

	//default constructor
	public ProductionWorker2()
	{
	}

	//overloaded constructor with invalid pay rate, shit exceptions, and employee parameters
	public ProductionWorker2(String Shift, double PayRate, String Name, String EmployeeNumber, String HireDate)
	throws InvalidPayRate, InvalidShift, InvalidEmployeeNumber
	{if (Shift=="Day"||Shift=="Night")
		this.Shift=Shift;
	else
		throw new InvalidShift();
	if (PayRate<0)
		throw new InvalidPayRate();
	else
		this.PayRate=PayRate;
	this.setName(Name);
	if (isValidEmpNum(EmployeeNumber)==false)
				throw new InvalidEmployeeNumber();
			else
	this.setEmployeeNumber(EmployeeNumber);
	this.setHireDate(HireDate);
	}

	//change Shift, throws invalidshift if not Day or Night
	public void setShift(String a) throws InvalidShift
	{if (a=="Day"||a=="Night")
		Shift=a;
	else
		throw new InvalidShift();
	}

	//change PayRate, throws invalidpayrate if a negative
	public void setPayRate(double a) throws InvalidPayRate
	{if (a<0)
		throw new InvalidPayRate();
	PayRate=a;
	}

	//return Shift
	public String getShift()
	{return Shift;
	}

	//return PayRate
	public double getPayRate()
	{return PayRate;
	}

	//string of ProductionWorker and Employee parameters, decimal format for dollar
	public String toString()
	{DecimalFormat dollar = new DecimalFormat("##.00");
	return getName() + ",Employee no.: " + getEmployeeNumber() + ",\nHire: " + getHireDate()+",Shift: " + Shift + ",Hourly rate: $" + dollar.format(PayRate);
	}
}