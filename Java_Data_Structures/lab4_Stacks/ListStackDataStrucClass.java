//Michael Cho
//CSC 236-64
//Lab 4.2

public class ListStackDataStrucClass<T> implements ListStackADT<T>
{
	private StackNode<T> stacktop;

	//default constuctor
	public ListStackDataStrucClass()
	{
		stacktop=null;
	}

	//overloaded constructor
	public ListStackDataStrucClass(StackNode<T> instacktop)
	{
		this.stacktop=instacktop;
	}

	//get top
	public StackNode<T> getTop()
	{
		return stacktop;
	}

	//set top
	public void setTop(StackNode<T> instacktop)
	{
		stacktop=instacktop;
	}

	//checks if list stack is empty
	public boolean isEmpty()
	{
		return (stacktop==null);

	}

	//checks if list stack is full(which it never is)
	public void ifEmpty() throws EmptyStackException
	{
		if(isEmpty())
			throw new EmptyStackException();
	}


	//add to top
	public void push(T newelement)
	{
		StackNode<T> newnode;

		newnode=new StackNode<T>(newelement, stacktop);

		stacktop=newnode;
	}

	//look at top, doesn't modify list stack
	public T peek() throws EmptyStackException
	{
		ifEmpty();

		return stacktop.getValue();

	}

	//remove from top
	public void pop() throws EmptyStackException
	{
		ifEmpty();

		stacktop=stacktop.getNext();

	}

	//tostring method
	public String toString()
	{
		return (String) stacktop.getValue();
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

		//copy constuctor
		public StackNode(StackNode a)
		{
			value=(T) a.value;
			next=a.next;
		}

		//get value
		public T getValue()
		{
			return value;
		}

		//get next
		public StackNode<T> getNext()
		{
			return next;
		}

		//set value
		public void setValue(T invalue)
		{
			value=invalue;
		}

		//set next
		public void setNext(StackNode<T> innext)
		{
			next=innext;
		}
	}

}