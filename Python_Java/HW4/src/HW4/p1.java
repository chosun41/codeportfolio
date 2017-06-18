package HW4;
import java.io.FileNotFoundException;
import java.io.IOException;
/*read in util library for the Scanner and InputMismatchException class*/
import java.util.*;

public class p1 {
	public static void main(String[] args)
	{
		/*initialize variables*/
		float convrate=0;
		float dollar=0;
		float yen=0;
		int choice=0; 
		
		/*Put in the initial conversion rate, before moving into the menu in the next part.
		 If initial conversion rate is negative, equal to 0, or is not a number, the while loop
		 will continue until right input is entered upon which it will print the conversion
		 rate message and break from while loop.*/
		System.out.println("Please enter the initial conversion rate for yen for $1");
		while(true)
		{
			try{
				Scanner initconv= new Scanner(System.in);
				convrate = initconv.nextFloat();
				testException(convrate);
				System.out.printf("The conversation rate for $1 is \u00a5%,.2f \n\n", convrate);
				break;
	    		}
			catch(FileNotFoundException e){
				System.err.println(e.getMessage());
			}
			catch(NumberFormatException e){
				System.err.println(e.getMessage());
			}
			catch(InputMismatchException e){ 
				System.err.println("Not a number. Please reenter.");
			}
		}
		
    	/*menu driven format and same exceptions from previous part are built in here.
		option 4 exits out of the program
		any other value besides value 1-4 continues the menu while loop*/
		while (choice != 4) {
			System.out.println("Enter one of the following commands:");
			System.out.println("1 - Change the conversion rate");
			System.out.println("2 - Dollar to Yen calculator");
			System.out.println("3 - Yen to Dollar calculator");
			System.out.println("4 - Exit program");
			Scanner menuinput = new Scanner(System.in);
			choice = menuinput.nextInt();

		    if (choice==1) {
				System.out.println("Please type in the new conversion rate of yen for $1");
				while(true)
				{
					try{
						Scanner inputa = new Scanner(System.in);
						convrate = inputa.nextFloat();
						testException(convrate);
						System.out.printf("The new conversation rate for $1 is \u00a5%,.2f \n\n", convrate);
						break;
			    		}
					catch(FileNotFoundException e){
						System.err.println(e.getMessage());
					}
					catch(NumberFormatException e){
						System.err.println(e.getMessage());
					}
					catch(InputMismatchException e){ 
						System.err.println("Not a number. Please reenter.");
					}
				}
		    }

		    else if(choice == 2) {
				System.out.println("Please type in the number of dollars you are "
						+ "trying to convert to yen");
				while(true)
				{
					try{
						Scanner inputb = new Scanner(System.in);
						dollar = inputb.nextFloat();
						testException(dollar);
						yen=dollar*convrate;
						System.out.printf("$%,.2f is equivalent to \u00a5%,.2f \n\n", dollar, yen); 
						break;
			    		}
					catch(FileNotFoundException e){
						System.err.println(e.getMessage());
					}
					catch(NumberFormatException e){
						System.err.println(e.getMessage());
					}
					catch(InputMismatchException e){ 
						System.err.println("Not a number. Please reenter.");
					}
				}
		    }

		    else if(choice == 3) {
				System.out.println("Please type in the number of yen you are "
						+ "trying to convert to dollars");
				while(true)
				{
					try{
						Scanner inputc = new Scanner(System.in);
						yen = inputc.nextFloat();
						testException(yen);
						dollar=yen/convrate;
						System.out.printf("\u00a5%,.2f is equivalent to $%,.2f \n\n", yen, dollar); 
						break;
			    	}
					catch(FileNotFoundException e){
						System.err.println(e.getMessage());
					}
					catch(NumberFormatException e){
						System.err.println(e.getMessage());
					}
					catch(InputMismatchException e){ 
						System.err.println("Not a number. Please reenter.");
					}
				}
		    }
		    else if(choice == 4) {
		    	System.out.println("Goodbye");
		    }
		    else{
		    	System.out.println("Wrong entry! Please pick another choice.");		    
		    }
		} 
	}
	
	/**
	 * This function throws filenotfound exception if float is negative 
	 * 
	 * @param float i
	 * @return none, just throws exceptions to be caught
	 */	
	public static void testException(float i) 
			throws FileNotFoundException, NumberFormatException{
		if(i < 0){
			throw new FileNotFoundException("Negative number. Please reenter.");
		}
		else if(i == 0){
			throw new NumberFormatException("Number can't be zero. Please reenter.");
		}
	}
}
