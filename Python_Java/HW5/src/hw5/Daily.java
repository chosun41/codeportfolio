package hw5;
import java.text.DateFormat;
import java.text.ParseException; 
import java.text.SimpleDateFormat; 
import java.util.Date; 

public class Daily extends Appointment{
	
	private Date from;
	private Date to;
	
	/* constructor that inherits from from superclass Appointment
	 * and parses a from and to date string to make into Date classes
	 * 
	 * @param string for description and a from and two string in mm/dd/yyyy format
	 * to parse in Date class
	 */
	public Daily(String descr, String f, String t) throws ParseException{
		super(descr);
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		this.from=dateFormat.parse(f);
		this.to=dateFormat.parse(t);
	}
	
	/* makes year month date into a string date to be parsed into Date class
	 * this conditional is an inclusive range where from<=chkdt<=to
	 * 
	 * @param integer year month day
	 * @return true or false
	 */
	public boolean occursIn(int year, int month, int day) throws ParseException{
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date chkdt = dateFormat.parse(String.format("%d/%d/%d", month, day, year));
		if(!from.after(chkdt) && !to.before(chkdt))
			return true;
		else
			return false;
	}
	
	public String toString(){
		String s="";
		s=String.format("Appointment Frequency: Daily, Description: %s,"
				+ " From Date: %s, To Date: %s",getdescr(),from, to);
		return s;
	}
}
