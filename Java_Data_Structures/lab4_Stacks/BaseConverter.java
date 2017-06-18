//Michael Cho
//CSC 236-64
//Lab 4.2

import java.util.Scanner;

public class BaseConverter
{
	//need two liststacks to hold remainder for base and string for output
	private  ListStackDataStrucClass <Long> remainderstack;
	private  ListStackDataStrucClass <String> stringstack;

	//default constructor
	public BaseConverter()
	{
		this.remainderstack = new ListStackDataStrucClass<Long>();
		this.stringstack = new ListStackDataStrucClass<String>();
	}

	//input prompt called by processandPrint method
	//for 3x takes in number and base, transforms them into new number with target base,
	//and uses stacks to produce a string for each basenumber
	public void inputPrompt() throws Exception
	{
		System.out.println("Please enter 3 numbers and the base you are converting them to");
		Scanner keyboard = new Scanner (System.in);
		for (int i=1; i<4; i++)
		{
			System.out.println("Please enter number " + i);
			Long innum=keyboard.nextLong();
			System.out.println("Please enter base for number " + i);
			Long inbase=keyboard.nextLong();
			BaseNumber inbasenum = new BaseNumber(innum, inbase);
			convertAll(convert(inbasenum));
		}
	}

	//convert each basenumber to a String. throws exception if base of
	//basenumber is <2 or >9
	public String convert(BaseNumber inbasenumber) throws Exception
	{
		Long remainder = 0L;
		Long testnum = inbasenumber.getNumber();
		Long testbase = inbasenumber.getBase();

		//base has to be between 2 and 9
		if (testbase<2 || testbase>9)
			throw new Exception("Wrong input base");

		String output = "The number " + testnum  + " base 10 in base " + testbase + " is: ";

		 //when converting to the new base,	the first digit starting from the right will be number % base.
		 //after we get the remainder, we divide by the base and push remainder on the remainderstack,
		 //so afterwards we can print the new number according to new base from left to right.
		 //we continue this until the quotient is 0 from the do while loop.
		 //we spit this all out until the remainderstack is empty, peeking, addingint to output string, and then popping.
	     do
	     {  remainder = testnum % testbase;
	     	testnum /= testbase;
	     	remainderstack.push(remainder);
	     }
		 while (testnum != 0);

		 while (!remainderstack.isEmpty())
	     {
			 Long peeked=(Long) remainderstack.peek();
			 output+=peeked;
			 remainderstack.pop();
	     }

		return output;
	}

	//this method actually just pushes each String from convert method
	//onto another stack named stringstack to be outputted and popped in the toString method
	public void convertAll(String instring)
	{
		stringstack.push(instring);

	}

	//toString method
	//peek, add to s String, and then pop, s will contain one long string
	//of all the converted numbers into their new bases
	public String toString()
	{
		String s="";

		while (!stringstack.isEmpty())
		{
			String peeked=(String) stringstack.peek();
			s+=peeked + "\n";
			stringstack.pop();
	    }

	    return s;
	}

	//calls inputprompt method which starts the menu
	public void processAndPrint() throws Exception
	{
		inputPrompt();
		System.out.println(toString());
	}

	private class BaseNumber
	{
		private Long number, base;

		//default constructor
		public BaseNumber()
		{
			number=0L;
			base=0L;
		}

		//overloaded constructor
		public BaseNumber(Long innumber, Long inbase)
		{
			this.number=innumber;
			this.base=inbase;

		}

		//get numer
		public Long getNumber()
		{
			return number;
		}

		//get base
		public Long getBase()
		{
			return base;
		}

		//set number
		public void setNumber(Long innumber)
		{
			number=innumber;

		}

		//set base
		public void setBase(Long inbase)
		{
			base=inbase;
		}

	}


}





























