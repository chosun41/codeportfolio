//Michael Cho
//CSC 236-64
//Lab 7.1

public class ArrayQueue<T> implements QueueADT<T>
{
	private int maxqueuesize, count, queuefront, queuerear;
	private T[] list;

	//default constructor
	public ArrayQueue()
	{
		maxqueuesize=1440;
		queuefront=0;
		queuerear=maxqueuesize-1;
		count=0;
		list=(T[]) new Object[maxqueuesize];
	}

	//overloaded constructor
	public ArrayQueue(int queuesize)
	{
		if(queuesize<=0)
		{
			System.err.println("The size of the array to" + "implement the queue must be positive.");
			System.err.println("Creating an array of the size 100.");
			maxqueuesize = 100;
		}
		else
			maxqueuesize=queuesize;

		queuefront=0;
		queuerear=maxqueuesize-1;
		count=0;
		list=(T[]) new Object[maxqueuesize];
	}

	//initialize queue
	public void initializequeue()
	{
		for(int i=queuefront; i<queuerear; i=(i+1)% maxqueuesize)
		list[i]=null;

		queuefront=0;
		queuerear=maxqueuesize-1;
		count=0;
	}

	//count length of queue
	public double count()
	{
		return count ;
	}

	//check if queue is empty
	public boolean isemptyqueue()
	{
		return (count==0);
	}

	//check if queue is full
	public boolean isfullqueue()
	{
		return(count==maxqueuesize);
	}

	//check beginning of queue
	public T front() throws QueueUnderflowException
	{
		if(isemptyqueue())
			throw new QueueUnderflowException();

		return (T) list[queuefront];
	}

	//check end of queue
	public T back() throws QueueUnderflowException
	{
		if(isemptyqueue())
			throw new QueueUnderflowException();

		return (T) list[queuerear];
	}

	//enqueue element at end
	public void enqueue(T queueelement) throws QueueOverflowException
	{
		if(isfullqueue())
			throw new QueueOverflowException();

		queuerear=(queuerear+1)%maxqueuesize;
		count++;
		list[queuerear]=queueelement;
	}

	//dequeue element with highest priority at front
	public T dequeue() throws QueueUnderflowException
	{
		if(isemptyqueue())
			throw new QueueUnderflowException();

		count--;
		T elementremoved= (T) list[queuefront];
		list[queuefront]=null;

		queuefront=(queuefront+1)%maxqueuesize;

		return elementremoved;
	}

}