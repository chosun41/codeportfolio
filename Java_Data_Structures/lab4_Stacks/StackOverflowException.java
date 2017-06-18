//Michael Cho
//CSC 236-64
//Lab 4.1, 4.3

public class StackOverflowException extends StackException
{

	public StackOverflowException()
	{
		super("Stack Overflow");
	}

	public StackOverflowException(String msg)
	{
		super(msg);
	}
}