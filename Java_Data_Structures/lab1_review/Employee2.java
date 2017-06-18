//Michael Cho
//CSC 236-64
//Lab 1.2,1.3

public class Employee2{

	private String Name, EmployeeNumber, HireDate;

	//default constructor
	public Employee2()
	{
	}

	//overloaded constructor with invalid employee exception
	public Employee2(String Name, String EmployeeNumber, String HireDate)throws InvalidEmployeeNumber
	{this.Name=Name;
	if (isValidEmpNum(EmployeeNumber)==false)
			throw new InvalidEmployeeNumber();
		else
	this.EmployeeNumber=EmployeeNumber;
	this.HireDate=HireDate;
	}

	//change name
	public void setName(String a)
	{Name=a;
	}

	//change Employee Number, checks for invalid employee exception
	public void setEmployeeNumber (String a) throws InvalidEmployeeNumber
	{if (isValidEmpNum(a)==false)
		throw new InvalidEmployeeNumber();
	else
		EmployeeNumber=a;
	}

	//change HireDate
	public void setHireDate (String a)
	{HireDate=a;
	}

	//return Name
	public String getName()
	{return Name;
	}

	//return EmployeeNumber
	public String getEmployeeNumber()
	{return EmployeeNumber;
	}

	//return HireDate
	public String getHireDate()
	{return HireDate;
	}

	//boolean for invalid employee that is used in constructor and
	//setEmployeeNumber for throwing exceptions
	public boolean isValidEmpNum(String a)throws InvalidEmployeeNumber
	{	boolean valid = true;
		if ((!Character.isDigit(a.charAt(0)))||
             (!Character.isDigit(a.charAt(1)))||
             (!Character.isDigit(a.charAt(2)))||
             (a.charAt(3) != '-') ||
             (Character.toUpperCase(a.charAt(4)) < 'A')||
             (Character.toUpperCase(a.charAt(4)) > 'M'))
         	valid = false;

         return valid;
	}

	//string of employee parameters
	public String toString()
	{return Name + ",Employee no.: " + EmployeeNumber + ",\nHire: " + HireDate;
	}

}