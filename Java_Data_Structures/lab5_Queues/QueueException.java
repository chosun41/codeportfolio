//Michael Cho
//CSC 236-64
//Lab 7.1

public class QueueException extends RuntimeException
{
	public QueueException()
	{
		super("Queue Exception");
	}

	public QueueException(String msg)
	{
		super(msg);
	}
}