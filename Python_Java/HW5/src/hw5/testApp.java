package hw5; 
import java.util.ArrayList;
import java.util.Collections;
import java.text.ParseException; 
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;

public class testApp {
	public static void main(String [] args) throws ParseException, IOException{
		
		/* empty array list
		 * 
		 */
		ArrayList<Appointment> x = new ArrayList<Appointment>();
		
		/* add appointments for onetime, daily, and monthly subclasses
		 * in empty appointment arraylist x
		 */
		x.add(new Onetime("surgery","11/01/2017"));
		x.add(new Daily("insulin shots","5/1/2017","8/1/2017"));
		x.add(new Monthly("mri",1, 2017, 2, 2017, 7));
		
		/* test cases for description, string method, and occursIn method true/false
		 * for each arraylist item of appointments
		 */
		System.out.println("Test cases for onetime appointment on 11/1/2017");
		System.out.println(x.get(0).getdescr());
		System.out.println("Testing 11/1/2017 " + x.get(0).occursIn(2017,11,1));
		System.out.println("Testing 11/2/2017 " + x.get(0).occursIn(2017,11,2));
		System.out.println(x.get(0).toString() + "\n");

		System.out.println("Test cases for daily appointment 5/1-8/1/2017");
		System.out.println(x.get(1).getdescr());
		System.out.println("Testing 4/1/2017 " + x.get(1).occursIn(2017,4,1));
		System.out.println("Testing 5/1/2017 " + x.get(1).occursIn(2017,5,1));
		System.out.println("Testing 6/5/2017 " + x.get(1).occursIn(2017,6,5));
		System.out.println("Testing 8/1/2017 " + x.get(1).occursIn(2017,8,1));
		System.out.println("Testing 9/1/2017 " + x.get(1).occursIn(2017,9,1));
		System.out.println(x.get(1).toString()+ "\n");
		
		System.out.println("Test cases for monthly appointment 2/1-7/1/2017");
		System.out.println(x.get(2).getdescr());
		System.out.println("Testing 1/1/2017 " + x.get(2).occursIn(2017,1,1));
		System.out.println("Testing 2/1/2017 " + x.get(2).occursIn(2017,2,1));
		System.out.println("Testing 5/1/2017 " + x.get(2).occursIn(2017,5,1));
		System.out.println("Testing 5/2/2017 " + x.get(2).occursIn(2017,5,2));
		System.out.println("Testing 7/1/2017 " + x.get(2).occursIn(2017,7,1));
		System.out.println("Testing 8/1/2017 " + x.get(2).occursIn(2017,8,1));
		System.out.println(x.get(2).toString()+ "\n");

		/* write appointments to text file
		 * 
		 */
		apptotxt(x);
		
		/* print out all appointments that occur on this date
		 * testing 7/1/2017 which has both a monthly and daily appointment
		 */
		printappt(x, 2017, 7, 1);
		
		}

	/* writes text of tostring method into a text file
	 * 
	 * @param arraylist of appointments
	 * @return nothing, just writes text of arraylist tostring method into file
	 */	
	public static void apptotxt(ArrayList<Appointment>inptx) throws IOException{
		
		FileWriter fw = new FileWriter("appt.txt");
	
		for (Appointment elem : inptx) {
			fw.write(elem.toString() + System.getProperty("line.separator"));
		}
		fw.close();
	}	
	
	/* writes text of tostring method into a text file
	 * 
	 * @param arraylist of appointments
	 * @return nothing, just writes text of arraylist tostring method into file
	 */	
	public static void printappt(ArrayList<Appointment>inptx, 
			int year, int month, int day) throws IOException, ParseException{
	
		for (int i = 0; i < inptx.size(); i++){ 
			if(inptx.get(i).occursIn(year, month, day)){
				System.out.println(inptx.get(i).toString());
			}
		}
	}	
	
}
