import java.util.Scanner;

public class LockDataStructureClass implements LockADT{

	// x for 1st number, y for 2nd number, z for 3rd number
	//boolean closed for state of lock
	//boolean position for current dial
	private int x, y, z, position;
	private boolean closed;

	//default constructor
	//initialize x,y,z to 0,0,0, dial position to 0, and closed to true
	//to indicate lock is closed
	public LockDataStructureClass()
	{x=0;
	y=0;
	z=0;
	position=0;
	closed=true;
	}

	//overloaded constructor
	public LockDataStructureClass(int startx, int starty, int startz, int startposition, boolean startclosed)
	{this.x=startx;
	this.y=starty;
	this.z=startz;
	this.position=startposition;
	this.closed=startclosed;
	}

	//copy constructor
	public LockDataStructureClass(LockDataStructureClass copylock)
	{x=copylock.x;
	y=copylock.y;
	z=copylock.z;
	position=copylock.position;
	closed=copylock.closed;
	}

	//mutator for x
	public void setx(int newx) throws Exception
	{if (newx<0 || newx>39)
		throw new Exception("Wrong input value. Please place number between 0-39");
	else
		x=newx;
	}

	//mutator for y
	public void sety(int newy) throws Exception
	{if (newy<0 || newy>39)
		throw new Exception("Wrong input value. Please place number between 0-39");
	else
		y=newy;
	}

	//mutator for z
	public void setz(int newz) throws Exception
	{if (newz<0 || newz>39)
		throw new Exception("Wrong input value. Please place number between 0-39");
	else
		z=newz;
	}

	//alter combo through scanner int values which
	//then feed inputx to setx, inputy to sety, and inputz to setz
	//has to be throwable because it uses the set methods which are throwable as well
 	public void alter() throws Exception
	{
	Scanner keyboard=new Scanner (System.in);
	System.out.println("Please enter first number.");
	int inputx=keyboard.nextInt();
	setx(inputx);
	System.out.println("Please enter second number.");
	int inputy=keyboard.nextInt();
	sety(inputy);
	System.out.println("Please enter third number.");
	int inputz=keyboard.nextInt();
	setz(inputz);
	}

	//simulate turning of knob, parameter turnumber indicates
	//turnnumber: which turn you are on (1 for x, 2 for y, 3 for z)
	//turnto: which number you are turning to
	//position=-1 resets for loop index to 39, position=40 resets for loop index to 0
	public int turn(int turnnumber, int turnto) throws Exception
	{	if (turnto<0 || turnto>39)
			throw new Exception("Can't turn to that number because it doesn't exist.");

		else if(turnnumber==1)
		{System.out.println();
		System.out.println();
		System.out.printf("First number (%d): \n",turnto);
		//40 to ensure one revolution of dial, (40-turnto+1) indicates how much numbers go after
		//1st revolution clockwise from 0 to 0
		int currposition=position;
			for(int i=0;i<40+(40+currposition-turnto+1);i++)
				{
				if (position==-1)
					position=39;
				if (position== 40)
					position=0;
				System.out.print(position-- + " ");
				}
		//has to position++ after for loop because of post processing with position-- from print
			position++;
		}

		else if(turnnumber==2)
		{System.out.println();
		System.out.println();
		System.out.printf("Second number (%d): \n",turnto);
		//currposition keeps track of ending position from turnumber 1
		//to know how many numbers to go through in forloop
		//have to consider two cases where turnto >= ending postion of turn 1
		//in which we have to at least do one revolution + the difference between
		//the turnto and currposition ex. 17 ...0...17 ...26
		//and where turnto < ending position of turn1 in which we have to
		//do around two revolutions + difference between turnto and currposition
		// ex. 17...0..16...0...16
		int currposition=position;
			if(turnto<currposition)
				{	for(int i=0;i<40+(40+turnto-currposition+1);i++)
					{
					if (position==-1)
						position=39;
					if (position== 40)
						position=0;
					System.out.print(position++ + " ");
					}
				}
			if(turnto>=currposition)
				{	for(int i=0;i<40+(turnto-currposition+1);i++)
					{
					if (position==-1)
						position=39;
					if (position== 40)
						position=0;
					System.out.print(position++ + " ");
					}
				}

		//has to position-- after for loop because of post processing with position++ from print
			position--;
		}

		else if(turnnumber==3)
		{System.out.println();
		System.out.println();
		System.out.printf("Third number (%d): \n",turnto);
		//currposition keeps track of ending position from turnumber 2
		//to know how many numbers to go through in forloop
		//have to consider two cases where turnto < ending postion of turn 2
		//in which we will not pass the 0 position ex. 26,25...9
		//and where turnto >= ending position of turn2 in which we have to
		//turn to 0 and possibly beyond clockwise ex.26,25..0..27 (>=1 revolution)
		int currposition=position;
			if(turnto<currposition)
				{	for(int i=0;i<(currposition)-turnto+1;i++)
						{
						if (position==-1)
							position=39;
						if (position== 40)
							position=0;
						System.out.print(position-- + " ");
						}
				}
			if(turnto>=currposition)
				{	for(int i=0;i<currposition+(40-turnto+1);i++)
						{if (position==-1)
							position=39;
						if (position== 40)
							position=0;
						System.out.print(position-- + " ");
						}
				}
		//has to position++ after for loop because of post processing with position-- from print
		position++;

		}

	//return position in order to use in attempt method and verify they match the x,y,z values
	//in order to open the lock
	return turnto;
	}

	//close the lock
	public void close()
	{closed=true;
	System.out.println("Lock is now closed");
	}

	//attempt to open lock and position in returned into the variables pass1, pass2, pass3
	//position reset at 0 in case of a prior attempt involving turn and position change
	public void attempt() throws Exception
	{int out1, out2, out3;
	Scanner keyboard2=new Scanner(System.in);

	System.out.println();
	System.out.println("What number are you turning to first? ");
	int in1=keyboard2.nextInt();
	out1=turn(1, in1);

	System.out.println();
	System.out.println();
	System.out.println("What number are you turning to second? ");
	int in2=keyboard2.nextInt();
	out2=turn(2, in2);

	System.out.println();
	System.out.println();
	System.out.println("What number are you turning to third? ");
	int in3=keyboard2.nextInt();
	out3=turn(3, in3);

	//if the ending position of the turns actually match the alter combo inputted form keyboard
	//lock will open
	if(x==out1 && y==out2 && z==out3)
		{closed=false;
		System.out.println();
		System.out.println();
		System.out.println("Your attempt was succesful!");
		}
	else
		{
		System.out.println();
		System.out.println();
		System.out.println("Your attempt was unsuccesful!");
		}
	}

	//see if lock is closed or open by checking closed boolean and printing message
	public void inquire()
	{
	if(closed==true)
		System.out.println("The lock is currently closed");
	if(closed==false)
		System.out.println("The lock is currently open");
	}

	//shows where the current dial at
	public String current()
	{return "The current dial is at: " + position;
	}

	//return string of current lock combination
	public String toString()
	{return "The current lock combination is: " + x + " - " + y + " - " + z;
	}
}