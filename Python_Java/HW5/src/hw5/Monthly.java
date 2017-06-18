package hw5;
import java.text.DateFormat;
import java.text.ParseException; 
import java.text.SimpleDateFormat; 
import java.util.Date; 
import java.util.Calendar;

public class Monthly extends Appointment {
	
	private Date from;
	private Date to;
	
	/* constructor that inherits from the superclass Appointment
	 * and takes day, beginning year and month and ending year and month
	 * monthly appointments occurs on same day of each month in the interim period
	 * 
	 * @param string for description and a from and two year and month, as well as 
	 * common day to place into a string in mm/dd/yyyy format and parse into Date format
	 */
	public Monthly(String descr, int day, int fyr, int fmnth, int tyr, int tmnth) 
			throws ParseException{
		super(descr);
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		this.from=dateFormat.parse(String.format("%d/%d/%d", fmnth, day, fyr));
		this.to=dateFormat.parse(String.format("%d/%d/%d", tmnth, day, tyr));
	}
	
	/* makes year month date into a string date to be parsed into Date class
	 * this conditional is an inclusive range where from<=chkdt<=to and 
	 * sees if the day is the same
	 * 
	 * @param integer year month day
	 * @return true or false
	 */
	public boolean occursIn(int year, int month, int day) throws ParseException{
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date chkdt = dateFormat.parse(String.format("%d/%d/%d", month, day, year));
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.setTime(chkdt);
		cal2.setTime(from);
		if((!from.after(chkdt) && !to.before(chkdt)) && 
				cal1.get(Calendar.DAY_OF_MONTH)==cal2.get(Calendar.DAY_OF_MONTH))
			return true;
		else
			return false;
	}

	public String toString(){
		String s="";
		s=String.format("Appointment Frequency: Monthly, Description: %s,"
				+ " From Date: %s, To Date: %s",getdescr(),from, to);
		return s;
	}
}
