//Michael Cho
//CSC 236-64
//Lab 1.1

public class ParkingMeter{
	private int MinutesPurchased;

	//constructor
	public ParkingMeter(int MinutesPurchased)
	{this.MinutesPurchased=MinutesPurchased;
	}

	//change MinutesPurchased
	public void setMinutesPurchased(int a)
	{MinutesPurchased=a;
	}

	//return MinutesPurchased
	public int getMinutesPurchased()
	{return MinutesPurchased;
	}
}