public interface NoteADT{

	public void setValue(int invalue) throws Exception;
	//sets value and calls on setstrvalue, setkeycolor, setfrequency
	//Precondition: if outside of value range throws exception

	public String setStrValue(int invalue) throws Exception;
	//sets the letter based on the value parameter

	public void setKeyColor(int invalue) throws Exception;
	//sets the color based on the value parameter

	public void setFrequency(int invalue) throws Exception;
	//sets the frequency based on the value parameter

	public String setStrLength(int inlength) throws Exception;
	//sets the string of length based on the length parameter

	public int getValue();
	//return value

	public int getLength();
	//return length

	public String getKeyColor();
	//return color

	public double getFrequency();
	//return frequency

	public String toString();
	//prints details about note object
}