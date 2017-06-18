//Michael Cho
//CSC 236-64
//Lab 6.2

public interface HeapPQ<T>
{
	//initialize queue
	public void initializequeue();

	//count length of queue
	public double count();

	//check if queue is empty
	public boolean isEmpty();

	//check if queue is full
	public boolean isFull();

	//check beginning of queue
	public T front() throws HeapUnderflowException;

	//check end of queue
	public T back() throws HeapUnderflowException;

	//enqueue element at end
	public void enqueue(T queueelement) throws HeapOverflowException;

	//dequeue element with highest priority at front
	public void dequeue() throws HeapUnderflowException;

	//preorder
	public String preorder();

	//inorder
	public String inorder();

	//postorder
	public String postorder();

	//to string method
	public String toString();
}