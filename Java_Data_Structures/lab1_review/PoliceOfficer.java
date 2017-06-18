//Michael Cho
//CSC 236-64
//Lab 1.1

public class PoliceOfficer{
	private String Name, BadgeNumber;

	//constructor
	public PoliceOfficer(String Name, String BadgeNumber)
	{this.Name=Name;
	this.BadgeNumber=BadgeNumber;
	}

	//copy constructor
	public PoliceOfficer(PoliceOfficer a)
	{
		Name=a.Name;
		BadgeNumber=a.BadgeNumber;
	}

	//change Name
	public void setName(String a)
	{Name=a;
	}

	//change BadgeNumber
	public void setBadgeNumber(String a)
	{BadgeNumber=a;
	}

	//return Name
	public String getName()
	{return Name;
	}

	//return BadgeNumber
	public String getBadgeNumber()
	{return BadgeNumber;
	}

	//difference between minutes purchase and parked to be
	//passed onto ParkingTicket class
	public int patrol(int a, int b)
	{int difference=a-b;
	return difference;
	}

	//string of PoliceOfficer parameters
	public String toString()
	{return "PoliceOfficer: "+ Name+","+ " badge no. "+BadgeNumber;
	}
}
