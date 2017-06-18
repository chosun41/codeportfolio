//Michael Cho
//CSC 236-64
//Lab 1.1

public class ParkedCar{

	private String Make, Model, Color, LicenseNumber;
	private int MinutesParked;

	//constructor
	public ParkedCar(String Make, String Model, String Color, String LicenseNumber, int MinutesParked)
	{
		this.Make=Make;
		this.Model=Model;
		this.Color=Color;
		this.LicenseNumber=LicenseNumber;
		this.MinutesParked=MinutesParked;
	}

	//copy constructor
    public ParkedCar(ParkedCar a){
		Make=a.Make;
		Model=a.Model;
		Color=a.Color;
		LicenseNumber=a.LicenseNumber;
		MinutesParked=a.MinutesParked;
	}

	//change Make
	public void setMake(String a)
	{Make=a;
	}

	//change Model
	public void setModel(String a)
	{Model=a;
	}

	//change Color
	public void setColor(String a)
	{Color=a;
	}

	//change LicenseNumber
	public void setLicenseNumber(String a)
	{LicenseNumber=a;
	}

	//change MinutesParked
	public void setMinutesParked(int a)
	{MinutesParked=a;
	}

	//return Make
	public String getMake()
	{return Make;
	}

	//return Color
	public String getColor()
	{return Color;
	}

	//return LicenseNumber
	public String getLicenseNumber()
	{return LicenseNumber;
	}

	//return MinutesParked
	public int getMinutesParked()
	{return MinutesParked;
	}

	//return parameters of constructor as a string
	public String toString()
	{return "ParkedCar: "+ Make+", "+Model+", "+Color+", license no. "+LicenseNumber+", "+MinutesParked+" mins. Parked";
	}
}
