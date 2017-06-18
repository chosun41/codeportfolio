//Michael Cho
//CSC 236-64
//Lab 4.1

public class PrimeFactorizationDemo{

	public static void main(String []args)
	{
		//only have to initialize one stack because it'll empty out before reuse, even though we are calling
		//a static method that is not tied to an instance
		ArrayStackDataStrucClass <Integer> numlist1= new ArrayStackDataStrucClass<Integer>(50,0);
		primefactors(3960, numlist1);
		primefactors(1234, numlist1);
		primefactors(222222, numlist1);
		primefactors(13780, numlist1);
	}


	//static method for prime factorization algorithm. takes in number and existing stack from main.
	//algorithm is basically a for loop from 2 to number,which will keep dividing by int testprime in loop as
	//long as innum%testprime=0, push each testprime onto teststack, and then divide innum by testprime to get new innum.
	//at the end spit it all back out with peek, put into a temp variable because of void, and then pops off stack
	private static void primefactors(int innum, ArrayStackDataStrucClass<Integer> instack)
	{
		try3
		{
			ArrayStackDataStrucClass <Integer> teststack =instack;
			System.out.print("The prime factorization of " + innum + " is: ");
			for (int testprime = 2; testprime <= innum; testprime++)
			{

				while (innum % testprime == 0)
		    	{
				teststack.push(testprime);
				innum /= testprime;
		    	}
			}

			while(!teststack.isEmptyStack())
			{
			int temp=teststack.peek();
			teststack.pop();
			if(teststack.isEmptyStack())
				System.out.print(temp);
			else
				System.out.print(temp + " * ");
			}

    		System.out.println();

		}

		catch(Exception a)
		{
			System.out.println("Exception thrown");
		}


	}
}












