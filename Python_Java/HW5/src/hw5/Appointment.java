package hw5;
import java.text.DateFormat;
import java.text.ParseException; 
import java.text.SimpleDateFormat; 
import java.util.Date; 

public abstract class Appointment{
	
	private String description;
	
	/* overloaded constructor
	 * @param String 
	 */
	public Appointment(String descr){
		this.description=descr;
	}
	
	/* Accessor method for getting the string description
	 * 
	 * @param none
	 * @return string description
	 */
	public String getdescr(){
		return description;
	}
	
	/* abstract method where it can be defined in subclasses
	 * @param Date 
	 */
	public abstract boolean occursIn(int year, int month, int day) throws ParseException;
}
