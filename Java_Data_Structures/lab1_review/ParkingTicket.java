//Michael Cho
//CSC 236-64
//Lab 1.1
//Michael Cho
//CSC 236-64
//Lab 1.1

import java.text.DecimalFormat;

public class ParkingTicket{
	private String Car, Officer;
	private int FineMins;

	//constructor
	public ParkingTicket(String Car, String Officer, int FineMins)
	{this.Car=Car;
	this.Officer=Officer;
	this.FineMins=FineMins;
	}

	//copy constructor
	public ParkingTicket(ParkingTicket a)
	{Car=a.Car;
	Officer=a.Officer;
	FineMins=a.FineMins;
	}

	//calculate Fine, Math.ceil for part of hour counting for whole houor, double because of division
	//$25 for first hour and $10 for each part of hour/hour after
	public double calculateFine()
	{	double Fine;
		double FineHours=Math.ceil(FineMins/60);

		if (FineMins>0){
			Fine=25+(FineHours-1)*10;
		}
		else
		Fine=0;
	return Fine;
	}

	//change Car
	public void setCar(String a)
	{Car=a;
	}

	//change Officer
	public void setOfficer(String a)
	{Officer=a;
	}

	//return Car
	public String getCar()
	{return Car;
	}

	//return Officer
	public String getOfficer()
	{return Officer;
	}

	//return Fine
	public double getFine()
	{return calculateFine();
	}

	//make parameters of Constructor String, Decimal Format for dollar
	public String toString()
		{DecimalFormat dollar = new DecimalFormat("##.00");
			if(FineMins>0)
				return "Car is parked illegally over: " + FineMins + " mins. Fine is: $" + dollar.format(getFine());
		else
				return "No crimes commited";
		}
}
