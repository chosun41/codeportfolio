//Michael Cho
//CSC 236-64
//Lab 4.1

public class ArrayStackDataStrucClass<T> implements ArrayStackADT<T>
{

	private int maxstacksize, stacktop;
	private T[] list;

	//default constructor
	public ArrayStackDataStrucClass()
	{
		maxstacksize=50;
		stacktop=0;
		list=(T[]) new Object [maxstacksize];
	}

	//overloaded constructor
	public ArrayStackDataStrucClass(int inmaxstacksize, int instacktop)
	{
		this.maxstacksize=inmaxstacksize;
		this.stacktop=instacktop;
		this.list=(T[]) new Object [inmaxstacksize];
	}

	//copy constructor
	public ArrayStackDataStrucClass(ArrayStackDataStrucClass a)
	{
		maxstacksize=a.maxstacksize;
		stacktop=a.stacktop;
		list=(T[]) a.list;
	}

	//initialize the stack
	public void initializeStack()
	{
		for(int i=0;i<stacktop;i++)
			list[i]=null;

		stacktop=0;
	}

	//checks if stack empty
	public boolean isEmptyStack()
	{
		return (stacktop==0);
	}

	//checks if stack full
	public boolean isFullStack()
	{
		return(stacktop==maxstacksize);
	}

	//push element onto top of stack
	public void push(T newitem) throws StackOverflowException
	{
		if(isFullStack())
			throw new StackOverflowException();

		list[stacktop]=newitem;

		stacktop++;
	}

	//looks at top of stock but doesn't modify
	public T peek() throws StackUnderflowException
	{
		if(isEmptyStack())
			throw new StackUnderflowException();
		return (T) list[stacktop-1];
	}

	//removes top of stack, doesn't return
	public void pop() throws StackUnderflowException
	{
		if(isEmptyStack())
			throw new StackUnderflowException();

		stacktop--;
		list[stacktop]=null;
	}
}