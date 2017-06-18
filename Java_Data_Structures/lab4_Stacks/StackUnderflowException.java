//Michael Cho
//CSC 236-64
//Lab 4.1, 4.3

public class StackUnderflowException extends StackException
{

	public StackUnderflowException()
	{
		super("Stack Underflow");
	}

	public StackUnderflowException(String msg)
	{
		super(msg);
	}
}