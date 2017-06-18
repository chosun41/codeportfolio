package hw5;
import java.text.DateFormat;
import java.text.ParseException; 
import java.text.SimpleDateFormat; 
import java.util.Date; 

public class Onetime extends Appointment{
	
	private Date onetmdt;

	/* overloaded constructor that inherits from the superclass Appointment
	 * and parses string for date from Onetime class
	 * 
	 * @param string for description and string in mm/dd/yyyy format to parse in Date class
	 */
	public Onetime(String descr, String dtstr) throws ParseException{
		super(descr);
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		this.onetmdt=dateFormat.parse(dtstr);
	}
	
	/* Accessor method to return the one time date.
	 * 
	 * @param none
	 * @return onetmdt
	 */
	public Date getonetmdt(){
		return onetmdt;
	}
	
	/*boolean method that first transforms integers year, month, and day into a string
	 * and parses into Date format and checks if this is equal to the onetime appointment
	 * 
	 * @param integer year month day
	 * @return true or false
	 */
	public boolean occursIn(int year, int month, int day) throws ParseException{
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date chkdt = dateFormat.parse(String.format("%d/%d/%d", month, day, year));
		if(chkdt.equals(this.onetmdt))
			return true;
		else
			return false;
	
	}
	
	public String toString(){
		String s="";
		s=String.format("Appointment Frequency: One time, Description: %s,"
				+ " Date: %s",getdescr(),onetmdt);
		return s;
	}
}
