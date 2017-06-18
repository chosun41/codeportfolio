//Michael Cho
//CSC 236-64
//Lab 4.3

public class LinkedStackDS<T> implements LinkedStackADT<T>
{
	private StackNode<T> stacktop;

	//default constuctor
	public LinkedStackDS()
	{
		stacktop=null;
	}

	//overloaded constructor
	public LinkedStackDS(StackNode<T> instacktop)
	{
		this.stacktop=instacktop;
	}

	//copy constructor
	public LinkedStackDS(LinkedStackDS a)
	{
		stacktop=a.stacktop;
	}

	//checks if list stack is empty
	public boolean isEmptyStack()
	{
		return (stacktop==null);

	}

	//add to top
	public void push(T newelement)
	{
		StackNode<T> newnode;

		newnode=new StackNode<T>(newelement, stacktop);

		stacktop=newnode;
	}

	//look at top, doesn't modify list stack
	public T peek() throws StackUnderflowException
	{
		if (isEmptyStack())
			throw new StackUnderflowException();

		return stacktop.value;

	}

	//remove from top
	public void pop() throws StackUnderflowException
	{
		if (isEmptyStack())
			throw new StackUnderflowException();

		stacktop=stacktop.next;

	}

	//stacknode class
	private class StackNode<T>
	{
		public T value;
		public StackNode<T> next;

		//default constructor
		public StackNode()
		{
			value=null;
			next=null;
		}

		//overloaded constructor
		public StackNode(T invalue, StackNode<T> innext)
		{
			this.value=invalue;
			this.next=innext;
		}

		//toString method
		public String toString()
		{
			return (String) stacktop.value;
		}
	}
}