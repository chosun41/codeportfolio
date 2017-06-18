//Michael Cho
//CSC 236-64
//Lab 7.1

public class QueueOverflowException extends QueueException
{
	public QueueOverflowException()
	{
		super("Queue Overflow");
	}

	public QueueOverflowException(String msg)
	{
		super(msg);
	}
}