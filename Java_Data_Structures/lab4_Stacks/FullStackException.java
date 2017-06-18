//Michael Cho
//CSC 236-64
//Lab 4.2

// for list this will never be thrown, because list has no maximum size
public class FullStackException extends StackException
{

	//please note this is never thrown or is called by any method in
	//ListStackDataStrucClass
	public FullStackException()
	{
		super("Stack Overflow");


	public FullStackException(String msg)
	{
		super(msg);
	}
}