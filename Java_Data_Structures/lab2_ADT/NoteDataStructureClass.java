public class NoteDataStructureClass implements NoteADT{

	private int value, length;
	private String letter, color, strlength;
	private double frequency;

	//default constructor
	//value and length drive whole program
	public NoteDataStructureClass() throws Exception
	{setLength(4);
	setValue(-9);
	}

	//overloaded constructor
	public NoteDataStructureClass(int inlength, int invalue) throws Exception
	{
	this.length=inlength;
	this.value=invalue;
	this.letter=setStrValue(this.value);
	setKeyColor(this.value);
	this.color=getKeyColor();
	setFrequency(this.value);
	this.frequency=getFrequency();
	this.strlength=setStrLength(length);
	}

	//copy constructor
	public NoteDataStructureClass(NoteDataStructureClass copyNote)
	{length=copyNote.length;
	value=copyNote.value;
	letter=copyNote.letter;
	color=copyNote.color;
	frequency=copyNote.frequency;
	strlength=copyNote.strlength;
	}


	//calls setstrvalue, setkeycolor, setfrequnecy
	//throws exception if outside of the range indicated in the diagram
	public void setValue(int invalue) throws Exception
	{if(invalue<-9 || invalue>2)
		throw new Exception("Invalid value!");
	else
		{value=invalue;
		setStrValue(value);
		setKeyColor(value);
		setFrequency(value);
		}
	}

	//assigns letter for value parameter
	public String setStrValue(int invalue) throws Exception
	{	switch(invalue)
			{	case -9: 	letter="C";
							break;
				case -8: 	letter="C#/Db";
							break;
				case -7: 	letter="D";
							break;
				case -6:	letter="D#/Eb";
							break;
				case -5:	letter="E";
							break;
				case -4:	letter="F";
							break;
				case -3:	letter="F#/Gb";
							break;
				case -2:	letter="G";
							break;
				case -1:	letter="G#/Ab";
							break;
				case 0:		letter="A";
							break;
				case 1:		letter="A#/Bb";
							break;
				case 2:		letter="B";
							break;
				default:	System.out.println("Corresponding letter could not be found");
							break;
				}
	return letter;
	}

	//assigns color for value parameter
	public void setKeyColor(int invalue) throws Exception
	{	switch(invalue)
			{	case -9:
				case -7:
				case -5:
				case -4:
				case -2:
				case 0:
				case 2:		color="White key (natural)";
							break;
				case -8:
				case -6:
				case -3:
				case -1:
				case 1:		color="Black key (sharp)";
							break;
				default:	System.out.println("Corresponding color could not be found");
							break;
				}
	}

	//calculates frequnecy and assigns it to frequency variable
	public void setFrequency(int invalue) throws Exception
	{frequency=440*(Math.pow(2,(double)invalue/12));
	}

	//throws exception if integer not 16,8,4,2,or 1
	//otherwise assing length value of inlength parameter
	public void setLength(int inlength) throws Exception
	{
	if(inlength!=16 && inlength!=8 && inlength !=4 && inlength !=2 && inlength !=1)
		throw new Exception ("Unacceptable input length!");
	else
		{length=inlength;
		setStrLength(length);
		}
	}

	//creates a corresponding string for length parameter
	public String setStrLength(int inlength) throws Exception
	{switch(inlength)
		{	case 16:	strlength="Sixteenth Note";
						break;
			case 8:		strlength="Eight Note";
						break;
			case 4:		strlength="Quarter Note";
						break;
			case 2:		strlength="Half Note";
						break;
			case 1:		strlength="Whole Note";
						break;
			default:	System.out.println("Correspondeing strlength could not be assigned!");
						break;
		}
	return strlength;
	}

	//return int value
	public int getValue()
	{return value;
	}

	//return length
	public int getLength()
	{return length;
	}

	//return color
	public String getKeyColor()
	{return color;
	}

	//return frequency
	public double getFrequency()
	{return frequency;
	}

	//toString method
	public String toString()
	{String str="";
	str+=letter + "\n";
	str+="Length: " + strlength + "\n";
	str+="Value: " + value + "\n";
	str+=color+"\n";
	str+=frequency + " Hz "+ "\n";
	return str;
	}
}