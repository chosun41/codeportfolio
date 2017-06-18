//Michael Cho
//CSC 236-64
//Lab 6.2

public class HeapUnderflowException extends RuntimeException
{
	public HeapUnderflowException()
	{
		super("Heap Underflow");
	}

	public HeapUnderflowException(String msg)
	{
		super(msg);
	}
}