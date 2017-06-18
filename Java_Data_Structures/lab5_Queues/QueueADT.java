//Michael Cho
//CSC 236-64
//Lab 7.1

public interface QueueADT<T>
{
	public void initializequeue();
	//Method to initialize the queue to an empty state
	//Postcondition: The queue is initialized.

	public boolean isemptyqueue();
	//Method to determine whether the queue is empty.
	//Postcondition:Returns true if the queue is empty
	//otherwise, returns false

	public boolean isfullqueue();
	//Method to determine whether the queue is full
	//Postcondtion: Returns true if the queue is full
	//otherwise, returns false

	public double count();
	//Method to count length of queue

	public T front() throws QueueUnderflowException;
	//Method to return the first element of the queue.
	//Precondition: The queue exists and is not empty.
	//Postcondition: If the queue is empty, the method throws
	//queueunderflow exception, otherwise a reference to the
	//first element of the queue is returned

	public T back() throws QueueUnderflowException;
	//Method to return the last element of the queue.
	//Precondition: The queue exists and is not empty
	//Postcondition: If the queue is empty, the method throws
	//queueunderflowexception, otherwise a reference to the
	//last element of the queue is returned

	public void enqueue(T queueelement) throws QueueOverflowException;
	//Method to add queueelement to the queue
	//Precondition: The queue exists and is not ull
	//Postcondition: The queue is changed and queueelement is add to the queue

	public T dequeue() throws QueueUnderflowException;
	//Method to remove the first element of the queue
	//Precondition: The queue exists and is not empty
	//Postcondition: The queue is changed and the first element is
	//removed from queue and returned

}