//Michael Cho
//CSC 236-64
//Lab 7.1

public class QueueUnderflowException extends QueueException
{
	public QueueUnderflowException()
	{
		super("Queue Underflow");
	}

	public QueueUnderflowException(String msg)
	{
		super(msg);
	}
}