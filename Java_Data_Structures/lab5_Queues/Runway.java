//Michael Cho
//CSC 236-64
//Lab 5.1

public class Runway
{
	private ArrayQueue<Airline> runwayqueue;
	private double runwaytime;

	//default constructor
	public Runway()
	{
		runwayqueue=new ArrayQueue<Airline>(1);
		runwaytime=0;
	}

	//overloaded constructor
	public Runway(int capacity, double inrunwaytime)
	{
		this.runwayqueue=new ArrayQueue<Airline>(capacity);
		this.runwaytime=inrunwaytime;
	}

	//access the runwayqueue
	public ArrayQueue<Airline> getrunwayqueue()
	{
		return runwayqueue;
	}

	//check how long Airline been in queue
	public double getrunwaytime()
	{
		return runwaytime;
	}

	//change arrayqueue
	public void setrunwayqueue(ArrayQueue<Airline> inrunwayqueue)
	{
		runwayqueue=inrunwayqueue;
	}

	//change entertime
	public void setrunwaytime(double inrunwaytime)
	{
		runwaytime=inrunwaytime;
	}

	//decrement current runwaytime
	public void updaterunway()
	{
		runwaytime--;
	}
}
