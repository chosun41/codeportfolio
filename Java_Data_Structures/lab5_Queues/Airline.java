//Michael Cho
//CSC 236-64
//Lab 5.1

public class Airline
{
	private double entertime;

	//default constructor
	public Airline()
	{
		entertime=0;
	}

	//overloaded constructor
	public Airline(double inentertime)
	{
		this.entertime=inentertime;
	}

	//check how long Airline been in queue
	public double getentertime()
	{
		return entertime;
	}


	//change entertime
	public void setentertime(double inentertime)
	{
		entertime=inentertime;
	}

}
