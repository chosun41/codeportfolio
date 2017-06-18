//Michael Cho
//CSC 236-64
//Lab 4.2

public class EmptyStackException extends StackException
{

	public EmptyStackException()
	{
		super("Stack Overflow");
	}

	public EmptyStackException(String msg)
	{
		super(msg);
	}
}