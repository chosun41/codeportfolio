//Michael Cho
//CSC 236-64
//Lab 6.2

public class HeapOverflowException extends RuntimeException
{
	public HeapOverflowException()
	{
		super("Heap Overflow");
	}

	public HeapOverflowException(String msg)
	{
		super(msg);
	}
}